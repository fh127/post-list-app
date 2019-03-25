package com.example.post.list.app.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public final class Constants {


    private Constants() {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            NONE,
            INITIAL_SELECTION,
            FAVORITE
    })
    public @interface PostState {
    }

    // post states
    public static final int NONE = 0;
    public static final int INITIAL_SELECTION = 1;
    public static final int FAVORITE = 2;

    // key filter option
    public static final int ALL_OPTION = 1;
    public static final int FAVORITE_OPTION = 2;
    public static final int UNKNOWN_OPTION = -1;

    // error messages
    public static final String ERROR_POST_REQUEST = "There is an error connection, Try again";
    public static final String ERROR_POTS_COMMENT_REQUEST = "There is an error connection to get Comments or User infor, Try again";
    public static final String ERROR_UNKNOWN_POST = "There is an UnKnown Error";
    public static final String ERROR_DELETE_POSTS = "Can't Remove the post or posts";
    public static final String ERROR_SAVE_POST = "Can't Save Post";
    public static final String ERROR_UPDATE_POST = "Can't update Post";
    public static final String ERROR_FAVORITE_POST = "There aren't favorite posts";
    public static final String ERROR_INTERNET_CONECTION_LOST = "Internet connection lost";

    // Key
    public static final String MENU_ITEM_OPTION = "menu_option";
    public static final String MENU_ITEM_ID = "menu_id";
    public static final String SELECTED_POST_KEY = "post_key";
    public static final String PARCELABLE_STATE_LIST = "PARCELABLE_STATE_LIST";
    public static final int REQUEST_CODE = 100;
}
