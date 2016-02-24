package com.open.imooc.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.base.McApplication;
import com.open.imooc.push.PushDemoReceiver;
import com.open.imooc.ui.main.utils.UIHelp;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.SharedUtil;
import com.open.imooc.utils.SystemBarTintManager;
import com.open.imooc.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener{

    @InjectView(R.id.main_guide)
     View guide;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.tabhost)
    FragmentTabHost tabhost;

    public static TextView tView = null;
    public static TextView tLogView = null;

    private Context context;
    private SystemBarTintManager barTintManager = null;
    private long lastBackTime=0;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    private void guide() {
        guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                guide.setVisibility(View.GONE);
                SharedUtil.newInstance(context).set(Contants.GUIDE, true);
            }
        });
        boolean flag = SharedUtil.newInstance(this).getBoolean(Contants.GUIDE, false);
        if (flag) {
            guide.setVisibility(View.GONE);
        } else {
            guide.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        context = MainActivity.this;
        guide();
        initTab();
        initParam();
        initPush();
    }

    private void initPush() {
        PushManager.getInstance().initialize(this.getApplicationContext());
        if (PushDemoReceiver.payloadData != null) {
        }
    }

    private void initTab() {
        barTintManager = Utils.initTintMgr(this, findViewById(R.id.main_view));
        tabhost.setup(this, getSupportFragmentManager(), R.id.framelayout);
//        tabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        tabhost.setOnTabChangedListener(this);
        View tabView = null;
        for (UIHelp pager : UIHelp.values()) {
            tabView = pager.getTabView(this);
            tabhost.addTab(tabhost.newTabSpec(pager.name()).setIndicator(tabView), pager.getPager(), null);
        }
    }

    private void initParam() {
        intent=getIntent();
        if (intent == null) return;
        int pagerIndex = intent.getIntExtra(Contants.CURR_FRAME_PAGER, -1);
        if (pagerIndex != -1) {
            tabhost.setCurrentTab(pagerIndex);
        }
    }


    @Override
    public void onTabChanged(String tabId) {
        //沉浸模式
        barTintManager.setStatusBarTintColor(getResources().getColor(UIHelp.getType(tabId).getStateBarColor()));
    }

    @Override
    public void onBackPressed() {
        long currentBackTime = System.currentTimeMillis();
        if (currentBackTime - lastBackTime > 2000) {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            lastBackTime = currentBackTime;
        } else {
            super.onBackPressed();
            McApplication.getInstance().quitApp();
            System.exit(0);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
