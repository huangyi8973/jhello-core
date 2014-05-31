package com.hy.core.db.dialect;

import com.hy.core.db.SqlParameter;

public interface IDialect {

	/**
	 * 获得插入的SQL语句
	 * 
	 * @param vo
	 * @return
	 * @author huangy
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @date 2012-11-15 上午5:51:42
	 */
	String getInsertSql(String tableName, String[] fields);

	/**
	 * 获得更新语句的SQL，改语句只根据PK进行数据更新
	 * 
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午6:58:50
	 */
	String getUpdateSql(String tableName, String[] fields, String condition);

	/**
	 * 获得查询语句
	 * 
	 * @return
	 * @author huangy
	 * @date 2012-11-16 上午3:10:44
	 */
	String getSelectSql(String tableName, String[] fields);

	/**
	 * 获得查询语句
	 * 
	 * @param cls VO类
	 * @param wherePart 条件，可以为空
	 * @param orderPart 排序条件，可以为空
	 * @param isAsc 是否为升序排序，如果排序条件为空，这个可以随便填
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:27:16
	 */
	String getSelectSql(String tableName, String[] fields, String condition);

	/**
	 * 获取根据条件删除的SQL语句
	 * 
	 * @param cls
	 * @param wherePart
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:54:33
	 */
	String getDeleteSql(String tableName, String wherePart);


	/**
	 * 获得完整的SQL语句，把?替换成真正的值
	 * @param sql
	 * @param par
	 * @return
	 */
	String getCompletelySql(String sql, SqlParameter par);
}