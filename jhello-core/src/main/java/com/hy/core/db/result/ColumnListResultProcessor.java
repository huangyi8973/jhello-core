package com.hy.core.db.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hy.core.utils.StringUtils;

/**
 * 返回某一列的结果List集合
 * @author huangy
 * @date   2013-4-12
 */
public class ColumnListResultProcessor<T> implements IResultProcessor {

	private String m_field=null;
	public ColumnListResultProcessor(String field){
		this.m_field=field;
	}
	
	public List<T> getResult(ResultSet rs) throws SQLException {
		List<T> list=new ArrayList<T>();
		if(rs!=null && !StringUtils.isEmpty(m_field)){
			while(rs.next()){
				Object obj=rs.getObject(m_field);
				// FIXME 判断类型，避免类型转换出错
				if(obj!=null){
					list.add((T)obj);
				}else{
					list.add(null);
				}
			}
		}
		return list;
	}

}
