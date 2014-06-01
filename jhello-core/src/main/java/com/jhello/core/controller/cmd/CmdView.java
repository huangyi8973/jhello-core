package com.jhello.core.controller.cmd;

import com.jhello.core.action.Params;
import com.jhello.core.config.JHelloConfig;
import com.jhello.core.modelview.ModelAndView;
import com.jhello.core.view.View;
import com.jhello.core.view.ViewFactory;

public class CmdView extends AbstractControllerCmd {

	public CmdView(String arg, Params params) {
		super(arg, params);
	}

	@Override
	public Object execute() throws Exception {
		ModelAndView mv =new ModelAndView();
		ViewFactory factory = new ViewFactory();
		View view = factory.createView(JHelloConfig.getInstance().getDefaultViewType(), this.getArg());
		mv.setView(view);
		return mv;
	}

}
