package com.example.post.list.app.di;

import com.example.post.list.app.model.repository.PostRepositoryImpl;
import com.example.post.list.app.presentation.presenters.DescriptionPresenterImpl;
import com.example.post.list.app.presentation.presenters.PostContractDescriptionView;
import com.example.post.list.app.presentation.presenters.PostContractDescriptionView.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DescriptionPresenterModule {

    @Provides
    Presenter providesDescriptionPresenter(PostContractDescriptionView.View view, PostRepositoryImpl repository) {
        return new DescriptionPresenterImpl(view, repository);
    }
}
