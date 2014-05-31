package com.jhello.core.viewrender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhello.core.model.Model;
import com.jhello.core.view.View;

public abstract class ViewRender {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	

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

	public ViewRender(HttpServletRequest req, HttpServletResponse resp){
		this.request = req;
		this.response = resp;
	}
	
	public ViewRender(){}
	
	public abstract void render(View view, Model model) throws Exception;
}
