package com.qtxu.db.dao;

import java.util.Properties;

public class DataSource {
	private String name;
	private String driver;
	private String URL;
	private String userName;
	private String password;
	private String internallogon;
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInternallogon() {
		return internallogon;
	}
	public void setInternallogon(String internallogon) {
		this.internallogon = internallogon;
	}
	
	public Properties getProperties(){
		Properties props = new Properties();
		if(this.getUserName() != null) props.setProperty("user", this.getUserName());
		if(this.getPassword() != null) props.setProperty("password", this.getPassword());
		if(this.getInternallogon() != null) props.setProperty("internal_logon", this.getInternallogon());
		return props;
	}
}
