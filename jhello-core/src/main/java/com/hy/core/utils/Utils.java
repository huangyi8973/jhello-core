package com.hy.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Utils {

	public static <T> boolean isEmplyOrNull(T[] ary){
		if(ary == null || ary.length == 0){
			return true;
		}
		return false;
	}
	
	public static <T> boolean isEmplyOrNull(Collection<T> collection){
		if(collection == null || collection.size()  <= 0){
			return true;
		}
		return false;
	}
	
	public static void fill(String[] ary,String value){
		if(!isEmplyOrNull(ary)){
			for(int i=0;i<ary.length;i++){
				ary[i]=value;
			}
		}
	}
	
	/**返回数组内容字符串格式：[value1,value2,value3]
	 * @param ary
	 * @return
	 * @author huangy
	 * @date 2013-4-12 下午5:19:47
	 */
	public static String toString(Object[] ary){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(Object o : ary){
			sb.append(o==null ? "null" : String.valueOf(o));
			sb.append(",");
		}
		sb.setLength(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 返回数组交集
	 * @param ary1
	 * @param ary2
	 * @return
	 */
	public static String[] intersection(String[] ary1,String[] ary2){
		List<String> list1=new ArrayList<String>(Arrays.asList(ary1));
		List<String> list2=new ArrayList<String>(Arrays.asList(ary2));
		list1.retainAll(list2);
		return list1.toArray(new String[0]);
	}
}
