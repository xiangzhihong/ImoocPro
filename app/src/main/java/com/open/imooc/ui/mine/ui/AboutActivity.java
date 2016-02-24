package com.open.imooc.ui.mine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xiangzhihong on 2016/1/7 on 10:56.
 */
public class AboutActivity extends BaseActivity {

    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.back)
    public void backClick(View view) {
        finish();
    }

}
