package com.jhello.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jhello.core.handle.ActionHandler;
import com.jhello.core.handle.ExceptionHandler;
import com.jhello.core.handle.ParamPrepareHandler;
import com.jhello.core.handle.ResourceHandler;
import com.jhello.core.view.ViewType;

public class DefaultConfig extends AbstractConfig {

	private Properties configProperties;
	
	public DefaultConfig(){
		configProperties = new Properties();
		InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			configProperties.load(ins);
		} catch (IOException e) {
			throw new RuntimeException("config init error",e);
		}
	}
	
	public Properties getConfigProperties() {
		return configProperties;
	}


	/**
	 * 获取处理器，有序
	 * @return
	 */
	public Class<?>[] getHandles(){
		return new Class<?>[]{
				ExceptionHandler.class,
				ParamPrepareHandler.class,
				ActionHandler.class,
				ResourceHandler.class
		};
	}
	
	@Override
	public ViewType getDefaultViewType() {
		return ViewType.JSP;
	}
}
