package com.dds.impl;

import javax.sql.DataSource;

public interface IDataSourceFactory {
	public DataSource createDataSource(String dsID) throws Exception;

}
