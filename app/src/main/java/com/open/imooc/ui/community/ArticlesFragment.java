package com.open.imooc.ui.community;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.open.imooc.R;
import com.open.imooc.ui.community.adapter.ArticleAdapter;
import com.open.imooc.ui.community.bean.ArticleModel;
import com.open.imooc.ui.community.bean.ArticleModel.ArticleItem;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.JsonUtil;
import com.open.imooc.widght.WebViewActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 文章
 */
public class ArticlesFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener{

    @InjectView(R.id.article_listview)
    PullToRefreshListView listView;
    @InjectView(R.id.empty_view)
    LinearLayout emptyView;

    private String token = "aec1e1fe6da2e8a4ba9d7b725d851f57";
    private int type=0;
    private int aid = 0;
    private String uid = "0";
    private int page=0;
    private int typeid=0;

    AsyncHttpClient client = new AsyncHttpClient();
    private ArticleModel articleModel;
    private ArticleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_articles, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initView();
        getData();
    }

    private void initView() {
        emptyView.setVisibility(View.GONE);
        adapter=new ArticleAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(this);
        listView.setScrollingWhileRefreshingEnabled(true);
        listView.setLastPage(true);
        listView.setOnItemClickListener(this);
    }

    private void getData() {
        String url = Contants.GET_ARTICLE_DATA;
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("aid",aid+"");
        params.put("type",type+"");
        params.put("page",page+"");
        params.put("typeid",typeid+"");
        params.put("token",token);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                if (listView != null)
                    listView.onRefreshComplete();
                if (content != null) {
                    articleModel = JsonUtil.parseJson(content, ArticleModel.class);
                    if (articleModel != null) {
                        bindData(articleModel.data);
                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                if (listView != null)
                    listView.onRefreshComplete();
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindData(List<ArticleItem> data) {
        if (aid == 0) adapter.clear();
        listView.setLastPage(data == null);
        adapter.addList(data);
        if (adapter.getCount() == 0) {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        ArticleItem item = adapter.getLastItem();
        if (item != null) {
            aid=item.id;
        }
        ++page;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        aid = 0;
        page=0;
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
         getData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url="http://tech.sina.com.cn/zl/post/detail/i/2013-11-06/pid_8436571.htm?from=groupmessage&isappinstalled=0";
        Intent intent=new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("title", "文章");
        intent.putExtra("url",url);
        startActivity(intent);

    }
}