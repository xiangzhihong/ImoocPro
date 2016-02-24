package com.open.imooc.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.imooc.R;

import butterknife.ButterKnife;

/**
 * Created by xiangzhihong on 2015/11/16 on 14:11.
 */
public class DownloadFragment extends Fragment {


    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_download, null, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
