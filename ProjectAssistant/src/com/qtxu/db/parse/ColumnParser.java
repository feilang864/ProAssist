package com.qtxu.db.parse;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.qtxu.document.dao.ExcelDocumentColumn;
import com.qtxu.document.util.DataConvert;

public class ColumnParser {
	private ResultSetMetaData rsmd;
	private ResultSet rs;

	public ColumnParser(ResultSet rs) throws Exception {
		this.rs = rs;
		this.rsmd = rs.getMetaData();
	}

	public Map parse() throws Exception {
		Set columnSet = new HashSet();
		Map columnMap = new HashMap();
		int columnCount = rsmd.getColumnCount();
		String columnName = "";
		for (int i = 1; i <= columnCount; i++) {
			columnName = rsmd.getColumnName(i);
			columnMap.put(
					columnName,
					new ExcelDocumentColumn(columnName, rsmd.getColumnClassName(i), new LinkedList()));
		}
		int rowNum = 0 ;
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				((ExcelDocumentColumn) columnMap.get(rsmd.getColumnName(i))).getData().add(DataConvert.getDBColumnObject(rs, i, rsmd.getColumnClassName(i)));
			}
//			if((++rowNum)%100 == 0){
//				System.out.println("结果集第行：" + rowNum);
//			}
		}
		return columnMap;
	}

}
