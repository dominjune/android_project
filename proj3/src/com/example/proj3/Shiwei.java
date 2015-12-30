package com.example.proj3;

public class Shiwei {
    private Class[] my = new Class[]{
	    new Class("云计算应用开发","C307",1,2,2),
	    new Class("云计算应用开发实验","A502",1,8,2),
	    new Class("嵌入式系统导论实验","A517",2,4,2),
	    new Class("无线传感器网络实验","A509",2,8,2),
	    new Class("无线传感器网络","C307",2,10,2),
	    new Class("羽毛球初级班","体育馆",2,12,3),
	    new Class("计算机网络原理实验","A102",3,2,2),
	    new Class("计算机网络原理","C307",3,4,2),
	    new Class("移动应用开发","C207",3,8,2),
	    new Class("面向对象程序设计","C301",3,13,3),
	    new Class("嵌入式系统导论","C307",4,8,2),
	    new Class("移动应用开发实验","A502",4,10,2),
	    new Class("人工智能实验","A102",5,4,2),
	    new Class("人工智能","D207",5,8,2) };
    
    Class get(int i){
    	return my[i];
    }
}
