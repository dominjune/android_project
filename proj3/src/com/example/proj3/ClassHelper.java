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
    	// 设置TextView的内容、宽度、重力、填充和字体颜色
    	tv.setText(my.getClassMsg());
		tv.setWidth(viewWidth);
		tv.setGravity(Gravity.CENTER);
		tv.setPadding(10, 10, 10, 10);
		tv.setTextColor(Color.WHITE);
		// 随机颜色
		int red = (int)(Math.random() * 256);
		int green = (int)(Math.random() * 256);
		int blue = (int)(Math.random() * 256);
		int color = 0xb0000000 | (red << 16) | (green << 8) | blue;
		int backColor = 0xffcecece;
		// 创建背景
		GradientDrawable drawable = new GradientDrawable();   // 生成Shape
		drawable.setGradientType(GradientDrawable.RECTANGLE); // 设置矩形
		drawable.setColor(color);         // 内容区域的颜色
		drawable.setStroke(1, backColor); // 四周描边
		drawable.setCornerRadius(10);     // 设置四角都为圆角
		tv.setBackground(drawable);

    	return tv;
    }
	
	public static TextView getTextView1(Context context, String text, int width){
    	TextView tv = new TextView(context);
    	// 设置TextView的内容、宽度、重力、填充和字体颜色
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
