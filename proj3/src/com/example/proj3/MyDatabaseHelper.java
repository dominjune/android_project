package com.example.proj3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//辅助类，主要用来管理数据库的创建和版本的管理
public class MyDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "ClassAndScore.db";
    private static final String TABLE_NAME = "ClassAndScore";
    
	public MyDatabaseHelper(Context context) {
		//创建数据库ClassAndScore.db
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建表
		String CREATE_TABLE = "create table "+TABLE_NAME+"(_id integer primary key autoincrement,"
				              + " _time text not null, _msg not null);";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("MyDatabaseHelper", "OnUpgrade");
		db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
		onCreate(db);
		Log.d("MyDatabaseHelper", "OnUpgrade end");
	}
    
	//插入
	public long insert(int no, String time, String msg){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("_time", time);
		values.put("_msg", msg);
		long id = db.insert(TABLE_NAME, null, values);
		db.close();
		return id;
	}
	
	//更新 
	public int update(int no, String time, String msg){
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "_time = ?";
		String[] whereArgs = {time};
		
		ContentValues values = new ContentValues();
		values.put("_time", time);
		values.put("_msg", msg);
		int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
		db.close();
		return rows;
	}
	
	//删除
	public int delete(int no, String time){
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "_time = ?";
		String[] whereArgs = {time};
		
		int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
		db.close();
		return rows;
	}
	
	//查询
	public Cursor query(int no, String time){
		SQLiteDatabase db = getWritableDatabase();
		return db.query(TABLE_NAME, null, null, null, null, null, null);
	}
}
