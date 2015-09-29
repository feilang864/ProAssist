package com.qtxu.document.dao;
/**
 * Excel�ļ�����
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.qtxu.document.util.Type;

public class ExcelDocument {
	/**
	 * Excel�ļ������ͣ��μ�ö��Type
	 */
	private Type type;
	/**
	 * Excel��sheet�ļ���
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
	 * ��Excel�����sheetҳ��Ĭ��Ϊ׷�ӵ���ǰ�ĵ������
	 * @param sheet
	 */
	public void addSheet(ExcelDocumentSheet sheet) {
		// TODO Auto-generated method stub
		if(sheet == null)
			return;
		addSheet(sheets.size(), sheet);
	}
	
	/**
	 * ��Excel�����sheetҳ����ָ��λ�ã���ǰλ�ú��ơ�
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
	 * ����sheet����ɾ��ҳ
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
	 * ����sheetҳ������ɾ��sheet��������0��ʼ
	 * @param index
	 */
	public void removeSheet(int index){
		sheets.remove(index);
	}
	
	public ExcelDocument(ArrayList<ExcelDocumentSheet> sheets){
		this.setSheets(sheets);
	}
	/**
	 * ��ȡExcel��sheetҳ�ļ���
	 * @return
	 */
	public List<ExcelDocumentSheet> getSheets() {
		return sheets;
	}
	/**
	 * ����Excel��sheetҳ�ļ���
	 * @param sheets
	 */
	public void setSheets(ArrayList<ExcelDocumentSheet> sheets) {
		this.sheets = sheets;
	}
	/**
	 * ��ȡExcel�ļ�������
	 * @return
	 */
	public Type getType() {
		return type;
	}
	/**
	 * ����Excel�ļ�������
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
}
