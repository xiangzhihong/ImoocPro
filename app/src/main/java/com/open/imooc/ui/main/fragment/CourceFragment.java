package com.open.imooc.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.open.imooc.R;
import com.open.imooc.bean.FlipModel;
import com.open.imooc.bean.MineModel;
import com.open.imooc.ui.cource.adapter.CourceAdapter;
import com.open.imooc.ui.cource.adapter.CourceGalleyAdapter;
import com.open.imooc.ui.cource.bean.CourceBanner;
import com.open.imooc.ui.cource.bean.CourceGallery;
import com.open.imooc.ui.cource.bean.CourceList;
import com.open.imooc.ui.cource.bean.CourceList.CourceData;
import com.open.imooc.ui.cource.view.CourceBottomView;
import com.open.imooc.ui.details.MoCourseDetailActivity;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.JsonUtil;
import com.open.imooc.widght.CycleView;
import com.open.imooc.widght.HorizontalListView;
import com.open.imooc.widght.McFlipTextView;
import com.open.imooc.widght.McListView;
import com.open.imooc.widght.WebActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xiangzhihong on 2015/11/16 on 14:11.
 */
public class CourceFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener{

    @InjectView(R.id.search)
    ImageView search;
    @InjectView(R.id.banner_view)
    CycleView bannerView;
    @InjectView(R.id.container_bottom)
    LinearLayout containerBottom;
    @InjectView(R.id.refresh_view)
    PullToRefreshScrollView refreshView;
    @InjectView(R.id.cource_listView)
    McListView listView;
    @InjectView(R.id.flip_tv)
    McFlipTextView flipView;
    @InjectView(R.id.horizontalListView)
    HorizontalListView horizontalListView;


    private View view;
    private FlipModel fm;
    private List<FlipModel> list;
    private CourceAdapter courceAdapter;
    private CourceGalleyAdapter galleyAdapter;
    private List<Map<String, Object>> galleryList;
    private Map<String,Object> map ;
    AsyncHttpClient client = new AsyncHttpClient();
    private CourceBottomView courceBottomView;
    private String timestamp = "1450167252147";
    private String token = "7f75e24cb1f7e5c358f03a7b40a60976";
    private int pageIndex = 0;
    private String uid = "0";
    private List<CourceData> courceDatas;
    private int currentPage = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tab_cource, null, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initView();
        initFlipData();
        initHorizonData();
        getBannerData();
        getCourceListData();
    }

    //横向滚动
    private void initHorizonData() {
        getData();
        galleyAdapter=new CourceGalleyAdapter(getActivity(),galleryList);
        horizontalListView.setAdapter(galleyAdapter);
    }

    private List<Map<String, Object>> getData(){
            int [] pics = {R.drawable.clothing_01,R.drawable.clothing_02,R.drawable.clothing_03,R.drawable.clothing_04,R.drawable.clothing_05,
                    R.drawable.clothing_06,R.drawable.clothing_07,R.drawable.clothing_08};
            String[] names={"温泉特惠","长白山","温泉","海岛游","温泉洗浴","国内游","国际旅游","海盗旅游"};
            galleryList = new ArrayList<Map<String,Object>>();
            for(int i = 0;i<pics.length;i++){
                map =new HashMap<String, Object>();
                map.put("name", names[i]);
                map.put("pic", pics[i]);
                galleryList.add(map);
            }
            return galleryList;
    }

    private void initFlipData() {
        if(list==null){
            list = new ArrayList<>();
        }else{
            list.clear();
        }
        fm = new FlipModel();
        fm.title="投票刮奖赢话费，还有免费泡温泉！";
        fm.url="http://m.lvmama.com/static/zt/3.0.0/1511/zhongguozhixing/index.html?osVersion=4.4.4&lvversion=7.4.0&globalLatitude=31.290786&globalLongitude=121.456522&firstChannel=ANDROID&udid=866818029371380&formate=json&secondChannel=ANDROID_360";
        list.add(fm);
        fm = new FlipModel();
        fm.title="听音乐跑温泉，5点开抢！";
        fm.url="http://m.lvmama.com/static/zt/3.0.0/1509/hotspring/index.html?losc=066028&ict=1&osVersion=4.4.4&lvversion=7.4.0&globalLatitude=31.290786&globalLongitude=121.456522&firstChannel=ANDROID&udid=866818029371380&formate=json&secondChannel=ANDROID_360";
        list.add(fm);
        flipView.setData(list, itemListener,flipView);
    }

    private McFlipTextView.ItemDataListener itemListener = new McFlipTextView.ItemDataListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent=new Intent(getActivity(), WebActivity.class);
            intent.putExtra("title", list.get(position).title);
            intent.putExtra("url",list.get(position).url);
            startActivity(intent);
        }
    };

    private void initView() {
        courceAdapter=new CourceAdapter(getActivity());
        listView.setAdapter(courceAdapter);
        listView.setOnItemClickListener(this);

        refreshView.setOnRefreshListener(this);
        refreshView.setScrollingWhileRefreshingEnabled(true);
        refreshView.setLastPage(true);
    }

    private void getBannerData() {
        String url = Contants.COURCE_BANNER;
        RequestParams params = new RequestParams();
        params.put("uid", "0");
        params.put("marking", "banner");
        params.put("token", "8301d54bbde33ffc9cce3317a51ecd13");
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                if(refreshView!=null){
                    refreshView.onRefreshComplete();
                }
                if (content != null) {
                    CourceBanner banner = JsonUtil.parseJson(content, CourceBanner.class);
                    if (banner != null) {
                        List<CourceBanner.BannerItem> list = banner.data;
                        bannerView.setImageResources(list, listener);
                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                if (refreshView!=null)
                refreshView.onRefreshComplete();
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getCourceListData() {
        String url = Contants.COURCE_LIST;
        RequestParams params = new RequestParams();
        params.put("timestamp", timestamp);
        params.put("uid", uid);
        params.put("page", pageIndex + "");
        params.put("token", token);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                if (content != null) {
                    if (refreshView!=null)
                    refreshView.onRefreshComplete();
                    CourceList list = JsonUtil.parseJson(content, CourceList.class);
                    if (list != null && list.data.size() > 0) {
                        List<CourceList.CourceData> listCource = list.data;
                        courceAdapter.setList(listCource);
//                        addBottomView(list);
                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                if (refreshView!=null)
                refreshView.onRefreshComplete();
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addBottomView(CourceList list) {
        courceDatas = new ArrayList<CourceData>();
        if (courceBottomView == null) {
            courceBottomView = new CourceBottomView(
                    getActivity(), list.data);
            containerBottom
                    .addView(courceBottomView);
        }
        if (pageIndex == 1) {
            courceDatas = list.data;
            courceBottomView.removeAllViews();
            courceBottomView.init(courceDatas);
        } else {
            courceBottomView.init(list.data);
        }
        pageIndex = currentPage;
        pageIndex++;
        refreshView.setLastPage(true);
    }

    private CycleView.CycleViewListener listener = new CycleView.CycleViewListener() {
        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
            ImageLoader.getInstance().displayImage(imageURL, imageView);
        }

        @Override
        public void onImageClick(CourceBanner.BannerItem info, int postion, View imageView) {
            Intent intent=new Intent(getActivity(), WebActivity.class);
            intent.putExtra("title", "慕课网广告");
            intent.putExtra("url",info.links);
            startActivity(intent);
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), MoCourseDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getBannerData();
        getCourceListData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getBannerData();
        getCourceListData();
    }
}
