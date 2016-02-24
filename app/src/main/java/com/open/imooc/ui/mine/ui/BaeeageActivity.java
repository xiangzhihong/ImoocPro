package com.open.imooc.ui.mine.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.ui.main.MainActivity;
import com.open.imooc.widght.barrage.BarrageView;

import java.util.Random;

/**
 * Created by xiangzhihong on 2016/1/7 on 14:37.
 */
public class BaeeageActivity extends BaseActivity {

    public static final int DELAY_TIME = 500;//随机弹幕的间隔时间
    private boolean isOnPause = false;
    private Random random = new Random();
    private Handler handler=new Handler();
    private String[] texts;
    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baeeage);

        init();
    }

    private void init() {
        initData();
    }

    private void initData() {
         texts = getResources().getStringArray(R.array.baeeage_array);
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        Runnable barrageView = new Runnable() {
            @Override
            public void run() {
                if (!isOnPause) {
                    //新建一条弹幕，并设置文字
                     BarrageView barrageView = new BarrageView(BaeeageActivity.this);
                    barrageView.setText(texts[random.nextInt(texts.length)]);
                    addContentView(barrageView, layoutParams);
                }
                handler.postDelayed(this, DELAY_TIME);
            }
        };
        handler.post(barrageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause=false;
    }
}
