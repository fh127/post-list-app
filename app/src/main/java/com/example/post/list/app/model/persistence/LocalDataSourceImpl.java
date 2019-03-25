package com.example.post.list.app.model.persistence;

import android.content.Context;

import com.example.post.list.app.model.persistence.dao.PostDao;
import com.example.post.list.app.model.persistence.dao.PostDatabase;
import com.example.post.list.app.model.persistence.entities.Post;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * This is the implementation of {@link LocalDataSource}
 * @author fabian hoyos
 */
@Singleton
public class LocalDataSourceImpl implements LocalDataSource {

    private final PostDao postDao;

    @Inject
    public LocalDataSourceImpl(Context context) {
        PostDatabase database = PostDatabase.getInstance(context);
        this.postDao = database.postDao();
    }

    @Override
    public long addPost(Post post) {
        return postDao.insertPost(post);
    }

    @Override
    public Flowable<List<Post>> getPosts() {
        return postDao.getPosts();
    }

    @Override
    public Flowable<List<Post>> getPostsByState(int state) {
        return postDao.findPostListByState(state);
    }

    @Override
    public Flowable<Post> findPostById(int id) {
        return postDao.findPostById(id);
    }

    @Override
    public void deletePost(int postId) {
        postDao.deletePost(postId);
    }

    @Override
    public void deletePosts() {
        postDao.deletePosts();
    }

    @Override
    public void updatePost(Post post) {
        postDao.update(post);
    }

    @Override
    public long checkPost(int id) {
        return postDao.checkPost(id);
    }
}
