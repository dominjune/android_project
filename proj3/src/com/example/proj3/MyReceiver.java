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
		//��ȡwifi����״̬
		State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		//��ȡ�ƶ���������״̬
		State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		
//		Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);       //��ȡ��ǰ��� 
//        int month = c.get(Calendar.MONTH)+1;   //��ȡ��ǰ�·� 
//        int day = c.get(Calendar.DAY_OF_MONTH);//��ȡ��ǰ�·ݵ����ں���
//        int week = c.get(Calendar.DAY_OF_WEEK)-1;
//        String[] Week = {"��","һ","��","��","��","��","��"};
        
		// ������
		if(!(wifiState != State.CONNECTED && mobileState != State.CONNECTED)) {
//			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//			
//			views.setTextViewText(R.id.appwidget_text, ""+year+"��"+month+"��"+day+"��   ����"+Week[week]);
//			
//			AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context.getApplicationContext(),
//					NewAppWidget.class), views);
			
			TextView tv = (TextView)MainActivity.tab04.findViewById(R.id.query_text);
			tv.setVisibility(View.GONE);
			
		} else {
			TextView tv = (TextView)MainActivity.tab04.findViewById(R.id.query_text);
			tv.setVisibility(View.VISIBLE);
			tv.setText("ͬѧ����û��Ŷ");
		}
		
		
	}
}
