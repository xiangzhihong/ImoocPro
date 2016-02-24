package com.open.imooc.ui.mine.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.ui.mine.adapter.CartAdapter;
import com.open.imooc.ui.mine.model.CartBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xiangzhihong on 2016/1/7 on 10:56.
 */
public class CartActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @InjectView(R.id.layout_web_topbar)
    RelativeLayout layoutWebTopbar;
    @InjectView(R.id.all_check_box)
    CheckBox allCheckBox;
    @InjectView(R.id.subtitle)
    TextView subtitle;
    @InjectView(R.id.tv_cart_Allprice)
    TextView tvCartAllprice;
    @InjectView(R.id.tv_cart_total)
    TextView tvCartTotal;
    @InjectView(R.id.tv_cart_num)
    TextView tvCartNum;
    @InjectView(R.id.cart_price)
    RelativeLayout cartPrice;
    @InjectView(R.id.cart_favorite)
    TextView cartFavorite;
    @InjectView(R.id.tv_cart_buy_or_del)
    TextView tvCartBuyOrDel;
    @InjectView(R.id.listview)
    ListView listview;

    /**
     * 批量模式下，用来记录当前选中状态
     */
    private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
    private static final int INITIALIZE = 0;
    private List<CartBean> mListData = new ArrayList<CartBean>();
    private CartAdapter adapter = null;
    private boolean isBatchModel;// 是否可删除模式
    private int totalPrice = 0; // 商品总价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.inject(this);
        init();
    }

    @OnClick(R.id.back)
    public void backClick(View view) {
        finish();
    }

    private void init() {
        initListenner();
        initData();
    }

    private void initListenner() {
        subtitle.setOnClickListener(this);
        tvCartBuyOrDel.setOnClickListener(this);
        allCheckBox.setOnClickListener(this);
    }

    private void initData() {
        new LoadDataTask().execute(new Params(INITIALIZE));
    }

    class Params {
        int op;

        public Params(int op) {
            this.op = op;
        }
    }

    class Result {
        int op;
        List<CartBean> list;
    }

    private class LoadDataTask extends AsyncTask<Params, Void, Result> {
        @Override
        protected Result doInBackground(Params... params) {
            Params p = params[0];
            Result result = new Result();
            result.op = p.op;
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.list = getData();
            return result;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result.op == INITIALIZE) {
                mListData = result.list;
            } else {
                mListData.addAll(result.list);
            }
            refreshListView();
        }
    }

    private List<CartBean> getData() {
        int maxId = 0;
        if (mListData != null && mListData.size() > 0)
            maxId = mListData.get(mListData.size() - 1).getId();
        List<CartBean> result = new ArrayList<CartBean>();
        CartBean data = null;
        for (int i = 0; i < 20; i++) {
            data = new CartBean();
            data.setId(maxId + i + 1);
            data.setShopName("我的" + (maxId + 1 + i) + "店铺");
            data.setContent("我的购物车里面的第" + (maxId + 1 + i) + "个商品");
            data.setCarNum(1);
            data.setPrice(305f);
            result.add(data);
        }
        return result;
    }

    private void refreshListView() {
        if (adapter == null) {
            adapter = new CartAdapter(this, mListData, mSelectState);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(this);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        itemClick(view, position);

    }

    private void itemClick(View view, int position) {
        CartBean bean = mListData.get(position);
        CartAdapter.ViewHolder holder = (CartAdapter.ViewHolder) view.getTag();
        int _id = (int) bean.getId();
        boolean selected = !mSelectState.get(_id, false);
        holder.checkBox.toggle();
        if (selected) {
            mSelectState.put(_id, true);
            totalPrice += bean.getCarNum() * bean.getPrice();
        } else {
            mSelectState.delete(_id);
            totalPrice -= bean.getCarNum() * bean.getPrice();
        }
        tvCartNum.setText("已选" + mSelectState.size() + "件商品");
        tvCartTotal.setText("￥" + totalPrice + "");
        if (mSelectState.size() >0) {
            allCheckBox.setChecked(true);
        } else {
            allCheckBox.setChecked(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subtitle:
                isBatchModel = !isBatchModel;
                if (isBatchModel) {
                    subtitle.setText(getResources().getString(R.string.menu_enter));
                    tvCartBuyOrDel.setText(getResources().getString(R.string.menu_del));
                    cartPrice.setVisibility(View.GONE);
                    cartFavorite.setVisibility(View.VISIBLE);

                } else {
                    subtitle.setText(getResources().getString(R.string.menu_edit));
                    cartFavorite.setVisibility(View.GONE);
                    cartPrice.setVisibility(View.VISIBLE);
                    tvCartBuyOrDel.setText(getResources().getString(R.string.menu_sett));
                }

                break;

            case R.id.all_check_box:
                if (allCheckBox.isChecked()) {
                    totalPrice = 0;
                    if (mListData != null) {
                        mSelectState.clear();
                        int size = mListData.size();
                        if (size == 0) {
                            return;
                        }
                        for (int i = 0; i < size; i++) {
                            int _id = (int) mListData.get(i).getId();
                            mSelectState.put(_id, true);
                            totalPrice += mListData.get(i).getCarNum() * mListData.get(i).getPrice();
                        }
                        refreshListView();
                        tvCartTotal.setText("￥" + totalPrice + "");
                        tvCartNum.setText("已选" + mSelectState.size() + "件商品");

                    }
                } else {
                    if (adapter != null) {
                        totalPrice = 0;
                        mSelectState.clear();
                        refreshListView();
                        tvCartTotal.setText("￥" + 0.00 + "");
                        tvCartNum.setText("已选" + 0 + "件商品");

                    }
                }
                break;

            case R.id.tv_cart_buy_or_del:
                if (isBatchModel) {
                } else {
                    Toast.makeText(this, "结算", Toast.LENGTH_LONG).show();
                }

                break;

            default:
                break;
        }
    }


    public interface CartListener {
        void onItem(int price, int num);
    }
}
