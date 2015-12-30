package com.example.proj3;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MyReceiver extends BroadcastReceiver {
	public MyReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//获取wifi网络状态
		State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		//获取移动数据网络状态
		State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		
//		Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);       //获取当前年份 
//        int month = c.get(Calendar.MONTH)+1;   //获取当前月份 
//        int day = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
//        int week = c.get(Calendar.DAY_OF_WEEK)-1;
//        String[] Week = {"天","一","二","三","四","五","六"};
        
		// 有网络
		if(!(wifiState != State.CONNECTED && mobileState != State.CONNECTED)) {
//			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//			
//			views.setTextViewText(R.id.appwidget_text, ""+year+"年"+month+"月"+day+"日   星期"+Week[week]);
//			
//			AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context.getApplicationContext(),
//					NewAppWidget.class), views);
			
			TextView tv = (TextView)MainActivity.tab04.findViewById(R.id.query_text);
			tv.setVisibility(View.GONE);
			
		} else {
			TextView tv = (TextView)MainActivity.tab04.findViewById(R.id.query_text);
			tv.setVisibility(View.VISIBLE);
			tv.setText("同学现在没网哦");
		}
		
		
	}
}
