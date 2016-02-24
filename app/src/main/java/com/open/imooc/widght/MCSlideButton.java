package com.open.imooc.widght;

import android.view.View;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.open.imooc.R;

/**
 * Created by xiangzhihong on 2016/1/7 on 11:52.
 */
public class MCSlideButton extends View implements View.OnClickListener {
    private boolean isDrag;
    private boolean isOn;
    private OnStateChanageListener listener;
    private Bitmap mOffBitmap;
    private Bitmap mOffBitmapBg;
    private Bitmap mOnBitmap;
    private Bitmap mOnBitmapBg;
    private Paint mPaint;
    private float startX;
    private float startY;

    public MCSlideButton(Context paramContext) {
        this(paramContext, null);
    }

    public MCSlideButton(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public MCSlideButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initView(paramAttributeSet);
    }

    private void changeState(boolean paramBoolean) {
        if (this.isOn == paramBoolean)
            return;
        this.isOn = paramBoolean;
        if (this.listener == null)
            return;
        this.listener.onStateChange(this);
    }

    private void initView(AttributeSet paramAttributeSet) {
        TypedArray localTypedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.SlideButton);
        int i = localTypedArray.getResourceId(R.styleable.SlideButton_sildeButtonOff, 0);
        int j = localTypedArray.getResourceId(R.styleable.SlideButton_sildeIsOn, 0);
        int k = localTypedArray.getResourceId(R.styleable.SlideButton_sildeOffBg, 0);
        int l = localTypedArray.getResourceId(R.styleable.SlideButton_slideOnBg, 0);
        Resources localResources = getResources();
        this.mOffBitmap = BitmapFactory.decodeResource(localResources, i);
        this.mOnBitmap = BitmapFactory.decodeResource(localResources, j);
        this.mOffBitmapBg = BitmapFactory.decodeResource(localResources, k);
        this.mOnBitmapBg = BitmapFactory.decodeResource(localResources, l);
        this.isOn = localTypedArray.getBoolean(4, false);
        if (this.isOn) ;
        for (int i1 = this.mOffBitmapBg.getWidth() - this.mOffBitmap.getWidth(); ; i1 = 0) {
            this.startX = i1;
            this.startY = ((this.mOffBitmap.getHeight() - this.mOffBitmapBg.getHeight()) / 2.0F);
            setOnClickListener(this);
            setTag(Boolean.valueOf(false));
            this.mPaint = new Paint();
            this.mPaint.setAntiAlias(true);
            localTypedArray.recycle();
            return;
        }
    }

    private void refreshView() {
        invalidate();
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void onClick(View paramView) {

    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mOffBitmap.recycle();
        this.mOnBitmap.recycle();
        this.mOffBitmap = null;
        this.mOnBitmap = null;
        this.mPaint = null;
    }

    protected void onDraw(Canvas paramCanvas) {
        if (this.isOn) {
            paramCanvas.drawBitmap(this.mOnBitmapBg, 0.0F, this.startY, this.mPaint);
            paramCanvas.drawBitmap(this.mOnBitmap, this.startX, 0.0F, this.mPaint);
        }
        while (true) {
            paramCanvas.drawBitmap(this.mOffBitmapBg, 0.0F, this.startY, this.mPaint);
            paramCanvas.drawBitmap(this.mOffBitmap, this.startX, 0.0F, this.mPaint);
            return;
        }
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        setMeasuredDimension(this.mOffBitmapBg.getWidth(), this.mOffBitmap.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
        }
        return super.onTouchEvent(event);
    }

    public void setChecked(boolean paramBoolean) {

    }

    public void setOnStateChanageListener(OnStateChanageListener paramOnStateChanageListener) {
        this.listener = paramOnStateChanageListener;
    }

    public static abstract interface OnStateChanageListener {
        public abstract void onStateChange(MCSlideButton paramMCSlideButton);
    }
}
