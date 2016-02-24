package com.open.imooc.ui.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.ui.community.ActiveFragmnet;
import com.open.imooc.ui.community.ArticlesFragment;
import com.open.imooc.ui.details.fragment.ChapterFragment;
import com.open.imooc.ui.details.fragment.CommentFragment;
import com.open.imooc.ui.details.fragment.CourseDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiangzhihong on 2015/12/23 on 14:14.
 * 课程详情
 */
public class MoCourseDetailActivity extends BaseActivity {

    @InjectView(R.id.img_back)
    ImageView imgBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.videoview)
    LinearLayout videoview;
    @InjectView(R.id.course_chapter_name)
    TextView courseChapterName;
    @InjectView(R.id.course_chapter_line)
    View courseChapterLine;
    @InjectView(R.id.course_comment_name)
    TextView courseCommentName;
    @InjectView(R.id.course_comment_line)
    View courseCommentLine;
    @InjectView(R.id.course_detail_name)
    TextView courseDetailName;
    @InjectView(R.id.course_detail_line)
    View courseDetailLine;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        initView();
        initTab();
        initViewpager();
    }

    private void initView() {
        tvTitle.setText("课程详情");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTab() {
        courseChapterName.setTextColor(Color.parseColor("#ffd51716"));
        courseChapterLine.setVisibility(View.VISIBLE);
        courseCommentName.setTextColor(Color.parseColor("#ff808080"));
        courseCommentLine.setVisibility(View.GONE);
        courseDetailName.setTextColor(Color.parseColor("#ff808080"));
        courseDetailLine.setVisibility(View.GONE);

        courseChapterName.setOnClickListener(new tabClick(0));
        courseCommentName.setOnClickListener(new tabClick(1));
        courseDetailName.setOnClickListener(new tabClick(2));
    }

    private void initViewpager() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new ChapterFragment());
        fragments.add(new CommentFragment());
        fragments.add(new CourseDetailFragment());
        viewPager.setAdapter(new tabPagerAdapter(getSupportFragmentManager(),
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
            switchTab(index);
            viewPager.setCurrentItem(index);
        }
    }


    public class tabChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int index) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
                switchTab(index);
        }
    }

    private void switchTab(int index) {
        switch (index) {
            case 0:
                courseChapterName.setTextColor(Color.parseColor("#ffd51716"));
                courseChapterLine.setVisibility(View.VISIBLE);
                courseCommentName.setTextColor(Color.parseColor("#ff808080"));
                courseCommentLine.setVisibility(View.GONE);
                courseDetailName.setTextColor(Color.parseColor("#ff808080"));
                courseDetailLine.setVisibility(View.GONE);
                return;
            case 1:
                courseChapterName.setTextColor(Color.parseColor("#ff808080"));
                courseChapterLine.setVisibility(View.GONE);
                courseCommentName.setTextColor(Color.parseColor("#ffd51716"));
                courseCommentLine.setVisibility(View.VISIBLE);
                courseDetailName.setTextColor(Color.parseColor("#ff808080"));
                courseDetailLine.setVisibility(View.GONE);
                break;
            case 2:
                courseChapterName.setTextColor(Color.parseColor("#ff808080"));
                courseChapterLine.setVisibility(View.GONE);
                courseCommentName.setTextColor(Color.parseColor("#ff808080"));
                courseCommentLine.setVisibility(View.GONE);
                courseDetailName.setTextColor(Color.parseColor("#ffd51716"));
                courseDetailLine.setVisibility(View.VISIBLE);
                break;
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

}
