package com.jhello.db.datasource;

import javax.sql.DataSource;

import com.jhello.core.config.ConfigConst;
import com.jhello.core.config.JHelloConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSourceProvider implements IDataSourceProvider {

	public DataSource getDataSource() throws Exception{
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass(JHelloConfig.getInstance().getConfigValue(ConfigConst.DB_DRIVER));
		cpds.setJdbcUrl(JHelloConfig.getInstance().getConfigValue(ConfigConst.DB_URL));
		cpds.setUser(JHelloConfig.getInstance().getConfigValue(ConfigConst.DB_USERNAME));
		cpds.setPassword(JHelloConfig.getInstance().getConfigValue(ConfigConst.DB_PASSWORD));
		
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		
		return cpds;
	}
}
