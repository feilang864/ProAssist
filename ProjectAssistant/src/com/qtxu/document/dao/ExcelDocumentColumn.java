package com.qtxu.document.dao;

import java.util.LinkedList;

public class ExcelDocumentColumn {
	/**
	 * �б�ͷ������
	 */
	private String name;
	/**
	 * �����ݵ�java����
	 */
	private String className;
	/**
	 * �����ݼ���
	 */
	private LinkedList<Object> data;
	
	public ExcelDocumentColumn(String name, String className){
		this(name, className, null);
	}
	public ExcelDocumentColumn(String name, String className,
			LinkedList<Object> data) {
		this.name = name;
		this.className = className;
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public LinkedList<Object> getData() {
		return data;
	}
	public void setData(LinkedList<Object> data) {
		this.data = data;
	}
	
	
	
}
