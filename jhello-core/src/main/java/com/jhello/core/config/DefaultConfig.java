package com.jhello.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jhello.core.handle.ActionHandler;
import com.jhello.core.handle.ExceptionHandler;
import com.jhello.core.handle.ParamPrepareHandler;
import com.jhello.core.handle.ResourceHandler;
import com.jhello.core.utils.StringUtils;
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
		String defaultViewType = (String) this.configProperties.get(ConfigConst.WEB_DEFAULT_VIEW_TYPE);
		if(StringUtils.isEmpty(defaultViewType)){
			return ViewType.JSP;
		}else{
			return Enum.valueOf(ViewType.class, defaultViewType);
		}
	}

	@Override
	public String getDateTimePattern() {
		String dateTimePattern = (String) this.configProperties.get(ConfigConst.COMMON_DATETIME_PATTERN);
		if(StringUtils.isEmpty(dateTimePattern)){
			return "yyyy-MM-dd HH:mm:ss";
		}
		return dateTimePattern;
	}
}
