package com.example.post.list.app.di;

import com.example.post.list.app.presentation.view.MainActivity;
import com.example.post.list.app.presentation.presenters.PostContractMainView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainViewModule {

    @Binds
    abstract PostContractMainView.View providesMainView(MainActivity mainActivity);
}
