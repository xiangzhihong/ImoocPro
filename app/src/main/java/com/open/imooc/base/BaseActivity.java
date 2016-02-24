package com.open.imooc.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.open.imooc.listener.BackGestureListener;

import java.io.Serializable;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity {
    private Handler mHandler;
    /** 手势监听 */
    GestureDetector mGestureDetector;
    private boolean mNeedBackGesture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        McApplication.getInstance().addActivity(this);
        initGestureDetector();
    }

    private void initGestureDetector() {
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(getApplicationContext(),
                    new BackGestureListener(this));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mNeedBackGesture){
            return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setNeedBackGesture(boolean mNeedBackGesture){
        this.mNeedBackGesture = mNeedBackGesture;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }

    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    public void doBack(View view) {
        onBackPressed();
    }

}
