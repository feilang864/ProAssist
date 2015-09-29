package com.qtxu.document.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDocumentFactory {
	private ExcelDocumentFactory() {
	}
	
	public static Workbook createWorkBook(Type type){
		if(type.equals(Type.EXCEL_XLS)){
			return new HSSFWorkbook();
		}else if(type.equals(Type.EXCEL_XLSX)){
			return new XSSFWorkbook();
			//return new SXSSFWorkbook(1000);
		}else{
			return null;
		}
	}
}
