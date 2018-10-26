package com.example.live.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.live.di.module.LookLiveModule;

import com.jess.arms.di.scope.ActivityScope;
import com.example.live.mvp.ui.activity.LookLiveActivity;

@ActivityScope
@Component(modules = LookLiveModule.class, dependencies = AppComponent.class)
public interface LookLiveComponent {
    void inject(LookLiveActivity activity);
}