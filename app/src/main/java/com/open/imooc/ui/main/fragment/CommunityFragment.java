package com.open.imooc.ui.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.ui.community.ActiveFragmnet;
import com.open.imooc.ui.community.ArticlesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiangzhihong on 2015/11/16 on 14:11.
 */
public class CommunityFragment extends Fragment {

    @InjectView(R.id.viewpager)
    ViewPager viewPager;
    @InjectView(R.id.tab_article)
    View tabArticle;
    @InjectView(R.id.tab_active)
    View tabActive;
    @InjectView(R.id.tab_article_tv)
    TextView tabArticleTv;
    @InjectView(R.id.tab_article_line)
    View tabArticleLine;
    @InjectView(R.id.tab_active_tv)
    TextView tabActiveTv;
    @InjectView(R.id.tab_active_line)
    View tabActiveLine;

    private View view;
    private List<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tab_community, null, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initTab();
        initViewpager();
    }

    private void initTab() {
        tabArticleTv.setText("文章");
        tabArticleTv.setTextColor(Color.parseColor("#ffffff"));
        tabArticleLine.setVisibility(View.VISIBLE);
        tabActiveTv.setText("活动");
        tabActiveTv.setTextColor(Color.parseColor("#c0c0c0"));
        tabActiveLine.setVisibility(View.GONE);

        tabArticleTv.setOnClickListener(new tabClick(0));
        tabActiveTv.setOnClickListener(new tabClick(1));
    }


    private void initViewpager() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new ArticlesFragment());
        fragments.add(new ActiveFragmnet());
        viewPager.setAdapter(new tabPagerAdapter(getActivity().getSupportFragmentManager(),
                fragments));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new tabChangeListener());
    }


    private class tabClick implements View.OnClickListener {
        private int index = 0;

        public tabClick(int i) {
            index = i;
        }

        public void onClick(View v) {
            switch (index) {
                case 0:
                    tabArticleTv.setTextColor(Color.parseColor("#ffffff"));
                    tabActiveTv.setTextColor(Color.parseColor("#c0c0c0"));
                    tabArticleLine.setVisibility(View.VISIBLE);
                    tabActiveLine.setVisibility(View.GONE);
                    break;
                case 1:
                    tabArticleTv.setTextColor(Color.parseColor("#c0c0c0"));
                    tabActiveTv.setTextColor(Color.parseColor("#ffffff"));
                    tabArticleLine.setVisibility(View.GONE);
                    tabActiveLine.setVisibility(View.VISIBLE);
                    break;
            }
            viewPager.setCurrentItem(index);
        }
    }

    public class tabChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int index) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
            switch (index) {
                case 0:
                    tabArticleTv.setTextColor(Color.parseColor("#ffffff"));
                    tabActiveTv.setTextColor(Color.parseColor("#c0c0c0"));
                    tabArticleLine.setVisibility(View.VISIBLE);
                    tabActiveLine.setVisibility(View.GONE);
                    break;
                case 1:
                    tabArticleTv.setTextColor(Color.parseColor("#c0c0c0"));
                    tabActiveTv.setTextColor(Color.parseColor("#ffffff"));
                    tabArticleLine.setVisibility(View.GONE);
                    tabActiveLine.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    class tabPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public tabPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null
                    : fragmentList.get(arg0);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
