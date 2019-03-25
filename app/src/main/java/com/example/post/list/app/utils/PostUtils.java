package com.example.post.list.app.utils;


import com.example.post.list.app.model.api.dto.CommentDto;
import com.example.post.list.app.model.api.dto.PostDto;
import com.example.post.list.app.model.api.dto.UserDto;
import com.example.post.list.app.model.persistence.entities.Comment;
import com.example.post.list.app.model.persistence.entities.Post;
import com.example.post.list.app.model.persistence.entities.User;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.example.post.list.app.utils.Constants.FAVORITE;
import static com.example.post.list.app.utils.Constants.NONE;
import static com.example.post.list.app.utils.Constants.PostState;


/**
 * this class contains different utilities to handle
 * the {@link com.example.post.list.app.model.persistence.entities.Post} object according the requirements
 */
public final class PostUtils {

    private PostUtils() {
    }

    private final static int MAX_LIST_VALUE_INITIAL_STATE = 20;


    public static Post buildPost(PostDto postDto, @PostState int state) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setBody(postDto.getBody());
        post.setTitle(postDto.getTitle());
        post.setState(state);
        post.setUserReferenceId(postDto.getUserId());
        return post;
    }

    public static List<Comment> buildComments(List<CommentDto> commentDtos) {
        List<Comment> comments = new ArrayList<>();
        for (CommentDto commentDto : commentDtos) {
            Comment comment = new Comment(commentDto.getName(), commentDto.getBody());
            comments.add(comment);
        }
        return comments;
    }


    public static User buildUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setWebsite(userDto.getWebsite());
        return user;
    }

    public static @PostState
    int getInitialState(long id) {
        return id > MAX_LIST_VALUE_INITIAL_STATE
                ? Constants.NONE
                : Constants.INITIAL_SELECTION;
    }

    public static boolean isValidUserDto(UserDto userDto, Post post) {
        return userDto != null && userDto.getId() == post.getUserReferenceId();
    }

    public static Post fillPost(Post post, UserDto userDto, List<CommentDto> commentDtos) {
        if (PostUtils.isValidUserDto(userDto, post)) {
            post.setUser(PostUtils.buildUser(userDto));
        }

        if (!CollectionsUtils.isEmpty(commentDtos)) {
            post.setComments(PostUtils.buildComments(commentDtos));
        }
        return post;
    }

    public static boolean validateUserAndComments(Post post) {
        return post.getUser() != null && !CollectionsUtils.isEmpty(post.getComments());
    }

    public static Post addRemoveTofavorites(Post post) {
        @PostState int state = post.getState() == NONE ? FAVORITE : NONE;
        post.setState(state);
        return post;
    }

    public @Nullable
    static Post findPostById(List<Post> list, int postId) {
        return Iterables.tryFind(list, input -> input.getId() == postId).orNull();
    }

}
