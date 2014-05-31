package com.hy.core.db.datasource;

import javax.sql.DataSource;

public interface IDataSourceProvider {

	DataSource getDataSource() throws Exception;

}