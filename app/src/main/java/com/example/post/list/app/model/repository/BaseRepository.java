package com.example.post.list.app.model.repository;


import com.example.post.list.app.utils.NetworkUtils;


/**
 * This class is used to reuse general validations for repository requirements (services and local storage)
 * @author fabian hoyos
 */
public abstract class BaseRepository {

    protected NetworkUtils networkUtils;

    /**
     * this method executes the generic service request validating the internet connection
     *
     * @param t
     * @param function
     * @param <T>
     */
    protected <T> void executeService(T t, RequestExecutor<? super T> function) {
        if (!networkUtils.isNetworkConnection()) {
            onInternetLostConnection();
        } else {
            function.executor(t);
        }
    }


    /**
     * this abstract method is used to implement the intent connection lost.
     */
    protected abstract void onInternetLostConnection();


    /**
     * based {@link java.util.function.Function}
     * this functional interface is used to implement the specific logic for requests
     *
     * @param <T> generic parameter
     */
    @FunctionalInterface
    protected interface RequestExecutor<T> {
        void executor(T t);
    }

}
