package com.open.imooc.ui.details.fragment;

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
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.open.imooc.R;
import com.open.imooc.ui.community.adapter.ArticleAdapter;
import com.open.imooc.ui.community.bean.ArticleModel;
import com.open.imooc.ui.details.adapter.CommentAdapter;
import com.open.imooc.ui.details.model.CommentModel;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.JsonUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2{

    @InjectView(R.id.comment_listView)
    PullToRefreshListView commentListView;
    @InjectView(R.id.note_image)
    ImageView noteImage;

    private View view;
    private CommentModel commentModel;
    private CommentAdapter adapter;

    private String token = "aec1e1fe6da2e8a4ba9d7b725d851f57";
    private int cid = 562;
    private String uid = "2652130";
    private int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initView();
        getData();
    }

    private void initView() {
        adapter = new CommentAdapter(getActivity());
        commentListView.setAdapter(adapter);
        commentListView.setOnRefreshListener(this);
        commentListView.setScrollingWhileRefreshingEnabled(true);
        commentListView.setLastPage(true);
    }

    private void getData() {
        String url = Contants.GET_COMMENT_INFO;
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("page", page + "");
        params.put("cid", cid + "");
        params.put("token", token);
//        AsyncHttpClient client=new AsyncHttpClient();
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(String content) {
//                commentListView.onRefreshComplete();
//                if (content != null) {
//                    commentModel = JsonUtil.parseJson(content, CommentModel.class);
//                    if (commentModel != null) {
//                        bindData(commentModel.data);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable error, String content) {
//        commentListView.onRefreshComplete();
//                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
//            }
//        });

//        String content=getResources().getString(R.string.comment_str);
//        commentModel = JsonUtil.parseJson(content, CommentModel.class);
//                    if (commentModel != null) {
//                        bindData(commentModel.data);
//                    }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void bindData(List<CommentModel.CommentItem> data) {
//        commentListView.setLastPage(data == null);
//        adapter.addList(data);
//        if (adapter.getCount() == 0) {
//            commentListView.setVisibility(View.GONE);
//        } else {
//            commentListView.setVisibility(View.VISIBLE);
//        }
//        ++page;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        commentListView.onRefreshComplete();
       getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
    }
}