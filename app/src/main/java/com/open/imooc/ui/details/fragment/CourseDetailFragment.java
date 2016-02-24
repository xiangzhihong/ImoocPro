package com.open.imooc.ui.details.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.imooc.R;
import com.open.imooc.ui.details.TeacherDetailsActivity;
import com.open.imooc.ui.details.model.DetailModel;
import com.open.imooc.utils.Contants;
import com.open.imooc.utils.JsonUtil;
import com.open.imooc.widght.RoundImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CourseDetailFragment extends Fragment {

    @InjectView(R.id.teacher_avert)
    RoundImageView teacherAvert;

    private View view;
    private String token = "5e8fb0913db1480814d866d04316c4f5";
    private int cid = 562;
    private String uid = "2652130";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cource_detail, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initView();
        reqData();
    }


    private void initView() {
        String avertUrl="http://img.mukewang.com/5649356b0001670502000200-100-100.jpg";
        ImageLoader.getInstance().displayImage(avertUrl, teacherAvert);
    }

    private void reqData() {
        String url = Contants.GET_COMMENT_INFO;
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("cid", cid + "");
        params.put("token", token);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                if (content != null) {
                    DetailModel commentModel = JsonUtil.parseJson(content, DetailModel.class);
                    if (commentModel != null) {

                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.related_teacher)
    public void back(View view){
        startActivity(new Intent(getActivity(), TeacherDetailsActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}