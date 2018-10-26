package com.example.live.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.live.di.module.LoginModule;

import com.jess.arms.di.scope.ActivityScope;
import com.example.live.mvp.ui.activity.LoginActivity;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}