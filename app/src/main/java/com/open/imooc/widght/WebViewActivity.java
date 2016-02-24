package com.open.imooc.widght;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.service.NewsDetailsService;


@SuppressLint("JavascriptInterface")
public class WebViewActivity extends BaseActivity {
	private TextView tvTitle;
	private ProgressBar progressBar;
	private FrameLayout customview_layout;
	private String web_url;
	private String web_title;
	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		setNeedBackGesture(true);
		getData();
		initView();
		initWebView();
	}
	private void getData() {
		web_title=getIntent().getStringExtra("title");
		web_url=getIntent().getStringExtra("url");
	}
	private void initWebView() {
		webView = (WebView)findViewById(R.id.wb_details);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		if (!TextUtils.isEmpty(web_url)) {
			WebSettings settings = webView.getSettings();
			settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			settings.setSupportZoom(false);
			settings.setBuiltInZoomControls(false);
			webView.setBackgroundResource(R.color.transparent);
			webView.addJavascriptInterface(new JavascriptInterface(getApplicationContext()),"imagelistner");
			webView.setWebChromeClient(new MyWebChromeClient());
			webView.setWebViewClient(new MyWebViewClient());
			new MyAsnycTask().execute(web_url, web_title);
		}
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.title);
		progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
		customview_layout = (FrameLayout) findViewById(R.id.customview_layout);

		progressBar.setVisibility(View.VISIBLE);
		tvTitle.setTextSize(13);
		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText(web_title);

		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	private class MyAsnycTask extends AsyncTask<String, String,String>{

		@Override
		protected String doInBackground(String... urls) {
			String data= NewsDetailsService.getNewsDetails(urls[0], urls[1]);
			return data;
		}

		@Override
		protected void onPostExecute(String data) {
			webView.loadDataWithBaseURL (null, data, "text/html", "utf-8",null);
		}
	}

	private void addImageClickListner() {
		webView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\");"
				+ "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
				+ "imgurl+=objs[i].src+',';"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(imgurl);  "
				+ "    }  " + "}" + "})()");
	}

	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			addImageClickListner();
			progressBar.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			progressBar.setVisibility(View.GONE);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}
	
	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if(newProgress != 100){
				progressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			tvTitle.setText(title);
		}
	}
}
