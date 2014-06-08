package com.jhello.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.utils.MathUtils;
import com.jhello.db.result.IResultProcessor;

/**
 * 基本数据访问对象
 * 
 * @author huangy
 * @date 2013-4-6
 */
public class BaseDao {
	protected Logger log = LoggerFactory.getLogger(BaseDao.class);
	private int m_maxRow=10000;//返回行数最大值
	public int getMaxRow() {
		return m_maxRow;
	}
	

	public void setMaxRow(int m_maxRow) {
		this.m_maxRow = m_maxRow;
	}

	
	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2013-4-6 下午10:27:47
	 */
	public int[] execUpdate(final String sql,final SqlParameter... parameters) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		long lSpandTimeStart = 0l;
		long lSpandTimeEnd = 0l;
		int[] rows;
		ps.clearParameters();
		ps.clearBatch();
		if (parameters != null && parameters.length > 0) {
			// 多个参数时，批量执行
			for (final SqlParameter par : parameters) {
				for (int i = 0; i < par.size(); i++) {
					ps.setObject(i + 1, par.get(i));
				}
				ps.addBatch();
				log.debug(String.format("执行SQL : %s", SqlHelper.getCompletelySql(sql, par)));
			}
			lSpandTimeStart = System.currentTimeMillis();
			rows = ps.executeBatch();
			lSpandTimeEnd = System.currentTimeMillis();
		} else {
			// 没有参数，只有SQL
			lSpandTimeStart = System.currentTimeMillis();
			rows = new int[] { ps.executeUpdate() };
			lSpandTimeEnd = System.currentTimeMillis();
			log.debug(String.format("执行SQL : %s", sql));
		}
		close(conn, ps, null);
		log.info(String.format("执行时间 : %dms", lSpandTimeEnd - lSpandTimeStart));
		log.info(String.format("共影响%d条记录", MathUtils.sum(rows)));
		return rows;
	}
	
	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2013-4-6 下午10:27:47
	 */
	public int execInsertByAutoInCrementKey(final String sql,final SqlParameter parameters) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		long lSpandTimeStart = 0l;
		long lSpandTimeEnd = 0l;
		lSpandTimeStart = System.currentTimeMillis();
		for (int i = 0; i < parameters.size(); i++) {
			ps.setObject(i + 1, parameters.get(i));
		}
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		int primaryKey = rs.getInt(1);
		lSpandTimeEnd = System.currentTimeMillis();
		log.debug(String.format("执行SQL : %s", sql));
		close(conn, ps, null);
		log.info(String.format("执行时间 : %dms", lSpandTimeEnd - lSpandTimeStart));
		return primaryKey;
	}
	
	/**
	 * 执行有查询结果的SQL
	 * @param sql
	 * @param param
	 * @return
	 * @author huangy
	 * @throws Exception 
	 * @date 2013-4-12 上午10:47:33
	 */
	public Object execute(final String sql,final SqlParameter param,IResultProcessor processor) throws Exception{
		ResultSet rs=null;
		long lSpandTimeStart = 0l;
		long lSpandTimeEnd = 0l;
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.clearBatch();
		ps.clearParameters();
		ps.setMaxRows(m_maxRow);
		if(param!=null){
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
		}
		log.debug(String.format("执行SQL : %s", SqlHelper.getCompletelySql(sql, param)));
		lSpandTimeStart=System.currentTimeMillis();
		rs=ps.executeQuery();
		lSpandTimeEnd=System.currentTimeMillis();
		log.info(String.format("执行时间 : %dms", lSpandTimeEnd - lSpandTimeStart));
		//TODO 有木有现成的方法?要是还要计算。。那就算了
//		log.info(String.format("共查询%d条记录",rs.getrow()));
		//执行结果集转换
		log.debug("进行数据格式转换");
		lSpandTimeStart=System.currentTimeMillis();
		Object value=processor.getResult(rs);
		lSpandTimeEnd=System.currentTimeMillis();
		log.info(String.format("数据格式转换完毕，共花费%dms", lSpandTimeEnd - lSpandTimeStart));
		close(conn, ps, rs);
		return value;
	}

	protected Connection getConnection() throws SQLException {
		return DataSourceHolder.getDataSource().getConnection();
	}


	/**
	 * 关闭连接，记录集等
	 * 
	 * @param conn
	 * @param ps
	 * @param rs
	 * @author huangy
	 * @throws SQLException 
	 * @date 2013-4-6 下午10:42:15
	 */
	protected void close(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		try {
			// 关闭记录集
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			// 关闭PreparedStatement
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				throw e;
			} finally {
				// 关闭连接
				if (conn != null) {
					conn.close();
				}
			}
		}
	}
}
