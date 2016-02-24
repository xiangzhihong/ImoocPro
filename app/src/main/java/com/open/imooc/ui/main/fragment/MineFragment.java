package com.open.imooc.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.open.imooc.R;
import com.open.imooc.bean.MineModel;
import com.open.imooc.ui.main.adapter.MineAdapter;
import com.open.imooc.ui.mine.ui.CartActivity;
import com.open.imooc.ui.mine.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xiangzhihong on 2015/11/16 on 14:11.
 */
public class MineFragment extends Fragment implements AdapterView.OnItemClickListener{

    @InjectView(R.id.mine_gridview)
    GridView mineGridview;

    private View view;
    private Context mContext;
    private MineAdapter adapter;
    private List<MineModel> mineList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tab_mine, null, false);
        ButterKnife.inject(this, view);
        mContext = getActivity();
        init();
        return view;
    }

    private void init() {
        mineList = new ArrayList<>();
        MineModel model = new MineModel();
        model.image = R.drawable.people_mycourse_icon;
        model.name = "关注的课程";
        mineList.add(model);
        model = new MineModel();
        model.image = R.drawable.people_myplan_icon;
        model.name = "我的计划";
        mineList.add(model);
        model = new MineModel();
        model.image = R.drawable.people_mymessage_icon;
        model.name = "我的消息";
        mineList.add(model);
        model = new MineModel();
        model.image = R.drawable.people_myarticle_icon;
        model.name = "收藏的文章";
        mineList.add(model);
        model = new MineModel();
        model.image = R.drawable.people_mynote_icon;
        model.name = "我的笔记";
        mineList.add(model);
        model = new MineModel();
        model.image = R.drawable.people_myhistory_icon;
        model.name = "最近学习";
        mineList.add(model);

        adapter = new MineAdapter(mContext, mineList);
        mineGridview.setAdapter(adapter);
        mineGridview.setOnItemClickListener(this);
    }

    @OnClick(R.id.setting)
    public void setting(View view){
        startActivity(new Intent(getActivity(),SettingActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            case 1:

                break;
        }
    }
}
