package com.example.live.mvp.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import com.example.live.di.component.DaggerLookLiveComponent;
import com.example.live.di.module.LookLiveModule;
import com.example.live.mvp.contract.LookLiveContract;
import com.example.live.mvp.model.entity.ZanBean;
import com.example.live.mvp.presenter.LookLivePresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LookLiveActivity extends BaseActivity<LookLivePresenter> implements LookLiveContract.View {

    @BindView(R.id.zanview)
    ZanView zanview;
    @BindView(R.id.xinxi)
    ImageView xinxi;
    @BindView(R.id.liwu)
    ImageView liwu;
    @BindView(R.id.aixin)
    ImageView aixin;
    @BindView(R.id.rl_lw)
    RelativeLayout rlLw;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.send_msg)
    Button sendMsg;
    @BindView(R.id.ll_et)
    LinearLayout llEt;
    @BindView(R.id.tan)
    ImageView tan;
    @BindView(R.id.tan1)
    ImageView tan1;
    private ZanBean mZanBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLookLiveComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .lookLiveModule(new LookLiveModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_look_live; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //2: 创建 Player
        //mPlayerView 即 step1 中添加的界面 view
        TXCloudVideoView mView = (TXCloudVideoView) findViewById(R.id.video_view);

        //创建 player 对象
        TXLivePlayer mLivePlayer = new TXLivePlayer(this);

        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mView);
        //step 3: 启动播放
        String flvUrl = "http://33092.liveplay.myqcloud.com/live/33092_8ca750f886.flv";
        mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV

        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
// 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
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

    @OnClick({R.id.xinxi, R.id.liwu, R.id.aixin, R.id.send_msg,R.id.tan, R.id.tan1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xinxi:
                editText.getText().clear();
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                rlLw.setVisibility(View.GONE);
                llEt.setVisibility(View.VISIBLE);
                break;
            case R.id.liwu:
                break;
            case R.id.aixin:
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
            case R.id.send_msg://发送消息
                String s = editText.getText().toString();
                Toast.makeText(this, "内容" + s, Toast.LENGTH_SHORT).show();
                rlLw.setVisibility(View.VISIBLE);
                llEt.setVisibility(View.GONE);
                break;
            case R.id.tan:
                tan1.setVisibility(View.VISIBLE);
                tan.setVisibility(View.GONE);
                Toast.makeText(this, "开启弹幕", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tan1:
                tan1.setVisibility(View.GONE);
                tan.setVisibility(View.VISIBLE);
                Toast.makeText(this, "关闭弹幕", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
