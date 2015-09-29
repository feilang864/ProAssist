package com.qtxu.excel.parse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.qtxu.document.dao.ExcelDocumentColumn;
import com.qtxu.excel.dao.ColumnConfig;
import com.qtxu.excel.dao.SheetConfig;

public class ColumnParser {
	private Log log = LogFactory.getLog(ColumnParser.class);
	private Sheet sheet;
	private SheetConfig sc;
	public ColumnParser(Sheet sheet, SheetConfig sc) {
		this.sheet = sheet;
		this.sc = sc;
	}
	public Map parse() throws Exception {
		Row firstRow = sheet.getRow(0);
		Map<String, Integer> indexMap = new HashMap<String, Integer>();
		
		for(int i = 0; i < firstRow.getPhysicalNumberOfCells(); i ++){
			indexMap.put(firstRow.getCell(i).getStringCellValue().trim(), i);
		}
		Map columnMap = new HashMap();
		for (ColumnConfig cc : sc.getColumns()) {
			columnMap.put(cc.getDbColumn(), new ExcelDocumentColumn(cc.getDbColumn(), cc.getType(), new LinkedList()));
		}
		
		for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++){
			Row row = sheet.getRow(i);
			for(Iterator<String> it = columnMap.keySet().iterator(); it.hasNext(); ){
				String dbColumn = it.next();
				int index = -1;
				try {
					index = indexMap.get(sc.getColumnName(dbColumn));
				} catch (Exception e) {
				}
				String str = index < 0? null : ((null == row.getCell(index))? null:row.getCell(index).toString());
				((ExcelDocumentColumn)columnMap.get(dbColumn)).getData().add(str);
			}
		}
		return columnMap;
	}
}
