package com.hy.core.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.hy.core.utils.StringUtils;

public class VOUtils {

	/**
	 * 获得VO类中有get/set的变量
	 * @param cls
	 * @return
	 * @author huangy
	 * @date 2013-4-13 下午12:49:05
	 */
	public static String[] getAvailableFields(Class<? extends IBaseVO> cls){
		Field[] fields=cls.getDeclaredFields();
		List<String> fieldList=new ArrayList<String>();
		for(Field field : fields){
			fieldList.add(field.getName());
		}
		Method[] methods=cls.getDeclaredMethods();
		List<String> methodNameList=new ArrayList<String>();
		for(Method method : methods){
			methodNameList.add(method.getName());
		}
		List<String> resultFieldNames=new ArrayList<String>();
		for(String fieldName : fieldList){
			String getMethodName="get"+StringUtils.firstLetterToUpper(fieldName);
			String setMethodName="set"+StringUtils.firstLetterToUpper(fieldName);
			if(methodNameList.contains(getMethodName) && methodNameList.contains(setMethodName)){
				resultFieldNames.add(fieldName);
			}
		}
		return resultFieldNames.toArray(new String[0]);
	}
	
	public static String getTableName(Class<? extends IBaseVO> cls) throws InstantiationException, IllegalAccessException{
		IBaseVO vo=cls.newInstance();
		return vo.getTableName();
	}
	
	
	
	/**
	 * 获取VO的值
	 * 
	 * @param columnName 字段名
	 * @return 字段值
	 * @author huangy
	 * @date 2012-11-15 上午4:42:48
	 */
	public static Object getValue(IBaseVO vo,String columnName) {
		columnName = columnName.toLowerCase();
		Class<? extends IBaseVO> c = vo.getClass();
		// 有这个字段，则获取这个字段的值
		String methodName = "get" + StringUtils.firstLetterToUpper(columnName);
		try {
			Method method = c.getMethod(methodName);
			return method.invoke(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设置值
	 * 
	 * @see cn.hy.vo.IBaseVO#setValue(java.lang.String, java.lang.Object)
	 * @param columnName 字段名称
	 * @param 字段值
	 * @author huangy
	 * @date 2012-11-15 上午4:44:03
	 */
	public static void setValue(IBaseVO vo,String columnName, Object value) {
		columnName = columnName.toLowerCase();
		Class<? extends IBaseVO> c = vo.getClass();
		// 有这个字段，则获取这个字段的值
		String methodName = "set" + StringUtils.firstLetterToUpper(columnName);
		try {
			// 按照函数签名的参数获取函数
			Method method = VOMethodCache.getInstance().getMethod(c, methodName);
			method.invoke(vo, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
