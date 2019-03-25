package com.example.post.list.app.presentation.presenters;

import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.utils.PostUtils;

import static com.example.post.list.app.model.repository.PostsContractRepository.Repository;
import static com.example.post.list.app.presentation.presenters.PostContractDescriptionView.Presenter;
import static com.example.post.list.app.presentation.presenters.PostContractDescriptionView.View;

/**
 * @author fabian hoyos
 */
public class DescriptionPresenterImpl extends BasePresenter<View> implements Presenter {
    private Repository repository;
    private Post currentPost;


    public DescriptionPresenterImpl(View view, Repository repository) {
        super(view);
        this.repository = repository;
        init();
    }

    @Override
    public void init() {
        super.init();
        repository.init(new DescriptionAdapterView() {
            @Override
            void onErrorMessage(String message) {
                view.onMessage(message);
                view.onShowLoader(false);
            }

            @Override
            void onPostResult(Post post) {
                currentPost = post;
                view.onPost(post);
            }
        });
    }

    @Override
    public void release() {
        super.release();
        repository.release();
        currentPost = null;
    }

    @Override
    public void addPostToFavorites() {
        if (currentPost != null) {
            view.onShowLoader(true);
            repository.updatePost(PostUtils.addRemoveTofavorites(currentPost));
        }
    }

    @Override
    public void getPost(Post post) {
        if (PostUtils.validateUserAndComments(post)) {
            this.currentPost = post;
            view.onPost(post);
        } else {
            view.onShowLoader(true);
            repository.getPost(post.getId());
        }
    }

    @Override
    public Post getCurrentPost() {
        return currentPost;
    }
}
