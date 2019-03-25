package com.example.post.list.app.model.api;

import com.example.post.list.app.model.api.dto.CommentDto;
import com.example.post.list.app.model.api.dto.PostDto;
import com.example.post.list.app.model.api.dto.UserDto;

import java.util.List;

import io.reactivex.Observable;

/**
 * this interface defines the communication of network layer
 * @author fabian hoyos
 */
public interface ServiceApiManager {

    void getPosts(OnPost listener);

    void removePost(int postId, OnRemovedPost listener);

    Observable<List<CommentDto>> getCommentsByPostId(int postId);

    Observable<UserDto> getUser(int userId);

    void clear();


    interface OnPost {
        void onPostList(List<PostDto> postDtos);
    }

    interface OnRemovedPost {
        void onRemovedPost();

        void onError();
    }
}
