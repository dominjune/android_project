package com.example.proj3;

import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
		}
		
		/*Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);       //获取当前年份 
        int month = c.get(Calendar.MONTH)+1;   //获取当前月份 
        int day = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        int week = c.get(Calendar.DAY_OF_WEEK)-1;
        String[] Week = {"天","一","二","三","四","五","六"};
        
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
		
		// 点击
		views.setOnClickPendingIntent(R.id.widget_click, pendingIntent);  
		// 设置文本
		//views.setTextViewText(R.id.appwidget_text1, ""+year+"年"+month+"月"+day+"日   星期"+Week[week]);
		//views.setTextViewText(R.id.appwidget_text2, "移动应用开发");
		//views.setTextViewText(R.id.appwidget_text3, "D207");
		
		appWidgetManager.updateAppWidget(appWidgetIds, views);*/
		
		
        
		
	}
	
	@Override
	  public void onDeleted(Context context, int[] appWidgetIds) {
	    super.onDeleted(context, appWidgetIds);
	    // 当某一个widget被删除的时候 会执行ondelete方法
	    Intent intent = new Intent(context, MyService.class);
	    context.stopService(intent);
	  }
	
	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
		Intent intent = new Intent(context, MyService.class);
	    context.startService(intent);
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}

	static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {

		CharSequence widgetText = context.getString(R.string.appwidget_text);
		// Construct the RemoteViews object
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.new_app_widget);
		views.setTextViewText(R.id.appwidget_text1, widgetText);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
