package com.qtxu.excel.dao;

import java.util.LinkedList;

public class ExcelConfig {
	private String srcFile;
	private String writeFile;
	private LinkedList<SheetConfig> sheets = new LinkedList<SheetConfig>();
	
	public ExcelConfig(){
		
	}
	public ExcelConfig(String srcFile, String writeFile, LinkedList<SheetConfig> sheets){
		this.srcFile = srcFile;
		this.writeFile = writeFile;
		this.sheets = sheets;
	}
	public String getSrcFile() {
		return srcFile;
	}
	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	public String getWriteFile() {
		return writeFile;
	}
	public void setWriteFile(String writeFile) {
		this.writeFile = writeFile;
	}
	public LinkedList<SheetConfig> getSheets() {
		return sheets;
	}
	public void setSheets(LinkedList<SheetConfig> sheets) {
		this.sheets = sheets;
	}
	
	public void addSheetConfig(String name, String dbTable){
		SheetConfig sheet = new SheetConfig(name, dbTable);
		sheets.add(sheet);
	}
	
	public void addSheetConfig(SheetConfig sheet){
		sheets.add(sheet);
	}
	
}
