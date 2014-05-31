package com.hy.core.vo;

public enum PrimaryKeyStrategy {
	GENERATE,//使用内置的PrimaryKeyGenerater生成32位UUID
	AUTO_INCREMENT //使用数据库自动生成
}
