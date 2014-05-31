package com.jhello.core.utils;

/**
 * String工具类
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public class StringUtils {

	/**
	 * 字符串首字母大写
	 * @param str
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午3:56:43
	 */
	public static String firstLetterToUpper(String str){
		char[] buffer=str.toCharArray();
		if(buffer.length==0){
			return str;
		}
		buffer[0]=Character.toUpperCase(buffer[0]);
		return String.valueOf(buffer);
	}
	
	/**
	 * 字符串数组按分割符组成一个字符串<br/>
	 * eg.["aaa","bbb"],","->"aaa,bbb"<br/>
	 * ["aaa","bbb"],"-"->"aaa-bbb"
	 * @param ary 字符串数组
	 * @param seperation 分割符
	 * @return 合成的字符串
	 * @author huangy
	 * @date 2012-11-15 上午6:10:50
	 */
	public static String arrayToString(String[] ary,String seperation){
		return arrayToString(ary, seperation, null, null);
	}
	/**
	 * 字符串数组按分割符组成一个字符串<br/>
	 * arrayToString(["aaa","bbb"],",","#","$")<br/>
	 * 结果为：#aaa$,#bbb$
	 * @param ary 字符串数组
	 * @param seperation 分隔符号
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午6:30:43
	 */
	public static String arrayToString(String[] ary,String seperation,String prefix ,String suffix){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<ary.length;i++){
			if(prefix!=null){
				sb.append(prefix);
			}
			sb.append(ary[i]);
			if(suffix!=null){
				sb.append(suffix);
			}
			if(i!=ary.length-1){
				//只有最后一个字符不用加分割符号，其他都加上
				sb.append(seperation);
			}
		}
		return sb.toString();
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:23:11
	 */
	public static boolean isEmpty(String str){
		if(str==null||str.trim()==""){
			return true;
		}
		return false;
	}
}
