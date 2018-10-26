package com.example.live.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.example.live.mvp.contract.LookLiveContract;
import com.example.live.mvp.model.LookLiveModel;


@Module
public class LookLiveModule {
    private LookLiveContract.View view;

    /**
     * 构建LookLiveModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LookLiveModule(LookLiveContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LookLiveContract.View provideLookLiveView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LookLiveContract.Model provideLookLiveModel(LookLiveModel model) {
        return model;
    }
}