package com.example.post.list.app.model.persistence.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class model is used for local storage and business logic
 * @author fabian hoyos
 */
public class Comment implements Parcelable {
    private String name;
    private String body;

    public Comment(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.body);
    }

    protected Comment(Parcel in) {
        this.name = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
