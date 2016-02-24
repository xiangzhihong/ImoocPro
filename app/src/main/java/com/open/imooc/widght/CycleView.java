package com.open.imooc.widght;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.open.imooc.R;
import com.open.imooc.ui.cource.bean.CourceBanner.BannerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告图片自动轮播控件</br>
 */
public class CycleView extends LinearLayout {

    private Context mContext;
    private CycleViewPager mBannerPager = null;
    private ImageCycleAdapter mAdvAdapter;
    private ViewGroup mGroup;
    private ImageView mImageView = null;
    private ImageView[] mImageViews = null;
    private int mImageIndex = 1;
    private float mScale;
    public CycleView(Context context) {
        super(context);
    }
    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        LayoutInflater.from(context).inflate(R.layout.cource_banner_item, this);
        mBannerPager = (CycleViewPager) findViewById(R.id.pager_banner);
        mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
        mBannerPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        startImageTimerTask();
                        break;
                    default:
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        mGroup = (ViewGroup) findViewById(R.id.cource_view);
    }

    public void setImageResources(List<BannerItem> BannerItemList, CycleViewListener imageCycleViewListener) {
        mGroup.removeAllViews();
        final int imageCount = BannerItemList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mContext);
            int imageParams = (int) (mScale * 20 + 0.5f);// XP与DP转换，适应不同分辨率
            int imagePadding = (int) (mScale * 5 + 0.5f);
            LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layout.setMargins(3, 0, 3, 0);
            mImageView.setLayoutParams(layout);
            //mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.drawable.dot_p);
            } else {
                mImageViews[i].setBackgroundResource(R.drawable.dot_n);
            }
            mGroup.addView(mImageViews[i]);
        }
        mAdvAdapter = new ImageCycleAdapter(mContext, BannerItemList, imageCycleViewListener);
        mBannerPager.setAdapter(mAdvAdapter);
        startImageTimerTask();
    }

    public void startCycle() {
        startImageTimerTask();
    }
    public void imageCycle() {
        stopImageTimerTask();
    }
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片每3秒滚动一次
        mHandler.postDelayed(mImageTimerTask, 3000);
    }

    private void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }
    private Handler mHandler = new Handler();
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            if (mImageViews != null) {
                if ((++mImageIndex) == mImageViews.length + 1) {
                    mImageIndex = 1;
                }
                mBannerPager.setCurrentItem(mImageIndex);
            }
        }
    };

    private final class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startImageTimerTask();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {

            if (index == 0 || index == mImageViews.length + 1) {
                return;
            }
            mImageIndex = index;
            index -= 1;
            mImageViews[index].setBackgroundResource(R.drawable.dot_p);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setBackgroundResource(R.drawable.dot_n);
                }
            }

        }

    }

    private class ImageCycleAdapter extends PagerAdapter {

        private List<ImageView> mImageViewCacheList;
        private List<BannerItem> mAdList = new ArrayList<BannerItem>();

        private CycleViewListener mImageCycleViewListener;

        private Context mContext;

        public ImageCycleAdapter(Context context, List<BannerItem> adList, CycleViewListener imageCycleViewListener) {
            mContext = context;
            mAdList = adList;
            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<ImageView>();
        }

        @Override
        public int getCount() {
            return mAdList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            String imageUrl = mAdList.get(position).pic;
            ImageView imageView = null;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(mAdList.get(position), position, v);
                }
            });
            imageView.setTag(imageUrl);
            container.addView(imageView);
            mImageCycleViewListener.displayImage(imageUrl, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            container.removeView(view);
            mImageViewCacheList.add(view);
        }

    }

    public static interface CycleViewListener {
    public void displayImage(String imageURL, ImageView imageView);
    public void onImageClick(BannerItem BannerItem, int postion, View imageView);
    }

}
