package com.qtxu.excel.dao;

public class ColumnConfig {
	private String name;
	private String dbColumn;
	private String type;
	private String ext;
	private String isExt;
	private String defaultValue;
	private String valueMap;
	private String classValue;
	
	public ColumnConfig(){
		
	}
	
//	public ColumnConfig(String name, String dbColumn, String type, String ext, String defaultValue) {
//		// TODO Auto-generated constructor stub
//		this.name = name;
//		this.dbColumn = dbColumn;
//		this.type = type;
//		this.ext = ext;
//		this.defaultValue = defaultValue;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDbColumn() {
		return dbColumn;
	}
	public void setDbColumn(String dbColumn) {
		this.dbColumn = dbColumn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isExt(){
		return "true".equalsIgnoreCase(isExt)||"1".equals(isExt);
	}
	@Override
	public String toString(){
		return getName() + "\t" + getDbColumn() + "\t" + getType() + "\t" + getDefaultValue() + "\t" + getExt() + "\t" + getIsExt();
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getIsExt() {
		return isExt;
	}

	public void setIsExt(String isExt) {
		this.isExt = isExt;
	}

	public String getValueMap() {
		return valueMap;
	}

	public void setValueMap(String valueMap) {
		this.valueMap = valueMap;
	}

	public String getClassValue() {
		return classValue;
	}

	public void setClassValue(String classValue) {
		this.classValue = classValue;
	}
}
