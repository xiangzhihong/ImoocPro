package com.open.imooc.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/**
 * Created by xiangzhihong on 2015/12/3 on 16:18.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MCVideoView extends VideoView {
    public MCVideoView(Context context) {
        super(context,null);
    }

    public MCVideoView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MCVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    public MCVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    public void playVideo(Context context, Uri paramUri)
    {
        if (paramUri == null)
            throw new IllegalArgumentException("Uri can not be null");
        setVideoURI(paramUri);
        start();
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });
    }

}
