package com.dds.impl;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public abstract class AbstractDataSourceFactory implements IDataSourceFactory {
	private Properties dbProps = null;


	public void setDbProps(Properties dbProps) {
		this.dbProps = dbProps;
	}

	@Override
	public DataSource createDataSource(String dsID) throws Exception {
		Properties theProps = new Properties(dbProps);
		DataSourceInfo dsInfo = findDataSourceInfo(dsID);
		if(dsInfo!= null){
			theProps.put("driverClassName", dsInfo.getDriverClsName());
			theProps.put("url", dsInfo.getUrl());
			theProps.put("username", dsInfo.getUserName());
			theProps.put("password", dsInfo.getPassword());
		}
		return createDataSourceByProps(theProps);
	}
	protected DataSource createDataSourceByProps(Properties props) throws Exception{
		return BasicDataSourceFactory.createDataSource(props);
	}
	protected abstract DataSourceInfo findDataSourceInfo(String dsID) throws Exception;


	
}
