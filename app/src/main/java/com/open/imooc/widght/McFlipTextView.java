package com.open.imooc.widght;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;


import com.open.imooc.R;
import com.open.imooc.bean.FlipModel;

import java.util.ArrayList;
import java.util.List;


public class McFlipTextView extends TextSwitcher implements TextSwitcher.ViewFactory {

    private Context mContext;
    private List<FlipModel> demoBeans = new ArrayList<>();
    private int mIndex;
    private ItemDataListener itemDataListener;
    private View view ;
    private static final int AUTO_RUN_FLIP_TEXT = 11;
    private static final int AUTO_TIME = 3500;

    public McFlipTextView(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public McFlipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    private void init(){
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_in));
        setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_bottom_out));
    }

    public View makeView() {
        View inflate=LayoutInflater.from(mContext).inflate(R.layout.flip_item_layout,null);
        return inflate;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUTO_RUN_FLIP_TEXT:
                    if(demoBeans.size()>0){
                        mHandler.sendEmptyMessageDelayed(AUTO_RUN_FLIP_TEXT, AUTO_TIME);
                        setText(demoBeans.get(mIndex).title);
                    }
                    mIndex++;
                    if(mIndex>demoBeans.size()-1){
                        mIndex=0;
                    }
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mIndex==0){
                                itemDataListener.onItemClick(demoBeans.size()-1);
                            }else{
                                itemDataListener.onItemClick(mIndex-1);
                            }
                        }
                    });
                    break;
            }
        }
    };

    public void setData(List<FlipModel> datas,ItemDataListener listener,View v){
        view = v;
        itemDataListener = listener;
        if(demoBeans.size()>0){
            demoBeans.clear();
        }
        demoBeans.addAll(datas);
        mIndex = 0;
        mHandler.removeMessages(AUTO_RUN_FLIP_TEXT);
        mHandler.sendEmptyMessage(AUTO_RUN_FLIP_TEXT);
    }


    public abstract interface ItemDataListener{
        public void onItemClick(int position);
    }
}
