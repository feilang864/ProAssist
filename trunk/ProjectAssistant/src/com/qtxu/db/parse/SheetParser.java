package com.qtxu.db.parse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.qtxu.db.util.ConnectionManager;
import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.document.util.HTMLConvert;

public class SheetParser {
	private String title;
	private String dataSourceName;
	private String sql;
	private Connection conn;
	private Log logger;

	public SheetParser(String dataSourceName, String sql) throws Exception {
		this.logger = LogFactory.getLog(SheetParser.class);
		this.dataSourceName = dataSourceName;
		this.sql = HTMLConvert.replaceAllByHTMLChar(sql);
		try {
			this.conn = ConnectionManager.getInstance().getConnection(dataSourceName);
			if(conn == null){
				logger.error("请确认数据源<"+dataSourceName+">是否存在！");
				System.exit(0);
			}
		} catch (Exception e) {
			logger.error("请确认数据源<"+dataSourceName+">是否存在！");
			System.exit(0);
		}
	}

	@SuppressWarnings("unchecked")
	public ExcelDocumentSheet parse() throws Exception {
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery(sql);
		//Orcale 无法用下方法获取表名
		//String sheetName = rs.getMetaData().getTableName(1);
		String upSql = sql.toUpperCase();
		String sheetName = upSql.substring(upSql.indexOf("FROM") + 4, upSql.indexOf("WHERE") == -1?(upSql.indexOf("ORDER") == -1?upSql.length():upSql.indexOf("ORDER")):upSql.indexOf("WHERE")).trim();
		String[] titles = new TitleParser(rs).parse();
		Map columnMap = new ColumnParser(rs).parse();
		rs.close();
		state.close();
		return new ExcelDocumentSheet(sheetName, titles, columnMap);
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
