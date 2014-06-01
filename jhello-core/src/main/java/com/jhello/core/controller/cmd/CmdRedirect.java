package com.jhello.core.controller.cmd;

import javax.servlet.http.HttpServletResponse;

import com.jhello.core.action.Params;

/**
 * 执行跳转
 * @author Huangyi
 *
 */
public class CmdRedirect extends AbstractControllerCmd {

	
	public CmdRedirect(String arg, Params params) {
		super(arg, params);
	}

	@Override
	public Object execute() throws Exception {
		HttpServletResponse resp = this.getParams().getResponse();
		if(this.getArg().startsWith("/")){
			resp.sendRedirect(String.format("%s%s", this.getParams().getContextPath(),this.getArg()));
		}else{
			//跳转到站外的情况
			resp.sendRedirect(this.getArg());
		}
		return null;
	}

}
