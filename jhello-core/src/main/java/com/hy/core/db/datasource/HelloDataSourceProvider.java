package com.hy.core.db.datasource;

import javax.sql.DataSource;

public class HelloDataSourceProvider implements IDataSourceProvider {

	public DataSource getDataSource() throws Exception {
		return HelloDataSource.getInstance();
	}

	
}
