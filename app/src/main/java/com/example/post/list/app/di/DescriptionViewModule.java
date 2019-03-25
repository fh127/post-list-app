package com.example.post.list.app.di;

import com.example.post.list.app.presentation.view.DescriptionActivity;
import com.example.post.list.app.presentation.presenters.PostContractDescriptionView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DescriptionViewModule {

    @Binds
    abstract PostContractDescriptionView.View providesDescriptionView(DescriptionActivity descriptionActivity);
}
