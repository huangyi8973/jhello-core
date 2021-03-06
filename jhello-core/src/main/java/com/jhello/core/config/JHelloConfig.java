package com.jhello.core.config;

import java.util.Properties;

import com.jhello.core.action.ActionProvider;
import com.jhello.core.view.ViewType;

public final class JHelloConfig extends AbstractConfig{
	
	private AbstractConfig config;
	private static JHelloConfig instance;
	private static Object lock = new Object();
	private JHelloConfig(){}
	
	public static  JHelloConfig getInstance(){
		if(instance == null){
			synchronized (lock) {
				if(instance == null){
					instance  = new JHelloConfig();
				}
			}
		}
		return instance;
	}
	/**
	 * 获取处理器，有序
	 * @return
	 */
	public Class<?>[] getHandles(){
		return config.getHandles();
	}
	
	public String getActionScanPackage(){
		return config.getConfigProperties().getProperty(ConfigConst.WEB_ACTION_SCAN_PACKAGE);
	}
	
	public String getAspectScanPackage(){
		return config.getConfigProperties().getProperty(ConfigConst.WEB_ASPECT_SCAN_PACKAGE);
	}
	
	public String getConfigValue(String key){
		return config.getConfigProperties().getProperty(key);
	}
	
	public int getIntConfigValue(String key){
		String value = getConfigValue(key);
		return Integer.parseInt(value);
	}
	
	public double getDoubleConfigValue(String key){
		String value = getConfigValue(key);
		return Double.parseDouble(value);
	}
	
	public boolean getBooleanConfigValue(String key){
		String value = getConfigValue(key);
		return Boolean.parseBoolean(value);
	}

	public AbstractConfig getConfig() {
		return config;
	}

	public void setConfig(AbstractConfig config) {
		this.config = config;
	}
	
	public ViewType getDefaultViewType(){
		return this.config.getDefaultViewType();
	}
	
	public String getDateTimePattern(){
		return this.config.getDateTimePattern();
	}

	@Override
	public Properties getConfigProperties() {
		return null;
	}

	@Override
	public ActionProvider getActionProvider() throws Exception {
		return this.config.getActionProvider();
	}
}
