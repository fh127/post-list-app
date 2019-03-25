package com.example.post.list.app.presentation.presenters;

import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.model.repository.PostsContractRepository.ModelOperations;

import java.util.List;

/**
 * this class to reuse only the necessary methods according interface segregation principle
 *
 * @author fabian hoyos
 */
public abstract class DescriptionAdapterView implements ModelOperations {

    abstract void onErrorMessage(String message);

    abstract void onPostResult(Post post);

    @Override
    public void onError(String message) {
        onErrorMessage(message);
    }

    @Override
    public void onRefreshList() {

    }

    @Override
    public void onPostList(List<Post> postList) {

    }

    @Override
    public void onRemovedPost() {

    }

    @Override
    public void onRemovedPosts() {

    }

    @Override
    public void onPost(Post post) {
        onPostResult(post);
    }
}
