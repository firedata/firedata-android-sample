package com.firedata.sdkSample;

import com.firedata.sdk.Firedata;
import com.firedata.sdk.Props;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Firedata.init(this.getApplicationContext(), "UFANSCJ55O", null);//FDU7X5BEQS
		Firedata.enableCrashReporting();
		
		//打开切换到后台的监控
		Firedata.enableForegroundTracking(this.getApplication());
		
		//打开调试，会打印更多信息
		Firedata.setDebug(BuildConfig.DEBUG);

		// Start the tracker in manual dispatch mode...

		// ...alternatively, the tracker can be started with a dispatch interval
		// (in seconds).
		// tracker.startNewSession("UA-YOUR-ACCOUNT-HERE", 20, this);

		setContentView(R.layout.main);
		Button createEventButton = (Button) findViewById(R.id.NewEventButton);
		createEventButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Firedata.event("/点击", // Category
						"不带属性点击"// Action
						); 
				Firedata.event("/点击", // Category
						"带属性点击", // Action
						Props.eventProps("btn_color", "red")
				);
				
				Firedata.event("/文章", // Category
						"点赞", // Action
						Props.contentInfoSet("散文", "sanwen123").content("title", "虫儿飞").event("likeNum", 23)
				);
			}
		});

		Button logException = (Button) findViewById(R.id.LogExceptionButton);
		logException.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("null")
			@Override
			public void onClick(View v) {
				String s = null;
				try {
					s.length();
				} catch (Exception e) {
					Firedata.exception(e, "故意的");
				}
			}
		});
		
		Button crashButton = (Button) findViewById(R.id.CrashButton);
		crashButton.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("null")
			@Override
			public void onClick(View v) {
				String s = null;
				s.length();
			}
		});

		Button userInfoButton = (Button) findViewById(R.id.UserInfoButton);
		userInfoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 设置用户信息
				Firedata.onLogin("user123", Firedata.toMap("age", "132"));
			}
		});
		
		Button webViewButton = (Button) findViewById(R.id.WebViewButton);
		webViewButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		Button quitButton = (Button) findViewById(R.id.QuitButton);
		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		//请务必在onDestroy 调用 Firedata.pageViewEnd("/首页");
		Firedata.pageViewStart("/首页");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 停止本页追踪
		Firedata.pageViewEnd("/首页");
	}
}
