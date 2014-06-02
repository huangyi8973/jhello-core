package com.jhello.core.config;

import java.util.Properties;

import com.jhello.core.view.ViewType;

public abstract class AbstractConfig {

	public abstract Class<?>[] getHandles();
	
	public abstract Properties getConfigProperties();
	
	public abstract ViewType getDefaultViewType();
	
	public abstract String getDateTimePattern();
}
