package com.dds.impl;

import java.io.Serializable;

public class DataSourceInfo implements Serializable {
	private static final long serialVersionUID = -5237663236889315437L;
	private String dsID = null;
	private String driverClsName = null;
	private String url = null;
	private String userName = null;
	private String password = null;
	
	public String getDsID() {
		return dsID;
	}
	public void setDsID(String dsID) {
		this.dsID = dsID;
	}
	public String getDriverClsName() {
		return driverClsName;
	}
	public void setDriverClsName(String driverClsName) {
		this.driverClsName = driverClsName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
