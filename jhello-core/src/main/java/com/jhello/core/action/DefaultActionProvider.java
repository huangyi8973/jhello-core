package com.jhello.core.action;

public class DefaultActionProvider extends ActionProvider {

	@Override
	public Object getActionInstance(Action action) throws Exception {
		return action.getControllerCls().newInstance();
	}

}
