package com.dds.test;

import java.io.Serializable;

public class Session implements Serializable {
	private static final long serialVersionUID = -3545628365998031391L;
	private String dsid = null;
	public String getDsid() {
		return dsid;
	}
	public void setDsid(String dsid) {
		this.dsid = dsid;
	}
	
}
