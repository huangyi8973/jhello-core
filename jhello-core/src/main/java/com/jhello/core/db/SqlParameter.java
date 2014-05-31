package com.jhello.core.db;


import java.util.ArrayList;
import java.util.List;

public class SqlParameter {

	//用于存放参数
	private List<Object> _parameterList;
	public SqlParameter(){
		_parameterList=new ArrayList<Object>();
	}
	public void addObject(Object value){
		_parameterList.add(value);
	}
	public List<Object> getParameters(){
		return _parameterList;
	}
	public int size(){
		return _parameterList.size();
	}
	public Object get(int i){
		return _parameterList.get(i);
	}
}
