package com.example.post.list.app.model.api;

import com.example.post.list.app.model.api.dto.CommentDto;
import com.example.post.list.app.model.api.dto.UserDto;
import com.example.post.list.app.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.post.list.app.model.api.JsonPlaceHolderApi.SERVICE_END_POINT;


/**
 * This is the implementation of {@link ServiceApiManager}
 * @author fabian hoyos
 */
@Singleton
public class ServiceApiManagerImpl implements ServiceApiManager {

    private JsonPlaceHolderApi api;
    private ServiceFactory serviceFactory;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ServiceApiManagerImpl(ServiceFactory serviceFactory, NetworkUtils networkUtils) {
        this.serviceFactory = serviceFactory;
        this.api = this.serviceFactory.createRetrofitService(JsonPlaceHolderApi.class, SERVICE_END_POINT);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getPosts(OnPost listener) {
        compositeDisposable.add(api.requestPosts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onPostList,
                        throwable -> listener.onPostList(new ArrayList<>())));
    }

    @Override
    public void removePost(int postId, OnRemovedPost listener) {
        compositeDisposable.add(api.deletePostbyId(postId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> listener.onRemovedPost(),
                        throwable -> listener.onError()));
    }

    @Override
    public Observable<List<CommentDto>> getCommentsByPostId(int postId) {
        return api.requestCommentsByPostId(postId);
    }

    @Override
    public Observable<UserDto> getUser(int userId) {
        return api.requestUser(userId);
    }

    @Override
    public void clear() {
        if (compositeDisposable.size() > 0 && !compositeDisposable.isDisposed())
            compositeDisposable.clear();
    }
}
