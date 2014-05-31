package com.jhello.core.db.dialect;

import com.jhello.core.utils.StringUtils;
import com.jhello.core.utils.Utils;


public class MySqlDialect extends AbstractDialect {

	/* (non-Javadoc)
	 * @see com.hy.core.db.dialect.IDialect#getInsertSql(java.lang.String, java.lang.String[])
	 */
	public String getInsertSql(String tableName, String[] fields) {
		StringBuilder sql = new StringBuilder();
		String[] values = new String[fields.length];
		Utils.fill(values, "?");
		sql.append("insert into ").append(tableName).append("(").append(StringUtils.arrayToString(fields, ","))
			.append(")").append(" values(").append(StringUtils.arrayToString(values, ",")).append(")");
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.hy.core.db.dialect.IDialect#getUpdateSql(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public String getUpdateSql(String tableName,String[] fields,String condition) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tableName).append(" set ")
			.append(StringUtils.arrayToString(fields, ",", null, "=?")).append(" where ").append(condition);
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.hy.core.db.dialect.IDialect#getSelectSql(java.lang.String, java.lang.String[])
	 */
	public String getSelectSql(String tableName, String[] fields) {
		return getSelectSql(tableName, fields, null);
	}

	/* (non-Javadoc)
	 * @see com.hy.core.db.dialect.IDialect#getSelectSql(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public String getSelectSql(String tableName, String[] fields, String condition) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(StringUtils.arrayToString(fields, ",")).append(" from ").append(tableName);
		// where条件不为空，加上where条件
		if (!StringUtils.isEmpty(condition)) {
			sql.append(" where ").append(condition);
		}
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see com.hy.core.db.dialect.IDialect#getDeleteSql(java.lang.Class, java.lang.String)
	 */
	public String getDeleteSql(String tableName, String wherePart) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(tableName).append(" where ").append(wherePart);
		return sql.toString();
	}

}
