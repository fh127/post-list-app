package com.example.post.list.app.model.repository;

import com.example.post.list.app.model.persistence.entities.Post;

import java.util.List;

/**
 * This interface contains the contract of interactions between the Repository and presenter
 * @author fabian hoyos
 */
public interface PostsContractRepository {

    interface Repository {

        void getPosts();

        void getPostsByState(int state);

        void refreshPost();

        void getPost(int postId);

        void removePost(int postId);

        void updatePost(Post post);

        void removeAllPosts();

        void release();

        void init(ModelOperations operations);
    }

    interface ModelOperations {

        void onPostList(List<Post> postList);

        void onRemovedPost();

        void onRemovedPosts();

        void onPost(Post post);

        void onError(String message);

        void onRefreshList();

    }
}
