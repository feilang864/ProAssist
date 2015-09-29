package com.qtxu.document.dao;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.qtxu.document.util.CellManager;
import com.qtxu.document.util.ExcelDocumentFactory;

public class ExcelDocumentBuilder {
	private Log log = LogFactory.getLog(ExcelDocumentBuilder.class);
	private ExcelDocument excelDocument;
	
	public ExcelDocumentBuilder(ExcelDocument excelDocument){
		this.excelDocument = excelDocument;
	}
	public ExcelDocument getExcelDocument() {
		return excelDocument;
	}

	public void setExcelDocument(ExcelDocument excelDocument) {
		this.excelDocument = excelDocument;
	}
	
	public void builder(OutputStream outputStream) throws IOException{
		//Workbook wb = ExcelDocumentFactory.createWorkBook(excelDocument.getType());
		Workbook wb = new SXSSFWorkbook(100);
		String sheetName = "";
		for(ExcelDocumentSheet sheet : this.excelDocument.getSheets()){
			sheetName = WorkbookUtil.createSafeSheetName(sheet.getName());
			log.info("开始Excel文件页签：" + sheetName);
			log.info("\n");
			Sheet sheetActul = wb.createSheet(sheetName);
			//生成标头
			createSheetTile(wb, sheetActul, sheet);
			createSheetData(wb, sheetActul, sheet);
			log.info("完成Excel文件页签：" + sheetName);
			log.info("\n");
		}
		wb.write(outputStream);
//		if(wb instanceof SXSSFWorkbook){
//			(SXSSFWorkbook)wb.dispose();
//		}
		
	}
	
	/**
	 * 生成sheet页的数据
	 * @param wb 
	 * @param sheetActul
	 * @param sheet
	 */
	private void createSheetData(Workbook wb, Sheet sheet, ExcelDocumentSheet edSheet) {
		// TODO Auto-generated method stub
		String[] titles = edSheet.getTitles();
		int rowCount = edSheet.getRowCount();
		int columnCount = edSheet.getColumnCount();
		Row row = null;
		Cell cell = null;
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    for(int i = 1; i <= rowCount && rowCount > 0; i ++){
			row = sheet.createRow(i);
			ExcelDocumentColumn edColumn = null;
			for(int j = 0; j < columnCount; j ++){
				edColumn = edSheet.getColumns().get(titles[j]);
				cell = row.createCell(j);
				if(i%2 == 0){
					cell.setCellStyle(style);
				}
				CellManager.setCellData(cell, edColumn.getClassName(), edColumn.getData().get(i-1));
				//row.createCell(j).setCellValue(edSheet.getColumns().get(titles[j]).getData().get(i-1).toString());
			}
			if(i%100 == 0){
				System.out.println("RowNum: " + i);
			}
		}
	}
	/**
	 * 生成标题头
	 * @param wb 
	 * @param sheetActul
	 * @param sheet
	 */
	private void createSheetTile(Workbook wb, Sheet sheet, ExcelDocumentSheet edSheet) {
		// TODO Auto-generated method stub
		String[] titles = edSheet.getTitles();
		Row row = sheet.createRow(0);
		Cell cell = null;
		CellStyle style = null;
		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		//style.setBorderLeft(CellStyle.BORDER_THIN);
		//style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THICK);
		
		// Create a new font and alter it.
	    Font font = wb.createFont();
	    font.setFontHeightInPoints((short)12);
	    //font.setFontName("Courier New");
	    font.setItalic(false);
	    font.setStrikeout(false);
	    
	    style.setFont(font);
	    for(int i = 0; i < titles.length; i ++){
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}
	}
}
