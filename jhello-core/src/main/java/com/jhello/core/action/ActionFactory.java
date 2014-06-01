package com.jhello.core.action;

import java.lang.reflect.Method;

import com.jhello.core.pub.HttpMethod;

public final class ActionFactory {

	private static ActionFactory _instance;
	private static Object _lock = new Object();
	private ActionFactory(){}
	
	public static ActionFactory getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new ActionFactory();
				}
			}
		}
		return _instance;
	}
	
	public Action getAction(String url,String strHttpMethod) throws ClassNotFoundException,NoSuchMethodException, SecurityException{
		String actionInfoStr = ActionMapper.getInstance().getActionInfoStr(String.format("%s#%s", strHttpMethod,url));
		if(actionInfoStr != null){
			String[] rs = actionInfoStr.split("#");
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(rs[0]);
			//这里不考虑方法重载
			Method method = null;
			for(Method m : cls.getMethods()){
				if(m.getName().equals(rs[1])){
					method = m;
					break;
				}
			}
			HttpMethod httpMethod = Enum.valueOf(HttpMethod.class, strHttpMethod);
			return new Action(url, cls, method,httpMethod,actionInfoStr);
		}
		return null;
	}
	
}
