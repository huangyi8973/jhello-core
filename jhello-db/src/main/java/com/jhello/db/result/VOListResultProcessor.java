package com.jhello.db.result;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhello.core.vo.IBaseVO;
import com.jhello.core.vo.VOUtils;
import com.jhello.db.convert.ConvertUtils;

/**
 * 返回VO的集合
 * @author huangy
 * @date   2013-4-12
 */
public class VOListResultProcessor implements IResultProcessor {

	private Class<? extends IBaseVO> voClass;
	public VOListResultProcessor(Class<? extends IBaseVO> cls){
		this.voClass=cls;
	}
	
	public List<IBaseVO> getResult(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException {
		List<IBaseVO> list=new ArrayList<IBaseVO>();
		if(rs!=null){
			//获得字段与类型映射关系
			Map<String,Class<?>> fieldAndClsMap = getFieldAndClsMap();
			while(rs.next()){
				IBaseVO vo=this.voClass.newInstance();
				
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
					String columnName=rs.getMetaData().getColumnName(i);
					VOUtils.setValue(vo,columnName,ConvertUtils.convertToVO(rs.getObject(i),fieldAndClsMap.get(columnName)));
				}
				list.add(vo);
			}
		}
		return list;
	}

	private Map<String, Class<?>> getFieldAndClsMap() {
		Map<String,Class<?>> fieldAndClsMap = new HashMap<String,Class<?>>();
		Field[]  fields = this.voClass.getDeclaredFields();
		for(Field f : fields){
			fieldAndClsMap.put(f.getName(), f.getType());
		}
		return fieldAndClsMap;
	}

}

