package com.open.imooc.ui.community.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.imooc.R;
import com.open.imooc.base.adapter.BasicAdapter;
import com.open.imooc.base.imageCache.ImageCache;
import com.open.imooc.ui.community.bean.ActiveModel.ActiveItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ActiveAdapter extends BasicAdapter<ActiveItem> {

    private Activity context;

    public ActiveAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.active_item_layout, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initItemView(viewHolder, getItem(position));
        return convertView;
    }

    private void initItemView(ViewHolder viewHolder, ActiveItem item) {
//        ImageCache.display(context, item.pic, viewHolder.eventCardImage);
        ImageLoader.getInstance().displayImage(item.pic,viewHolder.eventCardImage);
        viewHolder.eventTitle.setText(item.name);
        viewHolder.eventTime.setText("活动时间:"+item.start_time+"-"+item.end_time);
        viewHolder.eventHot.setText("热门:"+item.hits);
    }

    static class ViewHolder {
        @InjectView(R.id.event_card_image)
        ImageView eventCardImage;
        @InjectView(R.id.event_title)
        TextView eventTitle;
        @InjectView(R.id.event_hot)
        TextView eventHot;
        @InjectView(R.id.event_time)
        TextView eventTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            eventCardImage.setBackgroundResource(R.drawable.article_item_default);
            eventTitle.setText("");
            eventHot.setText("热度:0");
            eventTime.setText("活动时间:");
        }
    }
}
