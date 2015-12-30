package com.example.proj3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyService extends Service {
	
	private Timer timer;
	private TimerTask task;
	private AppWidgetManager widgetmanager;
	
	public MyService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return null;
	}
	
	@Override
	public void onCreate() {
	    timer = new Timer();
	    widgetmanager = AppWidgetManager.getInstance(getApplicationContext());
	    task = new TimerTask() {
	      @Override
	      public void run() {
	        // ����widget�Ľ���
	        ComponentName name = new ComponentName("com.example.proj3",
	            "com.example.proj3.NewAppWidget");// ��ȡǰ��������µĺ������Widget
	        RemoteViews views = new RemoteViews("com.example.proj3",R.layout.new_app_widget);// ��ȡWidget�Ĳ���
	        
	        Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);       //��ȡ��ǰ��� 
	        int month = c.get(Calendar.MONTH)+1;   //��ȡ��ǰ�·� 
	        int day = c.get(Calendar.DAY_OF_MONTH);//��ȡ��ǰ�·ݵ����ں���
	        int week = c.get(Calendar.DAY_OF_WEEK)-1;
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);
	        int second = c.get(Calendar.SECOND);
	        String[] Week = {"��","һ","��","��","��","��","��"};
	        
	        
	        views.setTextViewText(R.id.appwidget_text1, year+"��"+month+"��"+day+"��   ����"+Week[week]+" "+hour+":"+minute+":"+second);
	        
	        Class my_class = getClassForW(week, hour, minute);
	        views.setTextViewText(R.id.appwidget_text2, my_class.getClassMsg());
	       
	        
	        Intent intent = new Intent(MyService.this, MainActivity.class);
	        PendingIntent pendingIntent = PendingIntent.getBroadcast(
	            getApplicationContext(), 0, intent, 0);
	        views.setOnClickPendingIntent(R.id.widget_click, pendingIntent);// �������ļ��е�btn_clear���õ���¼�
	        widgetmanager.updateAppWidget(name, views);//����Widget
	      }
	    };
	    timer.scheduleAtFixedRate(task, 1000, 1000);//�ӳ�һ��      ����Ƶ��2��
	    super.onCreate();
	  }
	
		public Class getClassForW(int day, int hour, int min) {
			Shiwei shiwei = new Shiwei();
			ArrayList<Class> todayClass = new ArrayList<Class>();
			int i;
			for(i=0; i<14; i++)
			{
				Class c1 = shiwei.get(i);
				if(c1.getClassDay() == day) {
					todayClass.add(c1);
				}
			}
				
			int[] H = { 0, 8, 8, 9,10,11,12,13,14,15,16,17,18,19,19,20};
			int[] M = { 0, 0,55,50,45,40,35,30,25,20,15,10,05,00,55,50};
			int last = -1;
			int minTime = 3000;
			int time = hour*100 + min;
			int n = todayClass.size();
			for(i=0; i<n; i++){
				Class my = todayClass.get(i);
				int no = my.getClassNo();
				int mTime = H[no]*100 + M[no];
				if(mTime >= time){
					if(mTime < minTime) {
						last = i;
						minTime = mTime;
					}
				}
			}
			if(last==-1) return new Class("�����Ѿ�û����","�ؼ�˯����",1,1,1);
			return todayClass.get(last);
		}
	  @Override
	  public void onDestroy() {
	    timer.cancel();
	    timer = null;
	    task = null;
	    super.onDestroy();
	  }
}
