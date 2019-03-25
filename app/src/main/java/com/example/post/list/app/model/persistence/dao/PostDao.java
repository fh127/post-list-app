package com.example.post.list.app.model.persistence.dao;


import com.example.post.list.app.model.persistence.entities.Post;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

/**
 * this interface defines the database operations using {@link androidx.room.Room}
 * @author fabian hoyos
 */
@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPost(Post post);

    @Query("SELECT * FROM posts")
    Flowable<List<Post>> getPosts();

    @Query("SELECT id FROM posts WHERE id = :id")
    long checkPost(int id);

    @Query("SELECT * FROM posts WHERE id = :id LIMIT 1")
    Flowable<Post> findPostById(int id);

    @Query("SELECT * FROM posts WHERE state = :state")
    Flowable<List<Post>> findPostListByState(int state);

    @Query("DELETE FROM posts WHERE id = :id")
    void deletePost(int id);

    @Query("DELETE FROM posts")
    void deletePosts();

    @Update
    void update(Post post);

}
