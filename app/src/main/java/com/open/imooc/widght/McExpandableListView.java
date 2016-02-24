package com.open.imooc.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by xiangzhihong on 2015/12/28 on 15:59.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class McExpandableListView extends ExpandableListView {
    public McExpandableListView(Context context) {
        super(context,null);
    }

    public McExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public McExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);
    }

    public McExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(  Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
