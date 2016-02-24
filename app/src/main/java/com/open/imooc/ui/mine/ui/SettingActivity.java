package com.open.imooc.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiangzhihong on 2016/1/7 on 10:56.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.back)
    public void backClick(View view){
         finish();
    }

    @OnClick (R.id.baeeage)
    public void baeeageClick(View view){
        startActivity(new Intent(this,BaeeageActivity.class));
    }

    @OnClick (R.id.invest_friend)
    public void inveteClick(View view){
        startActivity(new Intent(this,ContactActivity.class));
    }

}
