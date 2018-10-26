package com.example.live.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.live.di.module.OpenLiveModule;

import com.jess.arms.di.scope.ActivityScope;
import com.example.live.mvp.ui.activity.OpenLiveActivity;

@ActivityScope
@Component(modules = OpenLiveModule.class, dependencies = AppComponent.class)
public interface OpenLiveComponent {
    void inject(OpenLiveActivity activity);
}