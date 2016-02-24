package com.open.imooc.ui.details.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.open.imooc.R;
import com.open.imooc.ui.details.TeacherDetailsActivity;
import com.open.imooc.ui.details.adapter.ChapterAdapter;
import com.open.imooc.ui.details.model.ChapterModel;
import com.open.imooc.ui.details.model.ChapterModel.DataEntity;
import com.open.imooc.ui.details.model.ChapterModel.DataEntity.MediaEntity;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.JsonUtil;
import com.open.imooc.utils.Utils;
import com.open.imooc.widght.McExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChapterFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ExpandableListView>, ExpandableListView.OnChildClickListener {

    @InjectView(R.id.chapter_expanableListview)
    McExpandableListView expandableListView;

    private View view;
    AsyncHttpClient client = new AsyncHttpClient();
    private String uid="2652130";
    private String cid="539";
    private String token="91977ac2b37455663fab9990a29a8ffa";
    private ChapterAdapter chapterAdapter;
    private List<DataEntity> groupList;
    private List<List<MediaEntity>> childList;
    private ChapterModel chapterModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chapter, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initView();
//        initData();
        getData();
    }


    private void initView() {
//        expandableListView.setOnGroupClickListener(this);
        expandableListView.setOnChildClickListener(this);
    }

    private void getData() {
        String content= Utils.getFromAssets(getActivity(),"chapter_default.txt");
       if(content!=null){
           chapterModel = JsonUtil.parseJson(content, ChapterModel.class);
           if (chapterModel != null&&chapterModel.data!=null&&chapterModel.data.size()>0) {
               bindData(chapterModel.data);
           }
       }
    }

    private void initData() {
        String url = Contants.GET_CHAPTER_INFO;
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("cid",cid);
        params.put("token",token);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                if (content != null) {
                    chapterModel = JsonUtil.parseJson(content, ChapterModel.class);
                    if (chapterModel != null&&chapterModel.data!=null&&chapterModel.data.size()>0) {
                        bindData(chapterModel.data);
                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void bindData(List<DataEntity> data) {
        //考虑到这种数据结构，只能有这种循环了
        groupList = new ArrayList<DataEntity>();
        childList = new ArrayList<List<MediaEntity>>();
        for(int i=0;i<data.size();i++){
             groupList.add(data.get(i));
            List<MediaEntity> child = new ArrayList<MediaEntity>();
            for(int j=0;j<data.get(i).media.size();j++){
                child.add(data.get(i).media.get(j));
            }
            childList.add(child);
        }
        chapterAdapter = new ChapterAdapter(getActivity(),groupList,childList);
        expandableListView.setAdapter(chapterAdapter);
        //默认展开
        for(int i = 0; i < chapterAdapter.getGroupCount(); i++){
            expandableListView.expandGroup(i);
        }
    }



   //下拉
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

    }
  //上拉
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        startActivity(new Intent(getActivity(), TeacherDetailsActivity.class));
        return true;
    }
}