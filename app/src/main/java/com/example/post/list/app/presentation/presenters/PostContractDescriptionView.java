package com.example.post.list.app.presentation.presenters;

import com.example.post.list.app.model.persistence.entities.Post;

/**
 * This interface contains the contract of interactions between view and presenter
 * @author fabian hoyos
 */
public interface PostContractDescriptionView {

    interface Presenter {

        void addPostToFavorites();

        void getPost(Post post);

        Post getCurrentPost();
    }

    interface View extends BaseView {

        void onPost(Post post);
    }
}
