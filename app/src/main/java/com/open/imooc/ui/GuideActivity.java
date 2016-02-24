package com.open.imooc.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.base.McApplication;
import com.open.imooc.ui.fragment.MCGuideFragment;
import com.open.imooc.ui.main.MainActivity;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.SharedUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangzhihong on 2015/12/3 on 15:27.
 */
public class GuideActivity extends BaseActivity implements OnClickListener,OnPageChangeListener {

    public static final String PAGE_ALL = "all";
    public static final String PAGE_SINGLE = "single";
    private ImageView[] images;
    private boolean isNeedRefresh = false;
    private FragmentPagerAdapter mAdapter;
    private ObjectAnimator mAnim;
    private int mCurrentPage = 0;
    private LinearLayout mDotLayout;
    private TextView mEnter;
    private List<Fragment> mList = new ArrayList();
    private TextView mLogin;
    private ViewPager mViewPager;
    private int pageSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.guide_layout);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = ((ViewPager) findViewById(R.id.viewpager));
        mDotLayout = ((LinearLayout) findViewById(R.id.dot_layout));
        mLogin = ((TextView) findViewById(R.id.login));
        mEnter = ((TextView) findViewById(R.id.enter));
        mLogin.setOnClickListener(this);
        mEnter.setOnClickListener(this);
        //init
        SharedUtil.newInstance(this).set(Contants.IS_FIRST_USE, false);
    }

    private void initData() {
        MCGuideFragment localMCGuideFragment1 = new MCGuideFragment();
        Bundle bundel = new Bundle();
        bundel.putInt("index", 1);
        localMCGuideFragment1.setArguments(bundel);
        MCGuideFragment localMCGuideFragment2 = new MCGuideFragment();
        bundel = new Bundle();
        bundel.putInt("index", 2);
        localMCGuideFragment2.setArguments(bundel);
        MCGuideFragment localMCGuideFragment3 = new MCGuideFragment();
        bundel = new Bundle();
        bundel.putInt("index", 3);
        localMCGuideFragment3.setArguments(bundel);
        mList.add(localMCGuideFragment1);
        mList.add(localMCGuideFragment2);
        mList.add(localMCGuideFragment3);
        mAdapter = new GuideFragmentPagerAdapter(getSupportFragmentManager(), mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(this);
        pageSize = mList.size();
        if (pageSize != 1) {
            images = new ImageView[pageSize];
            initDot(pageSize);
            images[mCurrentPage].setEnabled(true);
            return;
        }
        onPageSelected(pageSize);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("WelComeActivity", "当前 WelComeActivity:" + position);
        View localView = findViewById(R.id.button_layout);

        setCurrentDot(position);
        if (position == 0) {
            localView.setVisibility(View.GONE);
        } else if (position == 1) {
            localView.setVisibility(View.GONE);
        } else {
            localView.setVisibility(View.VISIBLE);
            fadeInAnim(localView);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void setCurrentDot(int paramInt) {
        if ((paramInt < 0) || (paramInt >= mList.size())) ;
        while (true) {
            images[paramInt].setEnabled(true);
            images[mCurrentPage].setEnabled(false);
            mCurrentPage = paramInt;
            return;
        }
    }

    //fragment adapter
    private class GuideFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> datas;

        public GuideFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public GuideFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            datas=list;
        }

        @Override
        public Fragment getItem(int pos) {
            if (datas != null) ;
            for (Fragment localFragment = (Fragment) datas.get(pos); ; localFragment = null)
                return localFragment;
        }

        @Override
        public int getCount() {
            if ((datas != null) && (datas.size() != 0)) ;
            for (int i = datas.size(); ; i = 0)
                return i;
        }

    }

    private void fadeInAnim(View paramView)
    {
        if (Build.VERSION.SDK_INT < 11)
            return;
        float[] arrayOfFloat = new float[2];
        arrayOfFloat[0] = 0.0F;
        arrayOfFloat[1] = 1.0F;
        mAnim = ObjectAnimator.ofFloat(paramView, "alpha", arrayOfFloat).setDuration(1000L);
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.play(mAnim);
        localAnimatorSet.start();
    }

    private void initDot(int paramInt)
    {
        for (int i = 0; ; ++i)
        {
            if (i >= paramInt)
                return;
            ImageView localImageView = new ImageView(this);
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
            if (i != 0)
                localLayoutParams.leftMargin = 30;
            localImageView.setLayoutParams(localLayoutParams);
            localImageView.setImageResource(R.drawable.dot_bg);
            localImageView.setEnabled(false);
            images[i] = localImageView;
            mDotLayout.addView(localImageView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                goMainActivity();
                break;

            case R.id.enter:
                goMainActivity();
                break;
        }
    }

    private void goMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isNeedRefresh = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNeedRefresh)
            return;
        mViewPager.setAdapter(mAdapter);
        if (pageSize <= 1)
            return;
        images[mCurrentPage].setEnabled(true);
        mViewPager.setCurrentItem(mCurrentPage);
    }
}
