package com.firedata.sdkSample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.firedata.sdk.Firedata;
import com.firedata.sdk.Props;

public class WebViewActivity extends Activity {
	WebView webview1;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		//请务必在onDestroy 调用 Firedata.pageViewEnd("/测试WebView");
		Firedata.pageViewStart("/测试WebView");

		webview1 = (WebView) findViewById(R.id.myWebview);
		// 绑定统计，在网页中也可以追踪事件啦
		Firedata.bindWebview(webview1, "/inWEB", Props.eventProps("from", "Android"));

		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				Log.i("webViewDemo", "load url:"+url);
				// 记录访问的页面
				Firedata.event("/WEBVIEW", "访问页面", Props.eventProps("url", url));
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});

		webview1.loadUrl("file:///android_asset/firedata.html");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Firedata.pageViewEnd("/测试WebView");
	}

}
