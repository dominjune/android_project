package com.example.proj3;

public class Class {
    private String name,room; //�γ�����,�Ͽεص�
    private int day,no,length;//���ڼ����ڼ��ڡ���ʱ
    
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
