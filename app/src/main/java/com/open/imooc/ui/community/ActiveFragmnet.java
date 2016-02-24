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
import com.open.imooc.ui.community.adapter.ActiveAdapter;
import com.open.imooc.ui.community.bean.ActiveModel;
import com.open.imooc.ui.community.bean.ActiveModel.ActiveItem;
import com.open.imooc.ui.community.bean.ArticleModel;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.JsonUtil;
import com.open.imooc.widght.WebActivity;
import com.open.imooc.widght.WebViewActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ActiveFragmnet extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener{

	@InjectView(R.id.active_listview)
	PullToRefreshListView listView;
	@InjectView(R.id.active_empty_view)
	LinearLayout emptyView;

	private String token = "ef4c7b190b90a4cc14a11a58828d5c46";
	private String uid = "0";
	private int page=1;

    private ActiveAdapter adapter;
	AsyncHttpClient client = new AsyncHttpClient();
	private ActiveModel activeModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_community_active, null);
		ButterKnife.inject(this, view);
		init();
		return view;
	}

	private void init() {
		initView();
		reqData();
	}


	private void initView() {
		emptyView.setVisibility(View.GONE);
		adapter=new ActiveAdapter(getActivity());
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(this);
		listView.setScrollingWhileRefreshingEnabled(true);
		listView.setLastPage(true);
		listView.setOnItemClickListener(this);
	}

	private void reqData() {
		String url = Contants.GET_ACTIVE_DATA;
		RequestParams params = new RequestParams();
		params.put("uid", uid);
		params.put("page", page + "");
		params.put("token", token);
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				if (listView != null)
					listView.onRefreshComplete();
				if (content != null) {
					activeModel = JsonUtil.parseJson(content, ActiveModel.class);
					if (activeModel != null) {
						bindData(activeModel.data);
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

	private void bindData(List<ActiveItem> data) {
		if(uid.equals("0")){
         adapter.clear();
		}
		listView.setLastPage(data == null);
		adapter.addList(data);
		if (adapter.getCount() == 0) {
			listView.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
		} else {
			listView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
		}
		ActiveItem item = adapter.getLastItem();
		if (item != null) {
			uid=item.uid;
		}
		++page;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent=new Intent(getActivity(), WebViewActivity.class);
		intent.putExtra("title", activeModel.data.get(position).name);
		intent.putExtra("url",activeModel.data.get(position).links);
		startActivity(intent);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		uid = "0";
		page=1;
		reqData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		reqData();
	}
}