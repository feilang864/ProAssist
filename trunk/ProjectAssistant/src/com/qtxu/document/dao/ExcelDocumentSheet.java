package com.qtxu.document.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ExcelDocumentSheet {
	/**
	 * sheetҳ������
	 */
	private String name;
	/**
	 * sheetҳ�ı���
	 */
	private String[] titles;
	/**
	 * sheetҳ���еļ���
	 */
	private Map<String, ExcelDocumentColumn> columns = new HashMap<String, ExcelDocumentColumn>();

	public ExcelDocumentSheet(String name, String titles, Set columns) {
		this(name, titles.split(","), getMapFromSet(columns));
	}

	public ExcelDocumentSheet(String name, String[] titles, Map columns) {
		this.name = name;
		this.setTitles(titles);
		this.setColumns(columns);
	}

	/**
	 * ���ص�ǰҳ����������
	 * 
	 * @return
	 */
	public int getRowCount() {
		return columns.get(titles[0]).getData().size();
	}

	/**
	 * ���ص�ǰҳ������
	 * 
	 * @return
	 */
	public int getColumnCount() {
		return titles.length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, ExcelDocumentColumn> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ExcelDocumentColumn> columns) {
		this.columns = columns;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	private static Map<String, ExcelDocumentColumn> getMapFromSet(Set<ExcelDocumentColumn> columns) {
		// TODO Auto-generated method stub
		Map<String, ExcelDocumentColumn> columns1 = new HashMap<String, ExcelDocumentColumn>();
		for(Iterator<ExcelDocumentColumn> it = columns.iterator(); it.hasNext(); ){
			ExcelDocumentColumn edColumn = it.next();
			columns1.put(edColumn.getName(), edColumn);
		}
		return columns1;
	}

}
