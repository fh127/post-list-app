package com.example.post.list.app.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.SparseIntArray;

import com.example.post.list.app.R;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import static com.example.post.list.app.utils.Constants.ALL_OPTION;
import static com.example.post.list.app.utils.Constants.FAVORITE;
import static com.example.post.list.app.utils.Constants.FAVORITE_OPTION;
import static com.example.post.list.app.utils.Constants.INITIAL_SELECTION;
import static com.example.post.list.app.utils.Constants.NONE;
import static com.example.post.list.app.utils.Constants.PostState;

public final class ViewUtils {

    private ViewUtils() {
    }

    // post states icons
    private static final SparseIntArray resourceStateMap = new SparseIntArray();

    static {
        resourceStateMap.put(NONE, R.mipmap.default_icon);
        resourceStateMap.put(INITIAL_SELECTION, R.mipmap.blue_dot);
        resourceStateMap.put(FAVORITE, R.mipmap.favorite_icon);
    }

    // post states icons
    private static final SparseIntArray resourceFavoriteStateMap = new SparseIntArray();

    static {
        resourceFavoriteStateMap.put(NONE, android.R.drawable.btn_star_big_off);
        resourceFavoriteStateMap.put(FAVORITE, R.mipmap.favorite_icon);
    }

    // filter ui options
    public static final SparseIntArray filterMenuUiOption = new SparseIntArray();

    static {
        filterMenuUiOption.put(R.id.action_all, ALL_OPTION);
        filterMenuUiOption.put(R.id.action_favorite, FAVORITE_OPTION);
    }

    public static @DrawableRes
    int getResourceFavoriteState(@PostState int state) {
        return resourceFavoriteStateMap.get(state);
    }

    public static @DrawableRes
    int getResourceState(@PostState int state) {
        return resourceStateMap.get(state);
    }

    public static Spannable getSpan(String message, @ColorInt int color, int typeface) {
        SpannableString spanString = new SpannableString(message);
        spanString.setSpan(new ForegroundColorSpan(color), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(typeface), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

}
