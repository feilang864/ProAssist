package com.qtxu.db.excuter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.qtxu.document.dao.ExcelDocumentColumn;
import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.document.util.DataConvert;

public class SelectToText extends DBAction{
	private String dataSource;
	private String sql;
	private String outFile;
	private Boolean console;
	private BufferedWriter bw;
	
	public SelectToText(String dataSource, String sql){
		this.setSql(sql);
		this.setDataSource(dataSource);
	}
	@Override
	public void excute() throws Exception {
		StringBuffer sb = new StringBuffer("");
		if(!isConsole()){
			if(outFile != null){
				bw = new BufferedWriter(new FileWriter(new File(outFile)));
			}else {
				throw new Exception("SelectToText对象属性不正确");
			}
		}
		ExcelDocumentSheet edSheet = getExcelDocumentSheet(dataSource, sql);
		for(int i = 0; i < edSheet.getRowCount(); i ++){
			Map<String, ExcelDocumentColumn> map = edSheet.getColumns();
			String title = "";
			for(int j = 0; j < edSheet.getTitles().length; j ++){
				title = edSheet.getTitles()[j];
				String dbValue = DataConvert.getSQLStringValue(map.get(title).getData().get(i), map.get(title).getClassName());
				sb.append(", ").append(dbValue);
			}
			if(isConsole()){
				System.out.println(sb.toString().substring(2));
			}else{
				bw.write(sb.toString().substring(2));
				bw.newLine();
			}
			sb.delete(0, sb.length());
		}
		if(bw != null)
			bw.close();
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public Boolean isConsole() {
		return console;
	}
	public void setConsole(Boolean console) {
		this.console = console;
	}
	public String getOutFile() {
		return outFile;
	}
	public void setOutFile(String outFile) {
		this.outFile = outFile;
	}
}
