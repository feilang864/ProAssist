package com.qtxu.app;

import com.qtxu.excel.excuter.ExcelToInsert;

public class ExcelToInsertFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ExcelToInsert eti = new ExcelToInsert();
		eti.parseXML();
		eti.excute();
	}
}
