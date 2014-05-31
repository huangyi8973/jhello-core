package com.hy.core.db.datasource;

import javax.sql.DataSource;

import com.hy.core.config.ConfigConst;
import com.hy.core.config.JHelloConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSourceProvider implements IDataSourceProvider {

	public DataSource getDataSource() throws Exception{
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass(JHelloConfig.getConfigValue(ConfigConst.DB_DRIVER));
		cpds.setJdbcUrl(JHelloConfig.getConfigValue(ConfigConst.DB_URL));
		cpds.setUser(JHelloConfig.getConfigValue(ConfigConst.DB_USERNAME));
		cpds.setPassword(JHelloConfig.getConfigValue(ConfigConst.DB_PASSWORD));
		
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		
		return cpds;
	}
}
