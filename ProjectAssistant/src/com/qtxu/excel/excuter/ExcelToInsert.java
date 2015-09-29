package com.qtxu.excel.excuter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester3.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qtxu.db.excuter.ExcelCollectionParser;
import com.qtxu.document.dao.ExcelDocumentColumn;
import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.document.util.DataConvert;
import com.qtxu.excel.dao.ColumnConfig;
import com.qtxu.excel.dao.ExcelConfig;
import com.qtxu.excel.dao.ExcelValueManager;
import com.qtxu.excel.dao.SheetConfig;
import com.qtxu.excel.parse.SheetParser;

/**
 * 将Excel文件按行记录导出insert语句到某个文件
 * @author Administrator
 *
 */
public class ExcelToInsert {
	private Log log = LogFactory.getLog(ExcelCollectionParser.class);
	private LinkedList<ExcelConfig> excelFiles = new LinkedList<ExcelConfig>();
	
	public ExcelToInsert(){
		
	}
	
	public void parseXML() throws Exception {
		String configFile = "exceltoinsert.xml";
		log.info("开始解析配置文件：" + configFile);
		log.info("\n");
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		digester.addObjectCreate("excel-collection/excel",ExcelConfig.class);
		digester.addSetProperties("excel-collection/excel");
		digester.addObjectCreate("excel-collection/excel/sheet", SheetConfig.class);
		digester.addSetProperties("excel-collection/excel/sheet");
		digester.addObjectCreate("excel-collection/excel/sheet/column", ColumnConfig.class);
		digester.addSetProperties("excel-collection/excel/sheet/column");
		digester.addSetNext("excel-collection/excel/sheet/column", "addColumnConfig");
		digester.addSetNext("excel-collection/excel/sheet", "addSheetConfig");
		digester.addSetNext("excel-collection/excel", "addExcelConfig");
		digester.parse(new FileInputStream(configFile));
		log.info("完成解析配置文件！");
		log.info("\n");
	}
	public void addExcelConfig(ExcelConfig ec) {
		excelFiles.add(ec);
	}
	
	
	public void excute() throws Exception{
		for(ExcelConfig ec : excelFiles){
			File file = new File(ec.getSrcFile());
			if(!file.exists()){
				log.error("Excel文件[" + ec.getSrcFile() + "]不存在！");
				log.error("\n");
				System.exit(0);
			}
			Workbook wb =WorkbookFactory.create(file);
			if(!volidate(file.getName(), wb, ec))
				System.exit(0);
			log.info("开始读取文件：" + file.getName());
			log.info("\n");
			boolean isAppend = false;
			for(SheetConfig sc : ec.getSheets()){
				if(sc.getWriteFile() == null || sc.getWriteFile().length() == 0){
					sc.setWriteFile(ec.getWriteFile());
				}
				log.info("开始读取sheet页签：" + sc.getName());
				log.info("\n");
				Sheet sheet = wb.getSheet(sc.getName());
				SheetParser sheetParser = new SheetParser(sheet, sc);
				ExcelDocumentSheet edSheet = sheetParser.parse();
				ExcelValueManager.action(edSheet, sc);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(sc.getWriteFile()), isAppend));
				int rowcount = edSheet.getRowCount();
				String insertColumnName = new StringBuffer("INSERT INTO ").append(edSheet.getName()).append(" (").append(getDBColumnSet(edSheet.getTitles())).append(") VALUES (").toString();
				log.info("查询文件集行数为：" + rowcount);
				log.info("\n");
				for(int i = 0; i < rowcount; i ++){
					Map<String, ExcelDocumentColumn> map = edSheet.getColumns();
					StringBuffer sb = new StringBuffer("");
					String title = "";
					for(int j = 0; j < edSheet.getTitles().length; j ++){
						title = edSheet.getTitles()[j];
						String dbValue = DataConvert.getSQLStringValue(map.get(title).getData().get(i), map.get(title).getClassName());
						sb.append(", ").append(dbValue);
					}
					sb.append(");");
					bw.write(insertColumnName + sb.toString().substring(2));	
					bw.newLine();
					//log.info("\n");
				}
				bw.newLine();
				bw.newLine();
				bw.close();
				isAppend = false;
				log.info("完成读取sheet页签：" + sc.getName());
				log.info("\n");
				
			}
			log.info("完成读取文件：" + file.getName());
			log.info("\n");
		}
	}
	
	private String getDBColumnSet(String[] columnArr){
		StringBuffer sb = new StringBuffer("");
		for(String column : columnArr){
			sb.append(", ").append(column);
		}
		return sb.delete(0, 2).toString();
	}
	
	private boolean volidate(String fileName, Workbook wb, ExcelConfig ec){
		boolean retValue = true;
		Set<String> columnSet = null;
		
		for(SheetConfig sc : ec.getSheets()){
			String sheetName = sc.getName();
			Sheet sheet = null;
			try {
				sheet = wb.getSheet(sheetName);
			} catch (Exception e) {
			}
			
			if(sheet == null){
				log.error("Excel文件["+fileName+"]中缺少页签：" + sheetName);
				log.error("\n");
				retValue = false;
				continue;
			}else{
				Row firstRow = sheet.getRow(0);
				columnSet = new HashSet<String>();
				for(int i = 0; i < firstRow.getPhysicalNumberOfCells(); i ++){
					columnSet.add(firstRow.getCell(i).getStringCellValue().trim());
				}
			}
			for(ColumnConfig cc : sc.getColumns()){
//				System.out.println(cc);
				if(!columnSet.contains(cc.getName()) && !cc.isExt()){
					log.error("Excel文件["+fileName+"]中页签：" + sheetName + " 缺少列：" + cc.getName());
					log.error("\n");
					retValue = false;
				}
			}
		}
		return retValue;
	}

}
