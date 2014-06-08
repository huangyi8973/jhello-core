package com.jhello.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.cache.LRUCache;
import com.jhello.core.utils.StringUtils;

/**
 * VO方法的缓存
 * @author huangy
 * @date   2013-4-13
 */
public class VOMethodCache {
	private LRUCache cache=new LRUCache("VOCache", 1000);
	private Logger log=LoggerFactory.getLogger(VOMethodCache.class);
	private VOMethodCache(){}
	private static VOMethodCache m_instance=new VOMethodCache();
	public static VOMethodCache getInstance(){
		return m_instance;
	}

	public Method getMethod(Class<? extends IBaseVO> cls,String methodName){
		Method method=(Method) cache.get(cls.getName()+methodName);
		if(method==null){
			initMethod(cls);
			method=(Method) cache.get(cls.getName()+methodName);
		}
		return method;
	}
	private void initMethod(Class<? extends IBaseVO> cls){
		Field[] fields = cls.getDeclaredFields();
		List<String> setMethodNameList=new ArrayList<String>();
		for(Field field : fields){
			String methodName="set"+StringUtils.firstLetterToUpper(field.getName());
			setMethodNameList.add(methodName);
		}
		Method[] Methods=cls.getMethods();
		int c=0;
		for(Method m : Methods){
			if(setMethodNameList.contains(m.getName())){
				cache.put(cls.getName()+m.getName(), m);
				c++;
			}
		}
		log.debug(String.format("%s类共添加set方法%d个", cls.getName(),c));
	}
	
}
