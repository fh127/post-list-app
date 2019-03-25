package com.example.post.list.app.presentation.presenters;

/**
 * @author fabian hoyos
 */
public interface BaseView {
    void onShowLoader(boolean show);

    void onMessage(String message);
}
