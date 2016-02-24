package com.open.imooc.ui.cource.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.imooc.R;
import com.open.imooc.base.imageCache.ImageCache;
import com.open.imooc.ui.cource.bean.CourceList.CourceData;
import com.open.imooc.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourceBottomView extends LinearLayout {
    @InjectView(R.id.item_image)
    ImageView itemImage;
    @InjectView(R.id.item_name)
    TextView itemName;
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.item_tv)
    TextView itemTv;
    @InjectView(R.id.item_count)
    LinearLayout itemCount;
    @InjectView(R.id.item_update_tv)
    TextView itemUpdateTv;

    private Context mContext;
    private String tagFrom;
    private LayoutParams params;

    public CourceBottomView(Context context, List<CourceData> data) {
        this(context, data, "");
    }

    public CourceBottomView(Context context, List<CourceData> data, String tagFrom) {
        super(context);
        this.mContext = context;
        this.tagFrom = tagFrom;
        init(data);
    }

    public CourceBottomView(Context context, AttributeSet attrs, List<CourceData> data) {
        this(context, attrs, data, "");
    }

    public CourceBottomView(Context context, AttributeSet attrs, List<CourceData> data,
                            String tagFrom) {
        super(context, attrs);
        this.mContext = context;
        this.tagFrom = tagFrom;
        init(data);
    }

    public CourceBottomView(Context context) {
        this(context, "");
    }

    public CourceBottomView(Context context, String tagFrom) {
        super(context);
        this.mContext = context;
        this.tagFrom = tagFrom;
    }

    public void setTagFrom(String tagFrom) {
        this.tagFrom = tagFrom;
    }

    private void paramsValue() {
        if (params == null) {
            int width = Utils.getScreenWidth((Activity) mContext);
            params = new LayoutParams(width, (int) (width / 2.37 + 0.5));
        }

    }

    public void init(final List<CourceData> data) {
        paramsValue();
        this.setBackgroundColor(mContext.getResources().getColor(
                R.color.white_FFFFFF));
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0; i < data.size(); i++) {
            final CourceData info = data.get(i);
            View convertView = inflater.inflate(
                    R.layout.course_item_layout, null);
            ButterKnife.inject(this, convertView);

            initData(info);
            this.addView(convertView);
        }
    }

    private void initData(CourceData item) {
//        ImageCache.display(mContext,item.pic,itemImage);
        ImageLoader.getInstance().displayImage(item.pic,itemImage);
        itemName.setText(item.desc);
        itemTv.setText(item.numbers + "");
        itemUpdateTv.setText("更新至："+item.update_time);
    }

}
