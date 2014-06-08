package com.jhello.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.action.Action;
import com.jhello.core.action.ActionProvider;

public class SpringActionProvider extends ActionProvider {

	private static Logger logger = LoggerFactory.getLogger(SpringActionProvider.class);
	@Override
	public Object getActionInstance(Action action) throws Exception {
		logger.debug("Action From Spring:"+action.getControllerCls().getName());
		return SpringUtil.getBean(action.getControllerCls());
	}

}
