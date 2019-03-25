package com.example.post.list.app.di;

import com.example.post.list.app.business.FilterMenuHandler;
import com.example.post.list.app.model.repository.PostRepositoryImpl;
import com.example.post.list.app.presentation.presenters.MainPresenterImpl;
import com.example.post.list.app.presentation.presenters.PostContractMainView;
import com.example.post.list.app.presentation.presenters.PostContractMainView.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainPresenterModule {

    @Provides
    Presenter providesMainPresenter(PostContractMainView.View view, PostRepositoryImpl repository, FilterMenuHandler filterMenuUseCase) {
        return new MainPresenterImpl(view, repository, filterMenuUseCase);
    }
}
