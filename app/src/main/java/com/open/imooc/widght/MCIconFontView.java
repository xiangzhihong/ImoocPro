package com.open.imooc.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.open.imooc.R;


/**
 * Created by xiangzhihong on 2015/12/22 on 11:07.
 */
public class MCIconFontView extends TextView implements View.OnClickListener{
    private boolean isClick;
    private OnIconFontClickListener mListener;

    public MCIconFontView(Context paramContext)
    {
        this(paramContext, null);
    }

    public MCIconFontView(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public MCIconFontView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
//        TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Iconfont);
//        this.isClick = localTypedArray.getBoolean(0, true);
//        localTypedArray.recycle();
        init(paramContext);
    }

    private void init(Context paramContext)
    {
        setTypeface(Typeface.createFromAsset(paramContext.getAssets(), "iconfont.ttf"));
        if (!this.isClick)
            return;
        setOnClickListener(this);
    }

    public void onClick(View paramView)
    {
        if (this.mListener == null)
            return;
        this.mListener.onClick(this);
    }

    public void setIconFontColor(int paramInt)
    {
        setTextColor(paramInt);
    }

    public void setIconFontSize(float paramFloat)
    {
        setTextSize(paramFloat);
    }

    public void setIconFontSize(int paramInt, float paramFloat)
    {
        setTextSize(paramInt, paramFloat);
    }

    public void setOnIconFontClickListener(OnIconFontClickListener paramOnIconFontClickListener)
    {
        this.mListener = paramOnIconFontClickListener;
    }

    public static abstract interface OnIconFontClickListener
    {
        public abstract void onClick(MCIconFontView paramMCIconFontView);
    }
}
