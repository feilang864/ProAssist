package com.qtxu.db.excuter;

import com.qtxu.db.parse.SheetParser;
import com.qtxu.db.util.ConnectionManager;
import com.qtxu.document.dao.ExcelDocumentSheet;

public abstract class DBAction {
	protected static ConnectionManager cm = null;
	static {
		try {
			cm = ConnectionManager.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected ExcelDocumentSheet getExcelDocumentSheet(String dataSourceName, String sql) throws Exception{
		return new SheetParser(dataSourceName, sql).parse();
	}
	
	public abstract void excute() throws Exception;
}
