package com.example.proj3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class WelcomeActivity extends Activity {
	
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View rootView = LayoutInflater.from(this).inflate(R.layout.activity_welcome, null);
		
		/**
		 * ���ﲻ��ʹ��findViewById(R.layout.acitivyt_spash)����������
		 * ��Ϊ��û�п�ʼ����setContentView()������Ҳ����˵��û����ǰ��Activity
		 * ������ͼ����ǰActivity Root ViewΪnull��findViewById()�����Ǵӵ�ǰ
		 * Activity��Root View�л�ȡ����ͼ��������ʱ��ᱨNullPointerException�쳣
		 * 
		 * View rootView = findViewById(R.layout.activity_splash);
		 * 
		 */

		setContentView(rootView);
		
		mHandler = new Handler();
		
		//��ʼ�����䶯��
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
		//���ö���������
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//����������������ʱ����ʼ��ת��MainActivity��ȥ
				
				Runnable mRunnable = new Runnable() {
					public void run() {
						Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);						
						startActivity(i);
						WelcomeActivity.this.finish();
					}
				};
				mHandler.postDelayed(mRunnable, 2000);
			}
		});
		
		//��ʼ���Ŷ���
		rootView.startAnimation(animation);
	}
}
