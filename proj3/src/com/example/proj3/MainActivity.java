package com.example.proj3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridLayout.LayoutParams;

public class MainActivity extends Activity {
	

	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	public static View tab01,tab02,tab03,tab04;
	private ImageButton ImgMy,ImgClass,ImgScore,ImgQuery;
	private List<View> mViews = new ArrayList<View>();// 用来存放Tab01-04
	private List<String> myMemo = new ArrayList<String>();	
	private DrawerLayout mDrawerLayout = null;
	private Timer timer;
	private TimerTask task;
	private AppWidgetManager widgetmanager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		updateWidget();
		
		getViewById();
		setListener();
		initViewPage();
		ImgMy.setBackgroundResource(R.drawable.bar_lan1);
		
		setActivityMy();
		setMemoList();
		
		setActivityClass();
		setActivityScore();
		setActivityQuery();
		
		checkNetworkState();
	}

	private void setListener() {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.ib1:
					mViewPager.setCurrentItem(0);    
					break;
				case R.id.ib2:
					mViewPager.setCurrentItem(1);  
					break;
				case R.id.ib3:
					mViewPager.setCurrentItem(2);
					break;
				case R.id.ib4:
					mViewPager.setCurrentItem(3);
					break;
				}
			}
		};

		ImgMy.setOnClickListener(listener);
		ImgClass.setOnClickListener(listener);
		ImgScore.setOnClickListener(listener);
		ImgQuery.setOnClickListener(listener);
	}

	private void getViewById() {
		//4 ImageButtons on bottom
		ImgMy = (ImageButton) findViewById(R.id.ib1);
		ImgClass = (ImageButton) findViewById(R.id.ib2);
		ImgScore = (ImageButton) findViewById(R.id.ib3);
		ImgQuery = (ImageButton) findViewById(R.id.ib4);
        //ViewPager
		mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
		LayoutInflater mLayoutInflater = LayoutInflater.from(this);
		tab01 = mLayoutInflater.inflate(R.layout.activity_my, null);
		tab02 = mLayoutInflater.inflate(R.layout.activity_class, null);
		tab03 = mLayoutInflater.inflate(R.layout.activity_score, null);
		tab04 = mLayoutInflater.inflate(R.layout.activity_query, null);
	}

	@SuppressWarnings("deprecation")
	private void initViewPage() {
        //add view to mViews(ArrayList<>)
		mViews.add(tab01);
		mViews.add(tab02);
		mViews.add(tab03);
		mViews.add(tab04);
        //set Adapter of ViewPager
		mPagerAdapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(mViews.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mViews.size();
			}
		};
		mViewPager.setAdapter(mPagerAdapter);
		//set OnPageChangeListener
	    mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				setAlpha(arg0, arg1, arg2);
			}
			@Override
			public void onPageSelected(int arg0) {
				setBackgroundResourceHUI();
				setBackgroundResourceLAN(arg0);
			}
	    });
	}
	
	protected void setAlpha(int arg0, float alpha, int arg2) {//设置Viewpager滑动时页面透明度
		//set view alpha when the PageView Scrolled
		switch(arg0){
		case 0: tab01.setAlpha(1-alpha); break;
		case 1: tab02.setAlpha(1-alpha); break;
		case 2: tab03.setAlpha(1-alpha); break;
		case 3: tab04.setAlpha(1-alpha); break;
		default: Toast.makeText(this, "设置透明度出问题", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void setBackgroundResourceHUI() {
		//set all imagebutton in hui color picture
		ImgMy.setBackgroundResource(R.drawable.bar_hui1);
		ImgClass.setBackgroundResource(R.drawable.bar_hui2);
		ImgScore.setBackgroundResource(R.drawable.bar_hui3);
		ImgQuery.setBackgroundResource(R.drawable.bar_hui4);
	}
	
	protected void setBackgroundResourceLAN(int no){
		//set no imagebutton in lan color picture
		switch(no){
		case 0: ImgMy.setBackgroundResource(R.drawable.bar_lan1); break;
		case 1: ImgClass.setBackgroundResource(R.drawable.bar_lan2); break;
		case 2: ImgScore.setBackgroundResource(R.drawable.bar_lan3); break;
		case 3: ImgQuery.setBackgroundResource(R.drawable.bar_lan4); break;
		default: break;
		}
	}
	
	/***** My  Activity ******/
	private TextView my_name,my_school;
	private ListView memo;
	private Button add_memo;
	private ImageView iv;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	private void setActivityMy(){
		my_name = (TextView)tab01.findViewById(R.id.my_name);
		my_school = (TextView)tab01.findViewById(R.id.my_school);
		memo = (ListView)tab01.findViewById(R.id.memo);
		iv = (ImageView)tab01.findViewById(R.id.iv);
		mDrawerLayout = (DrawerLayout)tab01.findViewById(R.id.drawer_layout);
		
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}
			
		});
		
		iv.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				chooseFromAlbum();
//				int num = preferences.getInt("img_choose", R.drawable.lake_yin);
//				editor.putInt("img_choose", R.drawable.smie + R.drawable.lake_yin - num);
//				editor.commit();
//				iv.setBackgroundResource(preferences.getInt("img_choose", 0));
				return false;
			}
			
		});
		// sharedPreferences
		preferences = getSharedPreferences("memo"+13354114, MODE_PRIVATE);
		editor = preferences.edit();
		int memo_num = preferences.getInt("memo_num", 0);
		for(int i=0;i<memo_num;i++){
			String a = preferences.getString("memo"+i, "wrong");
			myMemo.add(a);
		}
		
		add_memo = (Button)tab01.findViewById(R.id.add_memo);
		add_memo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				add_memo();
				//mDrawerLayout.openDrawer(Gravity.LEFT);
			}
		});
		
		//TODO: get student name,id, school
		my_name.setText("欢迎，" + "hxc");
		my_school.setText("13354hhh" + "\n" + "移动信息工程学院");
	};
	private void setMemoList() {
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myMemo);
		memo.setAdapter(adapter);
		// 长按删除
		memo.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final String s = (String) parent.getItemAtPosition(position);	
				adapter.remove(s);
				adapter.notifyDataSetChanged();
				editor.clear();
				int n = myMemo.size();
				for(int i=0;i<n;i++){
					String ss = myMemo.get(i);
					editor.putString("memo"+i, ss);
				}
				editor.putInt("memo_num", n);
     		    editor.commit();
				return true;//长按不会触发点击事件
			}
        });
		
	}
	
	private void add_memo() {
		final EditText et = new EditText(this);  
		new AlertDialog.Builder(this).setTitle("添加提醒").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String s = et.getText().toString();
				myMemo.add(s);
				int num = preferences.getInt("memo_num", 0);
				editor.putString("memo"+num, s);
				editor.putInt("memo_num", num+1);
				editor.commit();
			}
		    }).setNegativeButton("取消", null).show(); 
		
	}
	
	
	
	/***** Class  Activity ******/
	private int mWidth;
	private Button class_btn;
	private GridLayout class_grid;
	private Spinner class_year,class_term;
	
    private void setActivityClass() {
    	class_btn = (Button)tab02.findViewById(R.id.class_btn);
		class_grid = (GridLayout)tab02.findViewById(R.id.class_grid);
		class_year = (Spinner)tab02.findViewById(R.id.class_year);
	    class_term = (Spinner)tab02.findViewById(R.id.class_term);
	    
	    DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
	    mWidth = (dm.widthPixels - 120)/5;
	    
	    setClassGrid();
	    addClass();
	    
	    class_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String year = class_year.getSelectedItem().toString();
				String term = class_term.getSelectedItem().toString();
				Toast.makeText(v.getContext(), year+" "+term , Toast.LENGTH_SHORT).show();
				//TODO: send year and term to the service
				class_grid.removeAllViews();
				setClassGrid();
				addClass();
			}
		});
	}

    protected void setClassGrid() {
		//左边课程节数
		for(int i=0;i<15;i++){
			TextView tv = ClassHelper.getTextView1(this,"\n"+(i+1)+"\n",60);
			LayoutParams params = ClassHelper.getLayoutParams(0,i,1);
			class_grid.addView(tv, params);
		}
		//全部格子
		for(int i=0; i<15; i++){
			for(int j=1;j<=5;j++){
				TextView tv = ClassHelper.getTextView1(this," ",mWidth);
				LayoutParams params = ClassHelper.getLayoutParams(j,i,1);
				class_grid.addView(tv, params);
			}
		}
	}
	
	protected void addClass(){
		//加课表
		Shiwei shiwei = new Shiwei();
		for(int i=0; i<14; i++){
			Class my = shiwei.get(i);
			TextView tv = ClassHelper.getTextView(this, my, mWidth);
			LayoutParams params = ClassHelper.getLayoutParams(my.getClassDay(), 
					                    my.getClassNo()-1, my.getClassLength());
			class_grid.addView(tv, params);
		}
	}
	
	/***** Score  Activity ******/
	private Button score_btn;
	private ListView score_list;
	private Spinner score_year,score_term;
    private void setActivityScore(){
    	score_btn = (Button)tab03.findViewById(R.id.score_btn);
		score_list = (ListView)tab03.findViewById(R.id.score_list);
		score_year = (Spinner)tab03.findViewById(R.id.score_year);
	    score_term = (Spinner)tab03.findViewById(R.id.score_term);
	    
	    setScoreList();
	    score_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String year = score_year.getSelectedItem().toString();
				String term = score_term.getSelectedItem().toString();
				Toast.makeText(v.getContext(), year+" "+term , Toast.LENGTH_SHORT).show();
				//TODO: send year and term to the service
				setScoreList();
				Log.e("MainActivity", "setScoreList end2");
			}
		});
	};
	
	protected void setScoreList(){
		Log.e("MainActivity", "setScoreList begin");
		//Toast.makeText(this, "当前无成绩" , Toast.LENGTH_SHORT).show();
		final List<Score> myScore = new ArrayList<Score>();
		for(int i=0;i<25;i++){
			myScore.add(new Score("高等数学线性代数离散数学"+i,"teacher",5,2,71));
		}
		Log.e("MainActivity", "setScoreList begin1");
		ScoreAdapter adapter = new ScoreAdapter(this, R.layout.list_item, myScore);
		score_list.setAdapter(adapter);
		Log.e("MainActivity", "setScoreList end");
		
		// 点击显示课程详细信息
		score_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final Score item = (Score) parent.getItemAtPosition(position);
				show_detail_info(item);
			}
		});
	}
	
	private void show_detail_info(Score item) {
		new AlertDialog.Builder(this).setTitle(item.getName()).setMessage(
				"学分：" + item.getCredit() + "\n老师：" + item.getTeacher() + "\n绩点：" + item.getPoint() + "\n分数：" + item.getScore())
		.setNeutralButton("Close", class_term).show(); 
	}
	
	/***** Query  Activity ******/
	private Button query_class_btn,query_score_btn;
	private Spinner query_class_year,query_class_term;
	private Spinner query_score_year,query_score_term;
    private void setActivityQuery(){
    	query_class_btn = (Button)tab04.findViewById(R.id.query_class_btn);
		query_score_btn = (Button)tab04.findViewById(R.id.query_score_btn);
		query_class_year = (Spinner)tab04.findViewById(R.id.query_class_year);
		query_class_term = (Spinner)tab04.findViewById(R.id.query_class_term);
		query_score_year = (Spinner)tab04.findViewById(R.id.query_score_year);
		query_score_term = (Spinner)tab04.findViewById(R.id.query_score_term);
		
		OnClickListener listener_query = new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO set year and term to get the class or score
				if(v.getId()==R.id.query_class_btn){
					String year = query_class_year.getSelectedItem().toString();
					String term = query_class_term.getSelectedItem().toString();
					Toast.makeText(v.getContext(), "查询 " + year+" "+term + " 课表", 
							Toast.LENGTH_SHORT).show();
				} else {
					String year = query_score_year.getSelectedItem().toString();
					String term = query_score_term.getSelectedItem().toString();
					Toast.makeText(v.getContext(), "查询 " + year+" "+term + " 成绩", 
							Toast.LENGTH_SHORT).show();
				}
				
			}
		};
		
		query_class_btn.setOnClickListener(listener_query);
		query_score_btn.setOnClickListener(listener_query);
	};
	
	private void checkNetworkState() {
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		//获取wifi网络状态
		State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		//获取移动数据网络状态
		State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if((wifiState != State.CONNECTED && mobileState != State.CONNECTED)) {
			TextView tv = (TextView)MainActivity.tab04.findViewById(R.id.query_text);
			tv.setVisibility(View.VISIBLE);
			tv.setText("同学现在没网哦");
		} 
	}
	
	
	
	
	
	
	
	
	
	/////////////////////////
	// **********88
		public static final int TAKE_PHOTO = 1;
		public static final int CROP_PHOTO = 2;
		private Uri imageUri; 
		
		private void chooseFromAlbum(){
			File outputImage = new File(Environment.getExternalStorageDirectory(),
					"output_image.jpg");
			Toast.makeText(this, Environment.getExternalStorageDirectory().toString(), Toast.LENGTH_LONG).show();
	        try {
	            if (outputImage.exists()) {
	                outputImage.delete();
	            }
	            outputImage.createNewFile();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        imageUri = Uri.fromFile(outputImage);
	        Intent intent = new Intent("android.intent.action.PICK");
	        intent.setDataAndType(imageUri, "image/*");
	        intent.putExtra("crop", true);
	        intent.putExtra("scale", true);
	        
	        intent.putExtra("aspectX", 2);
	        intent.putExtra("aspectY", 1);
	        
	        intent.putExtra("outputX", 300);
	        intent.putExtra("outputY", mWidth);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	        startActivityForResult(intent, CROP_PHOTO);
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    switch (requestCode) {
		        case CROP_PHOTO:
		            if (resultCode == RESULT_OK) {
		                try {
		                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
		                            .openInputStream(imageUri));
		                    iv.setImageBitmap(bitmap);
		                } catch (FileNotFoundException e) {
		                    e.printStackTrace();
		                }
		                editor.putBoolean("imageSet", true);
						editor.commit();
		            }
		            break;
		        default:
		            break;
		    }
		}
		// **********88
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			
			super.onResume();
		}
		
		private void updateWidget() {
			widgetmanager = AppWidgetManager.getInstance(getApplicationContext());
			timer = new Timer();
		    
		    task = new TimerTask() {
		      @Override
		      public void run() {
		        // 更新widget的界面
		        ComponentName name = new ComponentName("com.example",
		            "com.example.proj3");// 获取前面参数包下的后参数的Widget
		        RemoteViews views = new RemoteViews(getPackageName(),R.layout.new_app_widget);// 获取Widget的布局
		        views.setTextViewText(R.id.appwidget_text1, "XXXX");//给process_count设置文本
		       
		        widgetmanager.updateAppWidget(name, views);//更新Widget
		      }
		    };
		    timer.scheduleAtFixedRate(task, 1000, 2000);//延迟一秒      更新频率2秒
		}
	    
	    
}
