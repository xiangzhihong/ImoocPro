package com.open.imooc.ui.main.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.ui.main.fragment.CommunityFragment;
import com.open.imooc.ui.main.fragment.CourceFragment;
import com.open.imooc.ui.main.fragment.DownloadFragment;
import com.open.imooc.ui.main.fragment.MineFragment;
import com.open.imooc.utils.Utils;

public enum UIHelp {

    COURCE(0, "课程", CourceFragment.class, R.drawable.tab_cource_selector),
    COMMUNITY(1, "社区", CommunityFragment.class, R.drawable.tab_community_selector),
    DOWNLOAD(2, "下载", DownloadFragment.class, R.drawable.tab_download_selector),
    MINE(3, "我的", MineFragment.class, R.drawable.tab_mine_selector);

    private int pagerIndex = 0;
    private int subPagerIndex = 0;
    private String label = null;
    private Class pager = null;
    private int icon = -1;

    UIHelp(int pagerIndex, String label, Class pager, int icon) {
        this.pagerIndex = pagerIndex;
        this.label = label;
        this.pager = pager;
        this.icon = icon;
    }

    public int getPagerIndex() {
        return pagerIndex;
    }

    public String getLabel() {
        return label;
    }

    public Class getPager() {
        return pager;
    }

    public int getPagerIcon() {
        return icon;
    }

    public View getTabView(Activity context) {
        View tabView = context.getLayoutInflater().inflate(R.layout.tab_item, null);
        TextView labelTxt = (TextView) tabView.findViewById(R.id.label);
        labelTxt.setText(getLabel());
        labelTxt.setCompoundDrawables(null, Utils.getCompoundDrawable(context, getPagerIcon()), null, null);
        return tabView;
    }

    public int getSubPagerIndex() {
        return subPagerIndex;
    }

    public void setSubPagerIndex(int subPagerIndex) {
        this.subPagerIndex = subPagerIndex;
    }

    public static UIHelp getType(int key) {
        for (UIHelp value : values()) {
            if (key == value.pagerIndex) {
                return value;
            }
        }
        return COURCE;
    }
    public static UIHelp getType(String name) {
        for (UIHelp value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return COURCE;
    }


    public int getStateBarColor(){
        if(this == COURCE){
            return R.color.red_D51716;
        }
        return R.color.cardview_dark_background;
    }
}
