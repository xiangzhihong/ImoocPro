package com.open.imooc.widght;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.open.imooc.R;
import com.open.imooc.base.BaseActivity;
import com.open.imooc.base.McApplication;


public class WebActivity extends BaseActivity implements OnClickListener {

	protected WebView webView;
    private TextView tvTitle;
	private  ProgressBar progressBar;
	private String webUrl,titleStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		init();

	}

	private void init() {
		initParam();
        initView();
		initweb();
		initWebView();
	}

	private void initweb() {
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setSaveFormData(false);
		webView.getSettings().setSavePassword(false);
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBlockNetworkLoads(false);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
	}

	private void initParam() {
		titleStr=getIntent().getStringExtra("title");
		webUrl=getIntent().getStringExtra("url");
	}

	private void initView() {
		webView=(WebView) findViewById(R.id.webView);
		findViewById(R.id.img_back).setOnClickListener(this);
		tvTitle= (TextView) findViewById(R.id.tv_title);
		progressBar= (ProgressBar) findViewById(R.id.web_progressBar);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		if(webUrl!=null){
			webView.loadUrl(webUrl);
//			webView.setWebViewClient(new webViewClient());
		}
		webView.setWebChromeClient(new webChromeClient());
	}

	class webChromeClient extends WebChromeClient{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			progressBar.setProgress(newProgress);
			if (progressBar.getProgress() == 100) {
				progressBar.setVisibility(View.GONE);
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			tvTitle.setText(title);
		}
	}

	class webViewClient extends WebViewClient{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			webView.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.img_back:
				finish();
				break;

			default:
				break;
		}
	}


}
