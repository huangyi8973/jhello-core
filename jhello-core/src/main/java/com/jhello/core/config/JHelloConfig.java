package com.jhello.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jhello.core.handle.ActionHandler;
import com.jhello.core.handle.ExceptionHandler;
import com.jhello.core.handle.ParamPrepareHandler;
import com.jhello.core.handle.ResourceHandler;

public class JHelloConfig {

	private static Properties config;
	
	static{
		config = new Properties();
		InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		try {
			config.load(ins);
		} catch (IOException e) {
			throw new RuntimeException("config init error",e);
		}
	}
	
	/**
	 * 获取处理器，有序
	 * @return
	 */
	public static Class<?>[] getHandles(){
		return new Class<?>[]{
				ExceptionHandler.class,
				ParamPrepareHandler.class,
				ActionHandler.class,
				ResourceHandler.class
		};
	}
	
	public static String getActionScanPackage(){
		return config.getProperty(ConfigConst.WEB_ACTION_SCAN_PACKAGE);
	}
	
	public static String getAspectScanPackage(){
		return config.getProperty(ConfigConst.WEB_ASPECT_SCAN_PACKAGE);
	}
	
	public static String getConfigValue(String key){
		return config.getProperty(key);
	}
	
	public static int getIntConfigValue(String key){
		String value = getConfigValue(key);
		return Integer.parseInt(value);
	}
	
	public static double getDoubleConfigValue(String key){
		String value = getConfigValue(key);
		return Double.parseDouble(value);
	}
	
	public static boolean getBooleanConfigValue(String key){
		String value = getConfigValue(key);
		return Boolean.parseBoolean(value);
	}
}
