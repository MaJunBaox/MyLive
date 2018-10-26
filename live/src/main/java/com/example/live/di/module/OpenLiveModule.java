package com.example.live.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.example.live.mvp.contract.OpenLiveContract;
import com.example.live.mvp.model.OpenLiveModel;


@Module
public class OpenLiveModule {
    private OpenLiveContract.View view;

    /**
     * 构建OpenLiveModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OpenLiveModule(OpenLiveContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OpenLiveContract.View provideOpenLiveView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OpenLiveContract.Model provideOpenLiveModel(OpenLiveModel model) {
        return model;
    }
}