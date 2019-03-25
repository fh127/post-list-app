package com.example.post.list.app.di;

import android.content.Context;

import com.example.post.list.app.app.PostApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Context providesContext(PostApplication application) {
        return application.getApplicationContext();
    }


}
