package com.qtxu.document.dao;
/**
 * Excel文件对象
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qtxu.document.util.Type;

public class ExcelDocument {
	/**
	 * Excel文件的类型：参见枚举Type
	 */
	private Type type;
	/**
	 * Excel中sheet的集合
	 */
	private List<ExcelDocumentSheet> sheets = new LinkedList<ExcelDocumentSheet>();
	
	public ExcelDocument(ExcelDocumentSheet sheet, Type type){
		this.addSheet(sheet);
		this.type = type;
	}
	
	public ExcelDocument(List<ExcelDocumentSheet> sheets, Type type){
		this.sheets = sheets;
		this.type = type;
	}
	/**
	 * 向Excel中添加sheet页，默认为追加到当前文档最后面
	 * @param sheet
	 */
	public void addSheet(ExcelDocumentSheet sheet) {
		// TODO Auto-generated method stub
		if(sheet == null)
			return;
		addSheet(sheets.size(), sheet);
	}
	
	/**
	 * 向Excel中添加sheet页，需指定位置，当前位置后移。
	 * @param index
	 * @param sheet
	 */
	public void addSheet(int index, ExcelDocumentSheet sheet){
		if(sheet == null){
			return ;
		}
		sheets.add(index, sheet);
	}
	
	/**
	 * 根据sheet名字删除页
	 * @param sheetName
	 */
	public void removeSheet(String sheetName){
		int index = -1;
		for(int i = 0; i < sheets.size(); i ++){
			if(sheets.get(i).getName().equalsIgnoreCase(sheetName.trim())){
				index = i;
				break;
			}
		}
		if(index > -1)
			removeSheet(index);
	}
	/**
	 * 根据sheet页的索引删除sheet，索引从0开始
	 * @param index
	 */
	public void removeSheet(int index){
		sheets.remove(index);
	}
	
	public ExcelDocument(ArrayList<ExcelDocumentSheet> sheets){
		this.setSheets(sheets);
	}
	/**
	 * 获取Excel中sheet页的集合
	 * @return
	 */
	public List<ExcelDocumentSheet> getSheets() {
		return sheets;
	}
	/**
	 * 设置Excel中sheet页的集合
	 * @param sheets
	 */
	public void setSheets(ArrayList<ExcelDocumentSheet> sheets) {
		this.sheets = sheets;
	}
	/**
	 * 获取Excel文件的类型
	 * @return
	 */
	public Type getType() {
		return type;
	}
	/**
	 * 设置Excel文件的类型
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
}
