package com.jhello.db;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.jhello.db.convert.ConvertUtils;
import com.jhello.db.result.ColumnListResultProcessor;
import com.jhello.db.result.VOListResultProcessor;
import com.jhello.vo.IBaseVO;
import com.jhello.vo.PrimaryKeyStrategy;
import com.jhello.vo.VOUtils;

/**
 * VO型数据访问对象
 * @author huangy
 * @date   2013-4-12
 */
public class VODao extends BaseDao {

	
	/**
	 * 根据条件查询VO的数据
	 * @param cls vo的class
	 * @param condition 条件
	 * @return
	 * @throws Exception
	 * @author huangy
	 * @date 2013-4-12 上午11:51:13
	 */
	@SuppressWarnings("unchecked")
	public IBaseVO[] queryByCondition(Class<? extends IBaseVO> cls,String condition) throws Exception{
		List<IBaseVO> resultList=null;
		Object rs=execute(SqlHelper.getSelectSql(VOUtils.getTableName(cls),getAvailableFields(cls), condition), null, new VOListResultProcessor(cls));
		if(rs!=null&&rs instanceof List){
			resultList=(List<IBaseVO>)rs ;
		}
		return resultList.toArray((IBaseVO[])Array.newInstance(cls, 0));
	}
	
	/**
	 * 查询VO的数据
	 * @param cls vo的class
	 * @return
	 * @throws Exception
	 * @author huangy
	 * @date 2013-4-12 上午11:51:03
	 */
	public IBaseVO[] query(Class<? extends IBaseVO> cls) throws Exception{
		return queryByCondition(cls, null);
	}
	
	/**插入数据
	 * @param vo
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @date 2013-4-12 下午5:50:48
	 */
	public Object insertVo(IBaseVO vo) throws SQLException, InstantiationException, IllegalAccessException{
		String[] availableFields=getAvailableFields(vo.getClass());
		Object primaryKey = null;
		//给VO赋予主键
		if(PrimaryKeyStrategy.GENERATE.equals(vo.getPrimaryKeyStrategy())){
			//使用内置的主键生成器
			primaryKey = PrimaryKeyGenerater.generaterKey();
			VOUtils.setValue(vo,vo.getPrimaryKey(), primaryKey);
			String sql=SqlHelper.getInsertSql(vo.getTableName(),availableFields);
			//创建参数
			SqlParameter par=new SqlParameter();
			for(String field : availableFields){
				par.addObject(ConvertUtils.convertToDb(VOUtils.getValue(vo,field)));
			}
			this.execUpdate(sql, par);
			return primaryKey;
		}else if(PrimaryKeyStrategy.AUTO_INCREMENT.equals(vo.getPrimaryKeyStrategy())){
			//使用数据库自动增长，不插入主键即可
			List<String> fields = new LinkedList<String>(Arrays.asList(availableFields));
			fields.remove(vo.getPrimaryKey());
			availableFields = fields.toArray(new String[0]);
			String sql=SqlHelper.getInsertSql(vo.getTableName(),availableFields);
			//创建参数
			SqlParameter par=new SqlParameter();
			for(String field : availableFields){
				par.addObject(ConvertUtils.convertToDb(VOUtils.getValue(vo,field)));
			}
			return this.execInsertByAutoInCrementKey(sql, par);
		}
		return 0;
	}

	/**
	 * 返回数据库和VO字段的交集，为可用字段
	 * @param dbFields
	 * @param voFields
	 * @return
	 * @author huangy
	 * @throws SQLException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @date 2013-4-12 下午5:25:26
	 */
	private String[] getAvailableFields(Class<? extends IBaseVO> cls) throws SQLException, InstantiationException, IllegalAccessException {
		List<String> list1=new ArrayList<String>(Arrays.asList(getDbFields(VOUtils.getTableName(cls))));
		List<String> list2=new ArrayList<String>(Arrays.asList(VOUtils.getAvailableFields(cls)));
		list1.retainAll(list2);
		return list1.toArray(new String[0]);
	}

	/**
	 * 获取数据库中表的字段
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2013-4-12 下午5:36:18
	 */
	private String[] getDbFields(String tableName) throws SQLException {
		List<String> fields=new ArrayList<String>();
		Connection conn=getConnection();
		//获得数据库信息
		DatabaseMetaData metaData=conn.getMetaData();
		//获得表相关的信息
		ResultSet rs=metaData.getColumns("", metaData.getSchemaTerm(), tableName, "");
		//获得列名的集合
		fields=(List<String>) (new ColumnListResultProcessor<String>("COLUMN_NAME")).getResult(rs);
		conn.close();
		return fields.toArray(new String[0]);
	}
}
