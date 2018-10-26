package com.example.live.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.live.R;
import com.example.live.di.component.DaggerLoginComponent;
import com.example.live.di.module.LoginModule;
import com.example.live.mvp.contract.LoginContract;
import com.example.live.mvp.presenter.LoginPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.rtmp.TXLiveBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.openlive)
    Button openlive;
    @BindView(R.id.looklive)
    Button looklive;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);
        Toast.makeText(this, "liteav sdk version is : " + sdkver, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.openlive, R.id.looklive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.openlive:
                Toast.makeText(this, "开直播", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,OpenLiveActivity.class));
                break;
            case R.id.looklive:
                Toast.makeText(this, "看直播", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,LookLiveActivity.class));
                break;
        }
    }
}
