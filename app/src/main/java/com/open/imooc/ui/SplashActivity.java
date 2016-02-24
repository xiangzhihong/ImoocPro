package com.open.imooc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.base.McApplication;
import com.open.imooc.base.imageCache.ImageCache;
import com.open.imooc.ui.main.MainActivity;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.SharedUtil;
import com.open.imooc.widght.WebActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xiangzhihong on 2015/12/10 on 11:33.
 */
public class SplashActivity extends BaseActivity {
    @InjectView(R.id.copyright)
    TextView copyright;
    @InjectView(R.id.splash_image)
    ImageView splashImage;
    @InjectView(R.id.step_over)
    Button stepOver;
    @InjectView(R.id.splash_timeCount)
    TextView splashTimeCount;

    private String imagePath = "http://img.mukewang.com/5667974a0001f89b07201062.jpg";
    private String imageUrl = "http://www.imooc.com/wenda/detail/300876";
    private Handler mHandler = new Handler();
    int retryCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        ImageCache.display(this, imagePath, splashImage);
        new Thread(new cutdown()).start();
    }

    @OnClick(R.id.step_over)
    public void stepOverClick(View view) {
        startActivity(new Intent(this, GuideActivity.class));
    }

    @OnClick(R.id.splash_image)
    public void webClick(View view) {
        Intent intent=new Intent(this, WebActivity.class);
        intent.putExtra("title", "慕课网广告");
        intent.putExtra("url",imageUrl);
        startActivity(intent);
    }

    class cutdown implements Runnable {
        @Override
        public void run() {
            while (retryCount > 0) {
                retryCount--;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //显示剩余时间
                        splashTimeCount.setText(retryCount + "s");
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retryCount <= 0) {
                    jumpPage();

                }
            }
        }
    }

    private void jumpPage() {
        boolean flag = SharedUtil.newInstance(this).getBoolean(Contants.IS_FIRST_USE, true);
        if (flag) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    }

    ;

}
