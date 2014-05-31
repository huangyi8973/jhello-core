package com.jhello.core.db.dialect;

import com.jhello.core.db.SqlParameter;


public abstract class AbstractDialect implements IDialect {

	/**
	 * 根据参数的类型获得参数值的SQL写法
	 * 
	 * @param object
	 * @return
	 * @author huangy
	 * @date 2012-11-18 下午2:40:52
	 */
	protected String getParameterValue(Object object) {
		String strResult = null;
		if (String.class.getName().equals(object.getClass().getName())) {
			strResult = "'" + (String) object + "'";
		} else if (Integer.class.getName().equals(object.getClass().getName())) {
			strResult = String.valueOf(object);
		} else if (Double.class.getName().equals(object.getClass().getName())) {
			strResult = String.valueOf(object);
		} else if (Float.class.getName().equals(object.getClass().getName())) {
			strResult = String.valueOf(object);
		}
		return strResult;
	}
	
	/**
	 * 获得完整的SQL语句（包含参数值)
	 * @param sql
	 * @param par
	 * @return
	 */
	public String getCompletelySql(String sql, SqlParameter par) {
		if (par == null || par.getParameters().size() == 0) {
			return sql;
		}
		String[] token = sql.split("\\?");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < par.getParameters().size(); i++) {
			sb.append(token[i]);
			if(par.get(i)!=null){
				sb.append(getParameterValue(par.get(i)));
			}else{
				sb.append("null");
			}
		}
		if (token.length > par.getParameters().size()) {
			// 当分解的字符串比参数值多1的时候，在这里加上最后一个字符串
			sb.append(token[token.length - 1]);
		}
		return sb.toString();
	}

}
