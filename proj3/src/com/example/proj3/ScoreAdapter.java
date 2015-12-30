package com.example.proj3;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScoreAdapter extends ArrayAdapter<Score>{
    private int resourceId;
	
	public ScoreAdapter(Context context, int textViewResourceId, List<Score> scores) {
		super(context, textViewResourceId, scores);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(this.getContext()).inflate(resourceId,null);
		TextView class_name = (TextView)view.findViewById(R.id.class_name);
		TextView class_teacher = (TextView)view.findViewById(R.id.class_teacher);
		TextView class_credit = (TextView)view.findViewById(R.id.class_credit);
		TextView class_point = (TextView)view.findViewById(R.id.class_point);
		TextView class_score = (TextView)view.findViewById(R.id.class_score);
		
		Score score = getItem(position);
		class_name.setText(score.getName());
		class_teacher.setText(score.getTeacher());
		class_credit.setText("5");
		class_point.setText("1");
		class_score.setText("2");
		
		if (position % 2 == 0) {  
	        view.setBackgroundColor(Color.parseColor("#b3ffffff"));  
	    } else {  
	    	view.setBackgroundColor(Color.parseColor("#5500ccff"));  
	    }
		
		return view;
	}
    
}
