package com.jhello.db.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回ArrayList<HashMap<字段，值>>
 * @author huangy
 * @date   2013-4-12
 */
public class ListMapResultProcessor implements IResultProcessor {

	/**
	 * @see cn.hy.db.result.IResultProcessor#getResult(java.sql.ResultSet)
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2013-4-12 上午11:05:34
	 */
	public List<Map<String,Object>> getResult(ResultSet rs) throws SQLException{
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		if(rs!=null){
			while(rs.next()){
				Map<String,Object> map=new HashMap<String, Object>();
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
					//获取的是字段别名，如果是select name as username from user这样的，返回的结果是是获取username
					String columnName=rs.getMetaData().getColumnLabel(i);
					map.put(columnName, rs.getObject(i));
				}
				result.add(map);
			}
		}
		return result;
	}
}
