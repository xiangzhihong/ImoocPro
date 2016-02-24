package com.open.imooc.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xiangzhihong on 2015/12/25 on 14:01.
 */
public class TeacherDetailsActivity extends BaseActivity {
    @InjectView(R.id.teacher_back)
    ImageButton teacherBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_layout);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
    }

    @OnClick(R.id.teacher_back)
    public void back(View view){
      finish();
    }
}
