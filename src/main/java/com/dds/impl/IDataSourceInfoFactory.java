package com.dds.impl;


public interface IDataSourceInfoFactory {
	public DataSourceInfo createDataSourceInfo(String dsID) throws RuntimeException;

}
