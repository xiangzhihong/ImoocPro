package com.open.imooc.ui.mine.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.ui.mine.model.CartBean;
import com.open.imooc.ui.mine.ui.CartActivity.CartListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CartAdapter extends BaseAdapter {

    private Activity context;
    private List<CartBean> cartList;
    private SparseArray<Boolean> selectState;
    private int totlePrice = 0;
    private CartListener cartListener;

    public CartAdapter(Activity context, List<CartBean> cartList, SparseArray<Boolean> selectState) {
        this.context = context;
        this.cartList = cartList;
        this.selectState = selectState;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public CartBean getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.cart_list_item, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initItemView(viewHolder, getItem(position));
        return convertView;
    }

    private void initItemView(ViewHolder viewHolder, CartBean item) {
        viewHolder.tvSourceName.setText(item.getShopName());
        viewHolder.tvIntro.setText(item.getContent());
        viewHolder.tvPrice.setText("￥" + item.getPrice());
        viewHolder.tvNum.setText(item.getCarNum() + "");
        int id = item.getId();

        boolean selected = selectState.get(id, false);
        viewHolder.checkBox.setChecked(selected);
        bindAdd(viewHolder, item);
        bindDel(viewHolder, item);
    }

    private void bindAdd(ViewHolder viewHolder, final CartBean item) {
        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = item.getId();
                boolean selected = selectState.get(id, false);
                item.setCarNum(item.getCarNum() + 1);
                notifyDataSetChanged();
                if (selected) {
                    totlePrice += item.getPrice();
                    if (cartListener != null) {
                        cartListener.onItem(totlePrice, 1);
                    }
                }
            }
        });
    }

    private void bindDel(ViewHolder viewHolder, CartBean item) {
        if (item.getCarNum() == 1)
            return;
        int id = item.getId();
        boolean selected = selectState.get(id, false);
        item.setCarNum(item.getCarNum() - 1);
        notifyDataSetChanged();
        if (selected) {
            totlePrice -= item.getPrice();
            if (cartListener != null) {
                cartListener.onItem(totlePrice, 1);
            }
        }
    }

   public static class ViewHolder {
        @InjectView(R.id.tv_source_name)
        TextView tvSourceName;
        @InjectView(R.id.cart_goto_shop)
        LinearLayout cartGotoShop;
        @InjectView(R.id.all_check_box)
       public CheckBox checkBox;
        @InjectView(R.id.iv_adapter_list_pic)
        ImageView ivAdapterListPic;
        @InjectView(R.id.tv_intro)
        TextView tvIntro;
        @InjectView(R.id.tv_price)
        TextView tvPrice;
        @InjectView(R.id.tv_reduce)
        TextView tvReduce;
        @InjectView(R.id.tv_num)
        TextView tvNum;
        @InjectView(R.id.tv_add)
        TextView tvAdd;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
        }
    }

    public void setCartListener(CartListener listener) {
        this.cartListener = listener;
    }
}
