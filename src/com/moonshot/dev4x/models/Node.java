package com.moonshot.dev4x.models;

public class Node {
	int _id;
	String _name;
	String _icon;
	String _content;
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(int id, String name, String icon, String content) {
		// TODO Auto-generated constructor stub
		this._id = id;
		this._name = name;
		this._icon = icon;
		this._content = content;
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
	
	public String getContent(){
		return this._content;
	}
	
	public void setContent(String content){
		this._content = content;
	}
}
