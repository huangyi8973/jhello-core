package com.jhello.core.db.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接代理
 * @author huangy
 * @date   2013-5-25
 */
class ConnectionHandle implements InvocationHandler {

	private Connection _conn=null;
	public Connection bind(Connection conn){
		_conn=conn;
		return (Connection)Proxy.newProxyInstance(conn.getClass().getClassLoader(), new Class[]{Connection.class}, this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object o=null;
		//处理close，不使用原本的关闭，使用连接池
		if("close".equals(method.getName())){
			HelloDataSource.getInstance().free(_conn);
		}else{
			o=method.invoke(_conn, args);
		}
		return o;
	}
	
	/**
	 * 原本的关闭连接方法
	 * @author huangy
	 * @throws SQLException 
	 * @date 2013-5-25 下午7:35:44
	 */
	public void realClose() throws SQLException{
		_conn.close();
	}
}
