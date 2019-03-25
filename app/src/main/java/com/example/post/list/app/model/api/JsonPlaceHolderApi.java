package com.example.post.list.app.model.api;

import com.example.post.list.app.model.api.dto.CommentDto;
import com.example.post.list.app.model.api.dto.PostDto;
import com.example.post.list.app.model.api.dto.UserDto;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * this interface defines the end point connections using {@link retrofit2.Retrofit}
 * @author fabian hoyos
 */
public interface JsonPlaceHolderApi {

    String SERVICE_END_POINT = "https://jsonplaceholder.typicode.com";

    @GET("/posts")
    Observable<List<PostDto>> requestPosts();

    @GET("/posts/{postId}/comments")
    Observable<List<CommentDto>> requestCommentsByPostId(@Path("postId") int postId);

    @GET("/users/{userId}")
    Observable<UserDto> requestUser(@Path("userId") int userId);

    @DELETE("/posts/{postId}")
    Observable<ResponseBody> deletePostbyId(@Path("postId") int postId);
}
