package com.open.imooc.ui.cource.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.ui.cource.bean.CourceGallery;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourceGalleyAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;


    public CourceGalleyAdapter(Context context, List<Map<String,Object>> list) {
        super();
        this.list = list;
        this.context = context;
    }

    public List<Map<String,Object>> getList() {
        return list;
    }

    public void setList(List<Map<String,Object>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.gallery_item_layout, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.resetView();
        }

//        viewHolder.itemImage.setBackgroundResource(list.get(position).image);
//        viewHolder.itemName.setText(list.get(position).name);
        viewHolder.itemImage.setImageResource((Integer) list.get(position).get("pic"));
        viewHolder.itemName.setText((String) list.get(position).get("page"));
        return convertView;
    }

     class ViewHolder {
        @InjectView(R.id.item_image)
        ImageView itemImage;
        @InjectView(R.id.item_name)
        TextView itemName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

         void resetView(){
             itemImage.setBackgroundResource(R.drawable.clothing_01);
             itemName.setText("畅游温泉");
         }
    }
}
