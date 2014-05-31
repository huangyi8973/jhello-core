package com.jhello.core.db.datasource;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhello.core.config.ConfigConst;
import com.jhello.core.config.JHelloConfig;


/**
 * 连接池管理(数据源)
 * 
 * @version V5.0
 * @author huangy
 * @date 2012-11-18
 */
public class HelloDataSource implements javax.sql.DataSource {

	private Logger logger = LoggerFactory.getLogger(HelloDataSource.class);

	// 连接池实例
	private static HelloDataSource _instance = null;

	// 最大连接数
	private int MAX_CONNECTION = 10;

	// 初始连接数
	private int INIT_CONNECTION = 2;

	// 数据库连接容器
	private List<Connection> _connPool;

	private List<Integer> _connStatus;

	// 连接标志
	private static int USED = 0; //使用中

	private static int FREE = 1; //空闲

	static{
		try {
			Class.forName(JHelloConfig.getConfigValue(ConfigConst.DB_DRIVER));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private HelloDataSource() {
		try {
			initConfig();
			initPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initConfig() throws ClassNotFoundException {
		//从dbconfig中获取数据
		//获取连接池初始化最小连接数
		String initConnection=JHelloConfig.getConfigValue(ConfigConst.DB_INIT_CONNECTION);
		INIT_CONNECTION=initConnection==null ? INIT_CONNECTION : Integer.parseInt(initConnection);
		logger.debug("数据库初始连接限制 : "+initConnection);
		//获取连接池最大连接数
		String maxConnection=JHelloConfig.getConfigValue(ConfigConst.DB_MAX_CONNECTION);
		MAX_CONNECTION=maxConnection==null ? MAX_CONNECTION : Integer.parseInt(maxConnection);
		logger.debug("数据库最大连接限制 : "+maxConnection);
	}

	/**
	 * 初始化连接池
	 * 
	 * @author huangy
	 * @date 2012-11-18 上午6:11:41
	 */
	private void initPool() {
		_connPool = new ArrayList<Connection>();
		_connStatus = new ArrayList<Integer>();
		logger.info("初始化连接池");
		long start = System.currentTimeMillis();
		for (int i = 0; i < INIT_CONNECTION; i++) {
			try {
				Connection conn=createConnection();
				_connPool.add(conn);
				_connStatus.add(FREE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		logger.info(String.format("共耗时：%s ms", end - start));

	}

	public static HelloDataSource getInstance() {
		if (_instance == null) {
			synchronized (HelloDataSource.class) {
				// 双重检查
				if (_instance == null) {
					_instance = new HelloDataSource();
				}
			}
		}
		return _instance;
	}

	/**
	 * 获得连接
	 * 
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2012-11-18 上午7:02:20
	 */
	public synchronized Connection getConnection() throws SQLException {
		long start = System.currentTimeMillis();
		Connection conn = null;
		int index = _connStatus.indexOf(FREE);
		if (index != -1) {
			_connStatus.set(index, USED);
			conn = _connPool.get(index);
		}
		if (conn == null) {
			if (_connPool.size() < MAX_CONNECTION) {
				conn = createConnection();
				_connPool.add(conn);
				_connStatus.add(USED);
				logger.debug("连接不够了，建立新连接，当前连接数：" + _connPool.size());

			}
		}
		long end = System.currentTimeMillis();
		logger.info(String.format("获取连接,共耗时：%s", end - start));
		return conn;
	}

	/**
	 * 创建数据库连接（被包装过的连接对象）
	 * @return
	 * @throws SQLException
	 * @author huangy
	 * @date 2013-5-25 下午8:26:22
	 */
	private Connection createConnection() throws SQLException {
		String dbUrl=JHelloConfig.getConfigValue(ConfigConst.DB_URL);
		String dbUserName=JHelloConfig.getConfigValue(ConfigConst.DB_USERNAME);
		String dbPassword=JHelloConfig.getConfigValue(ConfigConst.DB_PASSWORD);
		Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		//创建connection代理，如果用户直接close，可以使用free方法释放，防止用户自己关闭连接
		ConnectionHandle connProxy=new ConnectionHandle();
		conn=connProxy.bind(conn);
		return conn;
	}

	/**
	 * 释放连接
	 * 
	 * @param conn
	 * @author huangy
	 * @date 2012-11-18 上午6:43:09
	 */
	public synchronized void free(Connection conn) {
		long lTime=System.currentTimeMillis();
		logger.info("释放数据库连接开始");
		int index = getIndexFromPool(conn);
		if(index>=0){
			_connStatus.set(index, FREE);
			logger.info("释放数据库连接");
		}
		logger.info(String.format("释放连接结束,耗时：%dms",System.currentTimeMillis()-lTime));
		
		int freeCount = 0;
		for(int i=0;i<_connStatus.size();i++){
			if(_connStatus.get(i) == FREE){
				freeCount ++;
			}
		}
		logger.info(String.format("当前连接池 : [total : %d] [free : %d]",_connStatus.size(),freeCount));
		
	}

	/**
	 * 查找Connection的位置
	 * @param conn
	 * @return
	 * @author huangy
	 * @date 2013-5-25 下午8:07:37
	 */
	private int getIndexFromPool(Connection conn) {
		for(int i=0;i<_connPool.size();i++){
			if(_connPool.get(i).equals(conn) || _connPool.get(i)==conn){
				return i;
			}
		}
		return -1;
	}

	/**
	 * 关闭并移除所有连接
	 * @author huangy
	 * @date 2012-11-18 上午10:34:50
	 */
	public synchronized void closeAll() {
		try {
			for (Connection conn : _connPool) {
				//调用代理的关闭
				ConnectionHandle connProxy=(ConnectionHandle) conn;
				connProxy.realClose();
			}
			_connPool.clear();
			_connStatus.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭空闲的连接，直到当前在使用的连接数等于InitConnection
	 * @author huangy
	 * @date 2012-11-24 下午2:56:16
	 */
	public synchronized void doClean(){
		List<Connection> removeConn=new ArrayList<Connection>();
		List<Integer> removeIndex=new ArrayList<Integer>();
		for(int i=0;i<_connPool.size();i++){
			Connection conn=_connPool.get(i);
			if(_connStatus.get(i)==FREE&&(_connPool.size()-removeConn.size())>INIT_CONNECTION){
				removeConn.add(conn);
				removeIndex.add(i);
			}
		}
		logger.debug(String.format("找到%d个空闲连接",removeConn.size()));
		logger.info("开始清理空闲数据库连接");
		long start=System.currentTimeMillis();
		_connPool.removeAll(removeConn);//释放空闲连接
		for(int i=removeIndex.size()-1;i>=0;i--){
			_connStatus.remove(removeIndex.get(i));
		}
		long end=System.currentTimeMillis();
		logger.info(String.format("清理完毕，共耗时：%dms", removeConn.size(),end-start));
		logger.info(String.format("当前剩余 %d 个连接", _connPool.size()));
	}
	
	public Connection getConnection(String username, String password)
			throws SQLException {
		String dbUrl=JHelloConfig.getConfigValue(ConfigConst.DB_URL);
		Connection conn = DriverManager.getConnection(dbUrl, username, password);
		//创建connection代理，如果用户直接close，可以使用free方法释放，防止用户自己关闭连接
		ConnectionHandle connProxy=new ConnectionHandle();
		conn=connProxy.bind(conn);
		return conn;
	}
	
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public java.util.logging.Logger getParentLogger()
			throws SQLFeatureNotSupportedException {
		return null;
	}


	
}
