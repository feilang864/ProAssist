package com.qtxu.app;

import java.util.LinkedList;

import com.qtxu.db.excuter.ExcelCollectionParser;

public class CreateExcelFile {
	public static void main(String[] args) throws Exception{
//		ExcelDocumentColumn edColumn1 = new ExcelDocumentColumn("����", "java", getData("����"));
//		ExcelDocumentColumn edColumn2 = new ExcelDocumentColumn("����", "java", getData("23"));
//		Set set = new LinkedHashSet();
//		set.add(edColumn1);set.add(edColumn2);
//		ExcelDocumentSheet sheet = new ExcelDocumentSheet("ѧ����Ϣ��", "����,����", set);
//		ExcelDocument ed = new ExcelDocument(sheet, Type.EXCEL_XLSX);


//		ExcelDocumentSheet edSheet1 = new SheetParser("mysql", "select id ���, name ����, sex �Ա�, degree ���� from myclass").parse();
//		ExcelDocumentSheet edSheet2 = new SheetParser("mysql", "select * from myclass").parse();
//		edSheet2.setName("aadd");
//		List<ExcelDocumentSheet> list = new LinkedList<ExcelDocumentSheet>();
//		list.add(edSheet1);list.add(edSheet2);
//		
//		ExcelDocument edDocument = new ExcelDocument(list, Type.EXCEL_XLSX);
//		
//		OutputStream stream = new FileOutputStream(new File("D:/a.xlsx"));
//		ExcelDocumentBuilder edb = new ExcelDocumentBuilder(edDocument);
//		edb.builder(stream);
		
		ExcelCollectionParser ecp = new ExcelCollectionParser();
		ecp.parseXML();
		//System.out.println(new Date());
		ecp.excute();
		//System.out.println(new Date());
		
	}

//	private static LinkedList<Object> getData(String string) {
//		// TODO Auto-generated method stub
//		LinkedList list = new LinkedList();
//		list.add(string);
//		return list;
//	}
}
