package com.qtxu.excel.parse;

import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.excel.dao.SheetConfig;

public class SheetParser {
	private Sheet sheet;
	private SheetConfig sc;
	
	public SheetParser(Sheet sheet, SheetConfig sc) {
		this.sheet = sheet;
		this.setSc(sc);
	}

	public ExcelDocumentSheet parse() throws Exception {
		String sheetName = sheet.getSheetName();
		String[] titles = new TitleParser(sheet, sc).parse();
		Map columnMap = new ColumnParser(sheet, sc).parse();
		return new ExcelDocumentSheet(sc.getDbTable(), titles, columnMap);
	}

	public void setSc(SheetConfig sc) {
		this.sc = sc;
	}

	public SheetConfig getSc() {
		return sc;
	}
}
