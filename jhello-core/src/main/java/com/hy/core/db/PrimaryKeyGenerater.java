package com.hy.core.db;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 主键生成器，使用UUID生成32位主键
 * @author huangy
 * @date   2012-11-18
 */
public class PrimaryKeyGenerater {

	private PrimaryKeyGenerater(){};
	/**
	 * 使用UUID生成32位主键，去掉UUID里面的“-“
	 * @return
	 */
	public static String generaterKey(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	public static void main(String[] args){
		Set<String> set=new HashSet<String>();
		for(int i=0;i<100;i++){
			set.add(PrimaryKeyGenerater.generaterKey());
		}
		System.out.println(set);
	}
}

