package com.example.post.list.app.di;

import com.example.post.list.app.presentation.view.DescriptionActivity;
import com.example.post.list.app.presentation.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {MainViewModule.class, MainPresenterModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {DescriptionViewModule.class, DescriptionPresenterModule.class})
    abstract DescriptionActivity bindDescriptionActivity();

    // Add bindings for other sub-components here
}