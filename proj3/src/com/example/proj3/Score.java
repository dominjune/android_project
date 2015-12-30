package com.example.proj3;

public class Score {
    private String name,teacher;
    private int credit;
    private double point,score;
    
    public Score(String n, String t, int c, double p, double s){
    	name = n;
    	teacher = t;
    	credit = c;
    	point = p;
    	score = s;
    }
    
    public String getName(){
    	return name;
    }
    
    public String getTeacher(){
    	return teacher;
    }
    
    public int getCredit(){
    	return credit;
    }
    
    public double getPoint(){
    	return point;
    }
    
    public double getScore(){
    	return score;
    }
}
