package com.example.post.list.app.app;

import android.app.Activity;
import android.app.Application;

import com.example.post.list.app.di.ApplicationComponent;
import com.example.post.list.app.di.DaggerApplicationComponent;

import javax.inject.Inject;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author fabian hoyos
 */
public class PostApplication extends Application implements HasActivityInjector {

    private ApplicationComponent applicationComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }
}