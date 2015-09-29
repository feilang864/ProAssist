package com.qtxu.excel.dao;

import java.util.LinkedList;

public class SheetConfig {
	private String name;
	private String dbTable;
	private String writeFile;
	private LinkedList<ColumnConfig> columns = new LinkedList<ColumnConfig>();
	
	public LinkedList<ColumnConfig> getColumns() {
		return columns;
	}

	public void setColumns(LinkedList<ColumnConfig> columns) {
		this.columns = columns;
	}

	public SheetConfig(){
		
	}
	
	public SheetConfig(String name, String dbTable){
		this.name = name;
		this.dbTable = dbTable;
	}

	public String getName() {
		return name;
	}

	public void setWriteFile(String writeFile) {
		this.writeFile = writeFile;
	}

	public String getWriteFile() {
		return writeFile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbTable() {
		return dbTable;
	}

	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}
	
	public int getColumnCount(){
		return columns.size();
	}
	
//	public void addColumnConfig(String name, String dbColumn, String type, String isExtend, String defaultValue){
//		ColumnConfig cc = new ColumnConfig(name, dbColumn, type, isExtend, defaultValue);
//		if(null == isExtend)	cc.setExt("false");
//		cc.setDefaultValue(defaultValue);
//		columns.add(cc);
//		
//	}
	
	public void addColumnConfig(ColumnConfig column){
		columns.add(column);
	}
	
	public String getColumnName(String dbColumn){
		for(ColumnConfig cc : columns){
			if(cc.getDbColumn().equals(dbColumn))
				return cc.getName();
		}
		return null;
	}
	
	public ColumnConfig getColumnByName(String name){
		for(ColumnConfig cc : columns){
			if(cc.getName().equals(name))
				return cc;
		}
		return null;
	}
	/**
	 * 判断配置文件中标签<column>的属性name是否存在在excel文件sheet页第一行中
	 * @param name
	 * @return
	 */
	public boolean isContainsColumnConfig(String name){
		for(ColumnConfig cc : columns){
			if(cc.getName().equals(name))
				return true;
		}
		return false;
	}

	public ColumnConfig getColumnByDBColumn(String dbColumn) {
		for(ColumnConfig cc : columns){
			if(cc.getDbColumn().equals(dbColumn))
				return cc;
		}
		return null;
	}
}
