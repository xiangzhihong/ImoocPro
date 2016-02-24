package com.open.imooc.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by xiangzhihong on 2015/12/28 on 15:27.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class McScrollView extends ScrollView {


    public McScrollView(Context context) {
        super(context, null);
    }

    public McScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public McScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0, 0);
    }

    public McScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
