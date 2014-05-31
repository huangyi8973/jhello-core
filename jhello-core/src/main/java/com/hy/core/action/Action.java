package com.hy.core.action;

import java.lang.reflect.Method;

import com.hy.core.pub.HttpMethod;

public class Action {

	private String url;
	private Class<?> controllerCls;
	private Method method;
	private HttpMethod httpMethod;
	private String clsAndMethod;
	private Params params;
	
	public Action(String url, Class<?> controllerCls, Method method,HttpMethod httpMethod,String clsAndMethod) {
		super();
		this.url = url;
		this.controllerCls = controllerCls;
		this.method = method;
		this.httpMethod = httpMethod;
		this.clsAndMethod = clsAndMethod;
	}

	
	public String getClsAndMethod() {
		return clsAndMethod;
	}


	public void setClsAndMethod(String _clsAndMethod) {
		this.clsAndMethod = _clsAndMethod;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<?> getControllerCls() {
		return controllerCls;
	}

	public void setControllerCls(Class<?> controllerCls) {
		this.controllerCls = controllerCls;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}


	public Params getParams() {
		return params;
	}


	public void setParams(Params params) {
		this.params = params;
	}


	@Override
	public String toString() {
		return "Action [_url=" + url + ", _httpMethod=" + httpMethod
				+ ", _clsAndMethod=" + clsAndMethod + "]";
	}


	
}
