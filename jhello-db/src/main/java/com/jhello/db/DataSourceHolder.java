package com.jhello.db;

import javax.sql.DataSource;

public class DataSourceHolder {

	public static DataSource dataSource;

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		DataSourceHolder.dataSource = dataSource;
	}
	
}
