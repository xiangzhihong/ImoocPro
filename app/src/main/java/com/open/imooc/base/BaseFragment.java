package com.open.imooc.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {


	public BaseFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	public void launchSubPage(Activity root, Class<?> activity) {
		Intent in = new Intent(root, activity);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		root.startActivity(in);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
