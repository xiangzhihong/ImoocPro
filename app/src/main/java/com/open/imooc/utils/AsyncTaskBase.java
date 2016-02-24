package com.open.imooc.utils;

import android.os.AsyncTask;
import android.view.View;

import com.open.imooc.R;
import com.open.imooc.widght.LoadingView;


public class AsyncTaskBase extends AsyncTask<Integer, Integer, Integer> {
	private LoadingView mLoadingView;
	public AsyncTaskBase(LoadingView loadingView){
		this.mLoadingView=loadingView;
	}
	@Override
	protected Integer doInBackground(Integer... params) {

		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(result==1){
			mLoadingView.setVisibility(View.GONE);
		}else{
			mLoadingView.setText(R.string.no_contact);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mLoadingView.setVisibility(View.VISIBLE);
	}

}
