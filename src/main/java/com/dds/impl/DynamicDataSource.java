package com.dds.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private Map<String, DataSource>dsMap = new HashMap<String, DataSource>();
	private static ThreadLocal<String> tlDSID = new ThreadLocal<String>();
	
	public DynamicDataSource() {
		super();
		setTargetDataSources(new HashMap<Object, Object>()); 
	}
	
	@Override
	protected DataSource determineTargetDataSource() {
		String dsID = (String)determineCurrentLookupKey();
		DataSource ds = dsMap.get(dsID);
		if(ds == null){
			ds = resolveSpecifiedDataSource(dsID);
			dsMap.put(dsID, ds);
		}
		return ds;
	}
	
	public static void setDSID(String dsID){
		tlDSID.set(dsID);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return tlDSID.get();
	}

}
