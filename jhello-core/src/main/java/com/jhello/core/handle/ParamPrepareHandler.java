package com.jhello.core.handle;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhello.core.action.Params;

public class ParamPrepareHandler extends Handler {

	public ParamPrepareHandler(HttpServletRequest req,
			HttpServletResponse resp) {
		super(req, resp);
	}

	@Override
	public void handle() throws Exception {
		String ctx = String.format("%s://%s:%d%s", this.getRequest()
				.getScheme(), this.getRequest().getServerName(), this
				.getRequest().getServerPort(), this.getRequest()
				.getContextPath());
		//参数封装
		Params params =  prepareParams();
		
		this.getRequest().setAttribute(Params.CONTEXT_PATH, ctx);
		this.getRequest().setAttribute("params", params);
		this.nextHandle();
	}

	private Params prepareParams() {
		Params params = new Params();
		params.setRequest(this.getRequest());
		params.setResponse(this.getResponse());
		//参数
		for(Enumeration<String> e = this.getRequest().getParameterNames();e.hasMoreElements();){
			String k = e.nextElement();
			String v  = this.getRequest().getParameter(k);
			params.put(k, v);
		}
		return params;
	}
}
