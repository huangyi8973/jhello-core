package com.jhello.core.action;

public abstract class ActionProvider {

	public abstract Object getActionInstance(Action action) throws Exception;
}
