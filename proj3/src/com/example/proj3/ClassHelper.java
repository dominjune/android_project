package com.example.proj3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;

public class ClassHelper {
	@SuppressLint("NewApi")
	public static TextView getTextView(Context context, Class my, int viewWidth){
    	TextView tv = new TextView(context);
    	// ����TextView�����ݡ���ȡ�����������������ɫ
    	tv.setText(my.getClassMsg());
		tv.setWidth(viewWidth);
		tv.setGravity(Gravity.CENTER);
		tv.setPadding(10, 10, 10, 10);
		tv.setTextColor(Color.WHITE);
		// �����ɫ
		int red = (int)(Math.random() * 256);
		int green = (int)(Math.random() * 256);
		int blue = (int)(Math.random() * 256);
		int color = 0xb0000000 | (red << 16) | (green << 8) | blue;
		int backColor = 0xffcecece;
		// ��������
		GradientDrawable drawable = new GradientDrawable();   // ����Shape
		drawable.setGradientType(GradientDrawable.RECTANGLE); // ���þ���
		drawable.setColor(color);         // �����������ɫ
		drawable.setStroke(1, backColor); // �������
		drawable.setCornerRadius(10);     // �����ĽǶ�ΪԲ��
		tv.setBackground(drawable);

    	return tv;
    }
	
	public static TextView getTextView1(Context context, String text, int width){
    	TextView tv = new TextView(context);
    	// ����TextView�����ݡ���ȡ�����������������ɫ
    	tv.setText(text);
		tv.setWidth(width);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundResource(R.drawable.shape_timetable);
		
    	return tv;
    }
	
	@SuppressLint("NewApi")
	public static LayoutParams getLayoutParams(int col, int row, int length){
    	LayoutParams params = new LayoutParams();
		params.columnSpec = GridLayout.spec(col);
		params.rowSpec = GridLayout.spec(row,length, GridLayout.FILL);
        return params;
    }
}
