package com.jhello.core.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Params extends HashMap<String, String> {

	public final static String CONTEXT_PATH = "ctx";
	
	private static final long serialVersionUID = 1525460778409366498L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String getContextPath(){
		return (String) this.getRequest().getAttribute(CONTEXT_PATH);
	}
	
}
