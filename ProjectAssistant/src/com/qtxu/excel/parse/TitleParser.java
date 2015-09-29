package com.qtxu.excel.parse;

import org.apache.poi.ss.usermodel.Sheet;

import com.qtxu.excel.dao.ColumnConfig;
import com.qtxu.excel.dao.SheetConfig;

public class TitleParser {
	private Sheet sheet;
	private SheetConfig sc; 
	public TitleParser(Sheet sheet, SheetConfig sc) {
		this.sheet = sheet;
		this.sc = sc;
	}
	
	public String[] parse() throws Exception{
		String[] titles = new String[sc.getColumnCount()];
		int i = -1;
		for(ColumnConfig cc : sc.getColumns()){
			titles[++i] = cc.getDbColumn();
		}
		return titles;
	}
}
