package com.hy.core.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 处理器
 * @author Huangyi
 *
 */
public abstract class Handler {

	
	protected Handler _nextHandle;
	protected HttpServletRequest _request;
	protected HttpServletResponse _response;
	
	public Handler(HttpServletRequest req, HttpServletResponse resp){
		this._request = req;
		this._response = resp;
	}
	
	public HttpServletRequest getRequest() {
		return _request;
	}

	public void setRequest(HttpServletRequest request) {
		this._request = request;
	}

	public HttpServletResponse getResponse() {
		return _response;
	}

	public void setResponse(HttpServletResponse response) {
		this._response = response;
	}

	public void setNextHandle(Handler nextHandle){
		this._nextHandle = nextHandle;
	}
	
	public Handler getNextHandle(){
		return _nextHandle;
	}
	
	public abstract void handle() throws Exception;
	
	/**
	 * 交给下一个处理器
	 * @throws Exception
	 */
	public void nextHandle() throws Exception{
		if(this.getNextHandle() != null){
			this.getNextHandle().handle();
		}
	}
}
