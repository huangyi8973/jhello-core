package com.jhello.core.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhello.core.config.JHelloConfig;

public class ActionInvoker {

	private Action action;
	
	public ActionInvoker(Action action){
		this.action = action;
	}
	
	public Object invoke() throws Exception{
		Object controller = JHelloConfig.getInstance().getActionProvider().getActionInstance(action);
		Method method = action.getMethod();
		//根据方法的参数类型塞入参数
		Map<Class<?>,Object> paramMap = new HashMap<Class<?>,Object>();
		paramMap.put(HttpServletRequest.class, this.action.getParams().getRequest());
		paramMap.put(HttpServletResponse.class, this.action.getParams().getResponse());
		paramMap.put(Params.class, this.action.getParams());
		
		List<Object> argList = new ArrayList<Object>();
		for(Class<?> cls : method.getParameterTypes()){
			argList.add(paramMap.get(cls));
		}
		return method.invoke(controller, argList.toArray());
	}
}
