package com.qtxu.db.dao;

import java.util.LinkedList;

public class ExcelFileConfig {
	private String fileName;
	private String dataSourceName;
	private LinkedList<SheetConfig> sheets = new LinkedList<SheetConfig>();

	public LinkedList<SheetConfig> getSheets() {
		return sheets;
	}

	public void setSheets(LinkedList<SheetConfig> sheets) {
		this.sheets = sheets;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void addSheetConfig(String name, String sql, String dataSourceName){
		if(dataSourceName == null)
			dataSourceName = getDataSourceName();
		SheetConfig sheet = new SheetConfig(name, sql, dataSourceName);
		sheets.add(sheet);
	}
	
	public void addSheetConfig(SheetConfig sheet){
		if(null == sheet.getDataSourceName()){
			sheet.setDataSourceName(getDataSourceName());
		}
		sheets.add(sheet);
	}
}
