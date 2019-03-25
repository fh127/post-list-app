package com.example.post.list.app.presentation.presenters;

import com.example.post.list.app.model.persistence.entities.Post;

import java.util.List;

/**
 * This interface contains the contract of interactions between view and presenter
 * @author fabian hoyos
 */
public interface PostContractMainView {


    interface Presenter {

        void refreshList();

        void getPostList();

        void removePost(Post post);

        void removePosts();

        void changeInitialState(Post post);

        void setFilterMenuOption(int id, String title);

        String getFilterMenuOption(String defaultTitle);

    }

    interface View extends BaseView {

        void onPostList(List<Post> postList);

        void onRefresh();

        void onPostListRemoved();

        void onPostRemoved();

        void onUpdatedPost(Post post);

    }

}
