package com.open.imooc.ui.cource.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.imooc.R;
import com.open.imooc.base.imageCache.ImageCache;
import com.open.imooc.ui.cource.bean.CourceList.CourceData;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourceAdapter extends BaseAdapter {
    private Context context;
    private List<CourceData> list;

    public CourceAdapter(Context context) {
        this.context = context;
    }

    public CourceAdapter( Context context,List<CourceData> list) {
        super();
        this.list = list;
        this.context = context;
    }

    public List<CourceData> getList() {
        return list;
    }

    public void setList(List<CourceData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list!=null){
           return list.size();
        }else
        return 0;
    }

    @Override
    public CourceData getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.course_item_layout, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.resetView();
        }
        CourceData item = list.get(position);
        if (item == null) {
            return null;
        } else {
           initItemData(viewHolder,item);
        }
        return convertView;
    }

    private void initItemData(ViewHolder viewHolder, CourceData item) {
//        ImageCache.display(context,item.pic,viewHolder.itemImage);
        ImageLoader.getInstance().displayImage(item.pic,viewHolder.itemImage);
        viewHolder.itemName.setText(item.desc);
        viewHolder.itemTv.setText(item.numbers + "");
        viewHolder.itemUpdateTv.setText("更新至："+item.max_chapter_seq+"-"+item.max_media_seq);
    }

    public class ViewHolder {
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
        @InjectView(R.id.content_layout)
        RelativeLayout contentLayout;

        public ViewHolder(View parent) {
            ButterKnife.inject(this, parent);
            parent.setTag(this);
        }

        private void resetView() {

        }
    }

}
