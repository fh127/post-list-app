package com.example.post.list.app.di;

import com.example.post.list.app.app.PostApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, BuildersModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(PostApplication application);

        ApplicationComponent build();
    }

    void inject(PostApplication postApplication);

}
