package com.example.post.list.app.presentation.presenters;

/**
 * @author fabian hoyos
 */
public abstract class BasePresenter<V> {

    protected V view;


    protected BasePresenter(V view) {
        this.view = view;
    }

    /**
     * Contains common setup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    public void init() {
    }

    /**
     * Contains common cleanup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    public void release() {
        view = null;
    }
}