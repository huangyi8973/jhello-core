package com.jhello.core.utils;

/**
 * 数学工具
 * @version V5.0
 * @author huangy
 * @date   2012-11-19
 */
public class MathUtils {

	/**
	 * 计算整数合计
	 * @param num
	 * @return
	 * @author huangy
	 * @date 2012-11-19 上午4:35:50
	 */
	public static int sum(int[] num){
		int result=0;
		for(int n:num){
			result+=n;
		}
		return result;
	}
}
