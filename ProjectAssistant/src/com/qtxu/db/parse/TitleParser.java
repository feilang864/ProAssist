package com.qtxu.db.parse;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TitleParser {
	private ResultSetMetaData rsmd;
	public TitleParser(ResultSet rs) throws Exception {
		// TODO Auto-generated constructor stub
		this.rsmd = rs.getMetaData();
	}
	
	public String[] parse() throws Exception{
		int columnCount = rsmd.getColumnCount();
		String[] titles = new String[columnCount];
		for(int i = 1; i <= columnCount; i ++){
			titles[i - 1] = rsmd.getColumnName(i);
		}
		return titles;
	}
}
