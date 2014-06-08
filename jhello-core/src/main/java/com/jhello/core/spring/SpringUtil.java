package com.jhello.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext act)
			throws BeansException {
		applicationContext = act;
	}
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	public static Object getBean(Class<?> beanType){
		return applicationContext.getBean(beanType);
	}

}
