package com.jhello.db;

import com.jhello.core.vo.IBaseVO;
import com.jhello.db.dialect.DialectFactory;
import com.jhello.db.dialect.IDialect;


public class SqlHelper {

	private final static IDialect dialect = DialectFactory.getDialect();
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
	public static String getInsertSql(String tableName, String[] fields) {
		return dialect.getInsertSql(tableName, fields);
	}

	/**
	 * 获得更新语句的SQL，改语句只根据PK进行数据更新
	 * 
	 * @param vo
	 * @return
	 * @author huangy
	 * @date 2012-11-15 上午6:58:50
	 */
	public static String getUpdateSql(String tableName,String[] fields,String condition) {
		return dialect.getUpdateSql(tableName, fields, condition);
	}

	/**
	 * 获得查询语句
	 * 
	 * @return
	 * @author huangy
	 * @date 2012-11-16 上午3:10:44
	 */
	public static String getSelectSql(String tableName, String[] fields) {
		return dialect.getSelectSql(tableName, fields);
	}

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
	public static String getSelectSql(String tableName, String[] fields, String condition) {
		return dialect.getSelectSql(tableName, fields, condition);
	}

	/**
	 * 获得删除语句的SQL
	 * 
	 * @param cls
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:19:00
	 */
	public static String getDeleteSql(Class<? extends IBaseVO> cls) {
		IBaseVO vo = null;
		StringBuilder sql = new StringBuilder();
		try {
			vo = cls.newInstance();
			sql.append(" where ").append(vo.getPrimaryKey()).append("=?");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return getDeleteSql(cls, sql.toString());
	}

	/**
	 * 获取根据条件删除的SQL语句
	 * 
	 * @param cls
	 * @param wherePart
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午1:54:33
	 */
	public static String getDeleteSql(Class<? extends IBaseVO> cls, String wherePart) {
		String tableName = "";
		try {
			IBaseVO vo = cls.newInstance();
			tableName = vo.getTableName();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return dialect.getDeleteSql(tableName, wherePart);
	}
	
	public static String getCompletelySql(String sql, SqlParameter par){
		return dialect.getCompletelySql(sql, par);
	}

}
