package com.jhello.core.controller.cmd;

import com.jhello.core.action.Params;

public abstract class AbstractControllerCmd implements IControllerCmd {

	protected String arg;
	protected Params params;
	public AbstractControllerCmd(String cmdArg,Params params){
		this.arg = cmdArg;
		this.params = params;
	}
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	}
	
	
}
