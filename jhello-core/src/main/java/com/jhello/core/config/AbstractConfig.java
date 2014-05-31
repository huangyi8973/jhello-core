package com.jhello.core.config;

import java.util.Properties;

public abstract class AbstractConfig {

	public abstract Class<?>[] getHandles();
	
	public abstract Properties getConfigProperties();
}
