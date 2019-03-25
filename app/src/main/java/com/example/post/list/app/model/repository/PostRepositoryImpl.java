package com.example.post.list.app.model.repository;

import android.util.Log;

import com.example.post.list.app.model.api.ServiceApiManager;
import com.example.post.list.app.model.api.ServiceApiManagerImpl;
import com.example.post.list.app.model.api.dto.PostDto;
import com.example.post.list.app.model.persistence.LocalDataSource;
import com.example.post.list.app.model.persistence.LocalDataSourceImpl;
import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.utils.CollectionsUtils;
import com.example.post.list.app.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.post.list.app.model.api.ServiceApiManager.OnRemovedPost;
import static com.example.post.list.app.utils.Constants.ERROR_DELETE_POSTS;
import static com.example.post.list.app.utils.Constants.ERROR_FAVORITE_POST;
import static com.example.post.list.app.utils.Constants.ERROR_INTERNET_CONECTION_LOST;
import static com.example.post.list.app.utils.Constants.ERROR_POST_REQUEST;
import static com.example.post.list.app.utils.Constants.ERROR_POTS_COMMENT_REQUEST;
import static com.example.post.list.app.utils.Constants.ERROR_SAVE_POST;
import static com.example.post.list.app.utils.Constants.ERROR_UNKNOWN_POST;
import static com.example.post.list.app.utils.PostUtils.buildPost;
import static com.example.post.list.app.utils.PostUtils.fillPost;
import static com.example.post.list.app.utils.PostUtils.getInitialState;
import static com.example.post.list.app.utils.PostUtils.validateUserAndComments;
import static io.reactivex.Observable.fromIterable;
import static io.reactivex.Observable.zip;

/**
 * this implementation of  Post Repository:
 * - Handles the local storage and network connections
 * - Handles cache rules
 * - Encapsulates the logic of third-party libraries and keep interface communications of Java core.
 *
 * @author fabian hoyos
 */
@Singleton
public class PostRepositoryImpl extends BaseRepository implements PostsContractRepository.Repository {

    private static final String TAG = PostRepositoryImpl.class.getSimpleName();
    private PostsContractRepository.ModelOperations listener;
    private ServiceApiManager apiManager;
    private LocalDataSource localDataSource;
    private CompositeDisposable compositeDisposable;

    @Inject
    public PostRepositoryImpl(ServiceApiManagerImpl apiManager, LocalDataSourceImpl localDataSource, NetworkUtils networkUtils) {
        this.apiManager = apiManager;
        this.localDataSource = localDataSource;
        this.networkUtils = networkUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void init(PostsContractRepository.ModelOperations modelOperations) {
        this.listener = modelOperations;
    }

    @Override
    public void getPosts() {
        compositeDisposable.add(localDataSource.getPosts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::validatePostList,
                        throwable -> executeService(this::saveCachePostList, this::fetchPostRequest)));
    }

    @Override
    public void getPostsByState(int state) {
        compositeDisposable.add(localDataSource.getPostsByState(state)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onPostList,
                        throwable -> listener.onError(ERROR_FAVORITE_POST)));
    }


    @Override
    public void refreshPost() {
        executeService(this::saveCacheOnlyNewPost, this::fetchPostRequest);
    }

    @Override
    public void getPost(int postId) {
        compositeDisposable.add(localDataSource.findPostById(postId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::validatePost,
                        throwable -> listener.onError(ERROR_UNKNOWN_POST)));
    }

    @Override
    public void removePost(int postId) {
        executeService(postId, this::deletePostRequest);
    }

    @Override
    public void updatePost(Post post) {
        clearSubscriptions();
        compositeDisposable.add(Observable.create((ObservableOnSubscribe<Void>) emitter -> localDataSource.updatePost(post))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
        listener.onPost(post);
    }

    @Override
    public void removeAllPosts() {
        clearSubscriptions();
        compositeDisposable.add(Observable.create((ObservableOnSubscribe<Void>) emitter -> localDataSource.deletePosts())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
        listener.onRemovedPosts();
    }

    @Override
    public void release() {
        apiManager.clear();
        clearSubscriptions();
    }

    private void clearSubscriptions() {
        if (compositeDisposable.size() > 0 && !compositeDisposable.isDisposed())
            compositeDisposable.clear();
    }

    /**
     * this method validates if the post has complete data
     * another case the post object is processed to complete the data
     *
     * @param post
     */
    private void validatePost(Post post) {
        if (validateUserAndComments(post))
            listener.onPost(post);
        else
            executeService(post, this::fetchUserAndCommentsRequest);
    }

    private void validatePostList(List<Post> postList) {
        if (!CollectionsUtils.isEmpty(postList))
            listener.onPostList(postList);
        else
            executeService(this::saveCachePostList, this::fetchPostRequest);
    }

    // local storage methods

    /**
     * this method validates the services response and transforms the dto class to business model
     *
     * @param postDtos
     */
    private void processPostList(List<PostDto> postDtos, PostStorageExecutor executor) {
        if (CollectionsUtils.isEmpty(postDtos)) {
            listener.onError(ERROR_POST_REQUEST);
        } else {
            List<Post> posts = new ArrayList<>();
            for (PostDto postDto : postDtos) {
                Post post = buildPost(postDto, getInitialState(postDto.getId()));
                posts.add(post);
            }
            executor.processPosts(posts);
        }
    }

    private void removeCachePost(int postId) {
        clearSubscriptions();
        compositeDisposable.add(Observable.create((ObservableOnSubscribe<Void>) emitter -> localDataSource.deletePost(postId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
        listener.onRemovedPost();
    }

    private void saveCachePostList(List<Post> posts) {
        compositeDisposable.add(fromIterable(new ArrayList<>(posts))
                .map(post -> localDataSource.addPost(post))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> Log.d(TAG, "success saved post" + integer),
                        throwable -> listener.onError(ERROR_SAVE_POST)));
        listener.onPostList(posts);
    }

    private void saveCacheOnlyNewPost(List<Post> posts) {
        compositeDisposable.add(fromIterable(new ArrayList<>(posts))
                .filter(post -> localDataSource.checkPost(post.getId()) < 0)
                .map(post -> localDataSource.addPost(post))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> listener.onRefreshList(),
                        throwable -> listener.onError(ERROR_SAVE_POST)));
    }

    // services method
    private void fetchPostRequest(PostStorageExecutor executor) {
        apiManager.getPosts(postDtos ->
                processPostList(postDtos, executor));
    }

    private void fetchUserAndCommentsRequest(Post post) {
        compositeDisposable.add(zip(apiManager.getCommentsByPostId(post.getId()),
                apiManager.getUser(post.getUserReferenceId()),
                (commentDtos, userDto) -> fillPost(post, userDto, commentDtos))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(post1 -> updatePost(post),
                        throwable -> listener.onError(ERROR_POTS_COMMENT_REQUEST)
                ));
    }

    private void deletePostRequest(int postId) {
        apiManager.removePost(postId, new OnRemovedPost() {
            @Override
            public void onRemovedPost() {
                removeCachePost(postId);
            }

            @Override
            public void onError() {
                listener.onError(ERROR_DELETE_POSTS);
            }
        });
    }

    @Override
    protected void onInternetLostConnection() {
        listener.onError(ERROR_INTERNET_CONECTION_LOST);
    }


    //interfaces

    /**
     * this interface is used to save posts in local storage according the specific flows
     */
    @FunctionalInterface
    interface PostStorageExecutor {
        void processPosts(List<Post> postList);
    }
}
