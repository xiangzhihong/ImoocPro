package com.open.imooc.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.open.imooc.R;
import com.open.imooc.utils.Utils;

public class CountView extends View {
    private Paint textPaint = null;
    private Paint bgPaint = null;
    private String text = null;
    private Shape shape = Shape.POINT;
    private float width = 0;
    private float height = 0;
    private float padding = 12;
    private float radius = 8;
    private float textWidth, textHeight;
    private Paint.FontMetrics fontMetrics = null;
    private float baselineY = 0;

    private int textColor, bgColor ,textSize;

    public CountView(Context context) {
        super(context);
        init();
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(attrs, 0);
        init();
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CountView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributeSet(attrs, defStyleAttr);
        init();
    }

    private void initAttributeSet(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountView, defStyle, 0);
        bgColor = typedArray.getColor(R.styleable.CountView_bgColor, Color.parseColor("#FF0000"));
        textColor = typedArray.getColor(R.styleable.CountView_textColor, Color.parseColor("#FFFFFF"));
        textSize = (int) typedArray.getDimension(R.styleable.CountView_textSize, Utils.dip2px(getContext(), 12));
        padding = (int) typedArray.getDimension(R.styleable.CountView_padding, Utils.dip2px(getContext(), 10));
        radius = (int) typedArray.getDimension(R.styleable.CountView_pointRadius, Utils.dip2px(getContext(), 5));
        typedArray.recycle();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAlpha(255);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = textPaint.getFontMetrics();
    }

    public void setCount(int count) {
        if (count <= 0) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
        }
        if (count < 10) {
            shape = Shape.CICLE;
        } else {
            shape = Shape.OVAL;
        }
        text = String.valueOf(count);
        if (count > 99) {
            text = "99+";
            textPaint.setTextSize(textSize-1);
        }
        initTextAttribute();
        invalidate();
        requestLayout();
    }

    private void initTextAttribute() {
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
    }

    public void showPoint() {
        shape = Shape.POINT;
        setVisibility(View.VISIBLE);
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        switch (shape) {
            case POINT:
                width = 2 * radius;
                height = 2 * radius;
                break;
            case CICLE:
                float cell = textWidth > textHeight ? textWidth : textHeight;
                width = cell + padding;
                height = width;
                break;
            case OVAL:
                height = textHeight + padding;
                width = textWidth + textHeight;
                break;
        }
        setMeasuredDimension((int) width, (int) height);
        baselineY = (height - fontMetrics.ascent - fontMetrics.descent) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (shape) {
            case POINT:
                drawPoint(canvas);
                break;
            case CICLE:
                drawCicle(canvas);
                break;
            case OVAL:
                drawOval(canvas);
                break;
        }
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, radius, bgPaint);
    }

    private void drawCicle(Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, height / 2, bgPaint);
        canvas.drawText(text, width / 2, baselineY, textPaint);
    }

    private void drawOval(Canvas canvas) {
        Path path = new Path();
        float radius = height / 2;
        path.addCircle(radius, radius, radius, Path.Direction.CW);
        path.addCircle(width - radius, radius, radius, Path.Direction.CW);
        RectF rectF = new RectF(radius, 0, width - radius, height);
        path.addRect(rectF, Path.Direction.CW);
        canvas.drawPath(path, bgPaint);
        canvas.drawText(text, rectF.centerX(), baselineY, textPaint);
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        bgPaint.setColor(bgColor);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
    }

    public enum Shape {
        POINT, CICLE, OVAL
    }
}
