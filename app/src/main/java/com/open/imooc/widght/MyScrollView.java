package com.open.imooc.widght;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private boolean isScroll = true;
    private boolean isScrollBottom = false;
    private float mTouchStartX;
    private float mTouchStartY;
    private Context context;
    private OnScrollListener onScrollListener;
    private int lastScrollY;

    public MyScrollView(Context context) {
        super(context);
        this.context = context;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public boolean isScrollBottom() {
        return isScrollBottom;
    }

    public void setScrollBottom(boolean isScrollBottom) {
        this.isScrollBottom = isScrollBottom;
    }

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = MyScrollView.this.getScrollY();

            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
            if(onScrollListener != null){
                onScrollListener.onScroll(scrollY);
            }

        }

    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        if(onScrollListener != null){
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = ev.getX();
                mTouchStartY = ev.getY();
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:

                if (this.getChildAt(0).getBottom() - this.getHeight()
                        - this.getScrollY() == 0) {

                }

                final float touchDistanceX = (ev.getX() - mTouchStartX);
                final float touchDistanceY = (ev.getY() - mTouchStartY);
                ViewConfiguration config = ViewConfiguration.get(context);
                int mTouchSlop = config.getScaledTouchSlop();
                if (Math.abs(touchDistanceX) >= (Math.abs(touchDistanceY))
                        || Math.abs(touchDistanceY) < mTouchSlop) {
                    return false;
                } else if (this.getChildAt(0).getBottom() - this.getHeight()
                        - this.getScrollY() == 0) {
                    isScrollBottom = true;
                    return isScroll;
                } else {
                    isScrollBottom = false;
                    return true;
                }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
                break;
        }

        return false;
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {

        return 0;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
    //滚动的回调接口
    public interface OnScrollListener{
        void onScroll(int scrollY);
    }

}
