package com.dds.impl;

import java.util.Enumeration;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

public class DynamicDataSourceLookup implements DataSourceLookup {
	private IDataSourceInfoFactory dataSourceInfoFactory = null;
	private BeanFactory beanFactory = null;
	private Properties dsProps = null;
	private String dataSourceClassName = BasicDataSource.class.getName();
	@Override
	public DataSource getDataSource(String dsID) throws DataSourceLookupFailureException {
		String dsBeanName = constructDSBeanName(dsID);
		DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) getBeanFactory();
		DataSource ds = null;
		if (!dlbf.containsBean(dsBeanName)) {
			synchronized (dsBeanName) {
				if (!dlbf.containsBean(dsBeanName)) {
					DataSourceInfo dsInfo = getDataSourceInfoFactory().createDataSourceInfo(dsID);
					BeanDefinition beanDef = createDSBeanDefinition(dsInfo, dsProps);
					dlbf.registerBeanDefinition(dsBeanName, beanDef);
				}
			}
		}
		ds = dlbf.getBean(dsBeanName, DataSource.class);
		return ds;
	}

	protected String constructDSBeanName(String dsID) {
		return "_datasource_" + dsID;
	}

	public String getDataSourceClassName() {
		return dataSourceClassName;
	}

	public void setDataSourceClassName(String dataSourceClassName) {
		this.dataSourceClassName = dataSourceClassName;
	}

	protected BeanDefinition createDSBeanDefinition(DataSourceInfo dsInfo, Properties props) {
		GenericBeanDefinition gbd = new GenericBeanDefinition();
		gbd.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);
		gbd.setBeanClassName(getDataSourceClassName());
		MutablePropertyValues propValues = gbd.getPropertyValues();
		if (props != null) {
			Enumeration<Object> propKeys = props.keys();
			while (propKeys.hasMoreElements()) {
				Object key = propKeys.nextElement();
				Object value = props.get(key);
				propValues.add(key.toString(), value);
			}
		}
		propValues.add("driverClassName", dsInfo.getDriverClsName());
		propValues.add("url", dsInfo.getUrl());
		propValues.add("username", dsInfo.getUserName());
		propValues.add("password", dsInfo.getPassword());
		return gbd;
	}

	public IDataSourceInfoFactory getDataSourceInfoFactory() {
		return dataSourceInfoFactory;
	}

	public void setDataSourceInfoFactory(IDataSourceInfoFactory dataSourceInfoFactory) {
		this.dataSourceInfoFactory = dataSourceInfoFactory;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Properties getDsProps() {
		return dsProps;
	}

	public void setDsProps(Properties dsProps) {
		this.dsProps = dsProps;
	}

}
