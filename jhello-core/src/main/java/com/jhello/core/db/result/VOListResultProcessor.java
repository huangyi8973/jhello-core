package com.jhello.core.db.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jhello.core.vo.IBaseVO;
import com.jhello.core.vo.VOUtils;

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
			while(rs.next()){
				IBaseVO vo=this.voClass.newInstance();
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
					String columnName=rs.getMetaData().getColumnName(i);
					VOUtils.setValue(vo,columnName,rs.getObject(i));
				}
				list.add(vo);
			}
		}
		return list;
	}

}

