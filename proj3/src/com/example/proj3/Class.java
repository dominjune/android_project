package com.example.proj3;

public class Class {
    private String name,room; //课程名称,上课地点
    private int day,no,length;//星期几、第几节、课时
    
    public Class(String name, String room, int day, int no, int length){
    	this.name = name;
    	this.room = room;
    	this.day = day;
    	this.no = no;
    	this.length = length;
    }
    
    public String getClassMsg(){
    	String ans = name+"\n"+room;
    	return ans;
    }
    
    public int getClassDay(){
    	return day;
    }
    
    public int getClassNo(){
    	return no;
    }
    
    public int getClassLength(){
    	return length;
    }
    
}
