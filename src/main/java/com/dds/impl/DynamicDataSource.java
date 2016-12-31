package com.dds.impl;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource implements BeanFactoryAware {
	private BeanFactory beanFactory = null;
	private static ThreadLocal<String> tlDSID = new ThreadLocal<String>();
	private DynamicDataSourceLookup dataSourceLookup = null;

	public DynamicDataSource() {
		super();
		setTargetDataSources(new HashMap<Object, Object>());
	}

	@Override
	protected DataSource determineTargetDataSource() {
		String dsID = (String) determineCurrentLookupKey();
		if (this.dataSourceLookup != null) {
			return this.dataSourceLookup.getDataSource(dsID);
		} else {
			throw new RuntimeException("data source lookup is null.");
		}
	}

	public static void setDSID(String dsID) {
		tlDSID.set(dsID);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return tlDSID.get();
	}

	public void setDataSourceLookup(DynamicDataSourceLookup dataSourceLookup) {
		this.dataSourceLookup = dataSourceLookup;
		if (this.dataSourceLookup != null){
			this.dataSourceLookup.setBeanFactory(this.beanFactory);
		}
	}

	public DynamicDataSourceLookup getDataSourceLookup() {
		return dataSourceLookup;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		if (this.dataSourceLookup != null) {
			this.dataSourceLookup.setBeanFactory(beanFactory);
		}
	}

}
