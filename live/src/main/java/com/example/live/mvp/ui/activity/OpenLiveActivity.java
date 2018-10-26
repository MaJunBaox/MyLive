package com.example.live.mvp.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.live.R;
import com.example.live.di.component.DaggerOpenLiveComponent;
import com.example.live.di.module.OpenLiveModule;
import com.example.live.mvp.contract.OpenLiveContract;
import com.example.live.mvp.model.entity.ZanBean;
import com.example.live.mvp.presenter.OpenLivePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.tencent.rtmp.TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION;


public class OpenLiveActivity extends BaseActivity<OpenLivePresenter> implements OpenLiveContract.View {

    @BindView(R.id.xinxi)
    ImageView xinxi;
    @BindView(R.id.aixin)
    ImageView aixin;
    @BindView(R.id.xiangji)
    ImageView xiangji;
    @BindView(R.id.liwu)
    ImageView liwu;
    @BindView(R.id.zanview)
    ZanView zanview;
    @BindView(R.id.rl_lw)
    RelativeLayout rlLw;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.ll_et)
    LinearLayout llEt;
    @BindView(R.id.send_msg)
    Button sendMsg;
    private TXLivePushConfig mLivePushConfig;
    private boolean mHWVideoEncode;
    private TXLivePusher mMLivePusher;
    private boolean mTouchFocus;
    private ZanBean mZanBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOpenLiveComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .openLiveModule(new OpenLiveModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_open_live; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //1 创建 Pusher 对象
        mMLivePusher = new TXLivePusher(OpenLiveActivity.this);
        mLivePushConfig = new TXLivePushConfig();
        mMLivePusher.setConfig(mLivePushConfig);
        //2.启动推流
        String rtmpUrl = "rtmp://33092.livepush.myqcloud.com/live/33092_e7f78f5fff?bizid=33092&txSecret=6dcec0b80073c5541e99ba5e810e622d&txTime=5BD096FF";
        mMLivePusher.startPusher(rtmpUrl);
        //3.初始化控件
        TXCloudVideoView mCaptureView = (TXCloudVideoView) findViewById(R.id.video_view);
        mMLivePusher.startCameraPreview(mCaptureView);
        // 4: 设定清晰度
        mMLivePusher.setVideoQuality(VIDEO_QUALITY_HIGH_DEFINITION, false, false);
        //5: 美颜滤镜
        mMLivePusher.setBeautyFilter(1, 5, 5, 5);
        //setSpecialRatio 接口则可以设置滤镜的程度，从 0 到 1，越大滤镜效果越明显，默认取值 0.5。
        /*Bitmap bmp = null;
        bmp = decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp);
        if (mMLivePusher != null) {
            mMLivePusher.setFilter(bmp);
        }*/
        //8: 硬件编码
        if (mHWVideoEncode) {
            if (mLivePushConfig != null) {
                if (Build.VERSION.SDK_INT < 18) {
                    Toast.makeText(getApplicationContext(), "硬件加速失败，当前手机 API 级别过低（最低 18）",
                            Toast.LENGTH_SHORT).show();
                    mHWVideoEncode = false;
                }
            }
        }

        mLivePushConfig.setHardwareAcceleration(mHWVideoEncode ?
                TXLiveConstants.ENCODE_VIDEO_HARDWARE : TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
        mMLivePusher.setConfig(mLivePushConfig);
        // 如果你不清楚要何时开启硬件加速, 建议设置为 ENCODE_VIDEO_AUTO
        // 默认是启用软件编码, 但手机 CPU 使用率超过 80% 或者帧率 <= 10, SDK 内部会自动切换为硬件编码

        //摄像头自动或手动对焦
        mLivePushConfig.setTouchFocus(mTouchFocus);
        mMLivePusher.setConfig(mLivePushConfig);
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

    @OnClick({R.id.xinxi, R.id.aixin, R.id.xiangji, R.id.liwu, R.id.send_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xinxi://发送信息
                editText.getText().clear();
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                rlLw.setVisibility(View.GONE);
                llEt.setVisibility(View.VISIBLE);
                break;
            case R.id.aixin://点击爱心
                Random random = new Random();
                int i = random.nextInt(4);
                switch (i) {
                    case 0:
                        mZanBean = new ZanBean(BitmapFactory.decodeResource(getResources(), R.drawable.xin1), zanview);
                        break;
                    case 1:
                        mZanBean = new ZanBean(BitmapFactory.decodeResource(getResources(), R.drawable.xin2), zanview);
                        break;
                    case 2:
                        mZanBean = new ZanBean(BitmapFactory.decodeResource(getResources(), R.drawable.xin3), zanview);
                        break;
                    case 3:
                        mZanBean = new ZanBean(BitmapFactory.decodeResource(getResources(), R.drawable.xin4), zanview);
                        break;
                    case 4:
                        mZanBean = new ZanBean(BitmapFactory.decodeResource(getResources(), R.drawable.xin5), zanview);
                        break;
                }
                zanview.addZanXin(mZanBean);
                aixin.setImageResource(R.drawable.heart);
                break;
            case R.id.xiangji://切换相机
                // 默认是前置摄像头
                mMLivePusher.switchCamera();
                break;
            case R.id.liwu://礼物
                break;
            case R.id.send_msg://发送消息
                String s = editText.getText().toString();
                Toast.makeText(this, "内容"+s, Toast.LENGTH_SHORT).show();
                rlLw.setVisibility(View.VISIBLE);
                llEt.setVisibility(View.GONE);
                break;
        }
    }
}
