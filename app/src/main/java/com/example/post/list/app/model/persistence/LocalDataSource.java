package com.example.post.list.app.model.persistence;

import com.example.post.list.app.model.persistence.entities.Post;

import java.util.List;

import io.reactivex.Flowable;


/**
 * This interface defines the communication of persistence layer
 *
 * @author fabian hoyos
 */
public interface LocalDataSource {

    long addPost(Post post);

    Flowable<List<Post>> getPosts();

    Flowable<List<Post>> getPostsByState(int state);

    Flowable<Post> findPostById(int id);

    void deletePost(int postId);

    void deletePosts();

    void updatePost(Post post);

    long checkPost(int id);
}
