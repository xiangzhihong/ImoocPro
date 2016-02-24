package com.open.imooc.widght;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;

import java.lang.System;

import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by xiangzhihong on 2015/12/23 on 15:31.
 */
public class TextClock extends TextView {
    private static final String m12 = "h:mm:ss aa";
    private static final String m24 = "k:mm";
    Calendar mCalendar;
    String mFormat;
    private FormatChangeObserver mFormatChangeObserver;
    private Handler mHandler;
    private Runnable mTicker;
    private boolean mTickerStopped = false;

    public TextClock(Context paramContext) {
        super(paramContext);
        initClock();
    }

    public TextClock(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initClock();
    }

    private boolean get24HourMode() {
        return DateFormat.is24HourFormat(getContext());
    }

    private void initClock() {
        if (this.mCalendar == null)
            this.mCalendar = Calendar.getInstance();
        this.mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.mFormatChangeObserver);
        setFormat();
    }

    private void setFormat() {
        if (get24HourMode()) ;
        for (this.mFormat = "k:mm"; ; this.mFormat = "h:mm:ss aa")
            return;
    }

    protected void onAttachedToWindow() {
        this.mTickerStopped = false;
        super.onAttachedToWindow();
        this.mHandler = getHandler();
        this.mTicker = new Runnable() {
            public void run() {
                if (TextClock.this.mTickerStopped) ;
                while (true) {
                    TextClock.this.mCalendar.setTimeInMillis(System.currentTimeMillis());
                    TextClock.this.setText(DateFormat.format(TextClock.this.mFormat, TextClock.this.mCalendar));
                    TextClock.this.invalidate();
                    long l1 = SystemClock.uptimeMillis();
                    long l2 = l1 + (1000L - l1 % 1000L);
                    TextClock.this.mHandler.postAtTime(TextClock.this.mTicker, l2);
                    return;
                }
            }
        };
        this.mTicker.run();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTickerStopped = true;
        this.mHandler.removeCallbacks(this.mTicker);
        this.mHandler.obtainMessage().recycle();
        getContext().getContentResolver().unregisterContentObserver(this.mFormatChangeObserver);
    }

    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }

        public void onChange(boolean paramBoolean) {
            TextClock.this.setFormat();
        }
    }
}
