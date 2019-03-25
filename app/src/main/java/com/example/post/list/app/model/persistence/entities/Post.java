package com.example.post.list.app.model.persistence.entities;



import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import io.reactivex.annotations.NonNull;


/**
 * This class model is used for local storage and business logic
 * @author fabian hoyos
 */
@Entity(tableName = "posts")
public class Post implements Parcelable {

    @NonNull
    @PrimaryKey
    private int id;
    private String title;
    private String body;
    private int state;
    private int userReferenceId;
    @Embedded
    private User user;
    @ColumnInfo(name = "ListComment")
    @TypeConverters(DataTypeConverter.class)
    private List<Comment> comments;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getState() {
        return state;
    }

    public int getUserReferenceId() {
        return userReferenceId;
    }

    public void setUserReferenceId(int userReferenceId) {
        this.userReferenceId = userReferenceId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.comments);
        dest.writeInt(this.state);
        dest.writeInt(this.userReferenceId);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.body = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.comments = in.createTypedArrayList(Comment.CREATOR);
        this.state = in.readInt();
        this.userReferenceId = in.readInt();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
