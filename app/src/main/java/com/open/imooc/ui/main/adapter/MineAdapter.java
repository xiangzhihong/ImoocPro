package com.open.imooc.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.bean.MineModel;
import com.open.imooc.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiangzhihong on 2015/12/30 on 17:53.
 */
public class MineAdapter extends BaseAdapter {

    private Context context;
    private List<MineModel> list;

    public MineAdapter(Context context, List<MineModel> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
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
            convertView = inflater.inflate(R.layout.mine_grid_item, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemName.setText(list.get(position).name);
        Utils.setDrawableTop(viewHolder.itemName,context.getResources().getDrawable(list.get(position).image));
        return convertView;
    }


    class ViewHolder {
        @InjectView(R.id.item_name)
        TextView itemName;
        @InjectView(R.id.item_update_point)
        ImageView itemUpdatePoint;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
