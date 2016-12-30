package com.dds.impl;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource implements BeanFactoryAware {
	private BeanFactory beanFactory = null;
	private static ThreadLocal<String> tlDSID = new ThreadLocal<String>();
	
	public DynamicDataSource() {
		super();
		setTargetDataSources(new HashMap<Object, Object>()); 
	}
	
	@Override
	protected DataSource determineTargetDataSource() {
		String dsID = (String)determineCurrentLookupKey();
		String dsBeanName = constructDSBeanName(dsID);
		DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory)beanFactory;
		DataSource ds = null;
		if(!dlbf.containsBean(dsBeanName)){
			synchronized (beanFactory) {
				if(!dlbf.containsBean(dsBeanName)){
					ds = resolveSpecifiedDataSource(dsID);
					dlbf.registerSingleton(dsBeanName, ds);
				}
			}
		}
		ds = dlbf.getBean(dsBeanName, DataSource.class);
		return ds;
	}
	protected String constructDSBeanName(String dsID){
		return "_datasource_"+dsID;
	}
	public static void setDSID(String dsID){
		tlDSID.set(dsID);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return tlDSID.get();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
