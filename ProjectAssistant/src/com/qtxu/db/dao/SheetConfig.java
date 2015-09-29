package com.qtxu.db.dao;

public class SheetConfig {
	private String name;
	private String sql;
	private String dataSourceName;
	
	public SheetConfig() {
	}

	public SheetConfig(String name, String sql, String dataSourceName) {
		this.name = name;
		this.sql = sql;
		this.dataSourceName = dataSourceName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

}
