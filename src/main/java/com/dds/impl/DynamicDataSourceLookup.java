package com.dds.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

public class DynamicDataSourceLookup implements DataSourceLookup {
	private IDataSourceFactory dataSourceFactory = null;
	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceLookupFailureException {
		IDataSourceFactory dsf = getDataSourceFactory();
		try {
			DataSource ds = dsf.createDataSource(dataSourceName);
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public IDataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}
	public void setDataSourceFactory(IDataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

}
