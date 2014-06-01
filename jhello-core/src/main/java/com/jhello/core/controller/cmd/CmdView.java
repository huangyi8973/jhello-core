package com.jhello.core.controller.cmd;

import com.jhello.core.action.Params;
import com.jhello.core.modelview.ModelAndView;
import com.jhello.core.view.JspView;

public class CmdView extends AbstractControllerCmd {

	public CmdView(String arg, Params params) {
		super(arg, params);
	}

	@Override
	public Object execute() throws Exception {
		ModelAndView mv =new ModelAndView();
		JspView view = new JspView(this.getArg());
		mv.setView(view);
		return mv;
	}

}
