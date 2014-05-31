package com.jhello.core.vo;

/**
 * VO接口
 * @version V5.0
 * @author huangy
 * @date   2012-11-15
 */
public interface IBaseVO {
	/**
	 * 获得表名称
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:56:14
	 */
	String getTableName();
	
	/**
	 * 获得主键字段
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午5:56:55
	 */
	String getPrimaryKey();
	/**
	 * 获得主键值
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:02:11
	 */
	String getPrimaryKeyValue();
	
	/**
	 * 主键生成策略
	 * @return
	 */
	PrimaryKeyStrategy getPrimaryKeyStrategy();
}
