package com.example.post.list.app.model.api.dto;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * this DTO class gets post from service response
 * @author fabian hoyos
 */
public class PostDto {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}



