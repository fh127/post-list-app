package com.example.post.list.app.model.persistence.dao;

import android.content.Context;

import com.example.post.list.app.model.persistence.entities.Post;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * The Room database that contains the posts table
 * @author fabian hoyos
 */
@Database(entities = {Post.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase {

    private static volatile PostDatabase INSTANCE;

    public abstract PostDao postDao();

    public static PostDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PostDatabase.class) {
                if (INSTANCE == null) {
                   INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PostDatabase.class, "post.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}