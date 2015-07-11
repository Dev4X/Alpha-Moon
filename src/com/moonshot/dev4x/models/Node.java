package com.moonshot.dev4x.models;

public class Node {
	int _id;
	String _name;
	String _icon;
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(int id, String name, String icon) {
		// TODO Auto-generated constructor stub
		this._id = id;
		this._name = name;
		this._icon = icon;
	}
	
	public String getName(){
		return this._name;
	}
	
	public void setName(String name){
		this._name = name;
	}
	
	public int getId(){
		return this._id;
	}
	
	public void setId(int id){
		this._id = id;
	}
	
	public String getIcon(){
		return this._icon;
	}
	
	public void setIcon(String icon){
		this._icon = icon;
	}
}
