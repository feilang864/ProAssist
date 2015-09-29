package com.qtxu.db.excuter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtxu.db.dao.ExcelFileConfig;
import com.qtxu.db.dao.SheetConfig;
import com.qtxu.db.parse.SheetParser;
import com.qtxu.db.util.ConnectionManager;
import com.qtxu.document.dao.ExcelDocument;
import com.qtxu.document.dao.ExcelDocumentBuilder;
import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.document.util.Type;

public class ExcelCollectionParser extends DBAction {
	private Log log = LogFactory.getLog(ExcelCollectionParser.class);
	private LinkedList<ExcelFileConfig> excelFiles = new LinkedList<ExcelFileConfig>();

	public LinkedList<ExcelFileConfig> getExcelFiles() {
		return excelFiles;
	}

	public void setExcelFiles(LinkedList<ExcelFileConfig> excelFiles) {
		this.excelFiles = excelFiles;
	}

	public ExcelCollectionParser() {

	}

	public void parseXML() throws Exception {
		String configFile = "exceldocument.xml";
		log.info("��ʼ���������ļ���" + configFile);
		log.info("\n");
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		digester.addObjectCreate("excel-collection/excel",
				ExcelFileConfig.class);
		digester.addSetProperties("excel-collection/excel");
		digester.addObjectCreate("excel-collection/excel/sheet",
				SheetConfig.class);
		digester.addSetProperties("excel-collection/excel/sheet");
		digester.addSetNext("excel-collection/excel/sheet", "addSheetConfig");
		digester.addSetNext("excel-collection/excel", "addExcelConfig");
		digester.parse(new FileInputStream(configFile));
		log.info("��ɽ��������ļ���");
		log.info("\n");
	}

	public void addExcelConfig(ExcelFileConfig efc) {
		excelFiles.add(efc);
	}

	public void excute() throws Exception {
		for (ExcelFileConfig efc : excelFiles) {
			String fileName = efc.getFileName();
			List<ExcelDocumentSheet> list = new LinkedList<ExcelDocumentSheet>();
			for (SheetConfig sheet : efc.getSheets()) {
				ExcelDocumentSheet edSheet = getExcelDocumentSheet(sheet.getDataSourceName(), sheet.getSql());
				String sheetName = sheet.getName();
				if(sheetName != null && sheetName.length() > 0){
					edSheet.setName(sheetName);
				}
				list.add(edSheet);
			}
			ExcelDocument edDocument = new ExcelDocument(list, Type.getFileType(fileName.substring(fileName.lastIndexOf("."))));
			OutputStream stream = null;
			try {
				stream = new FileOutputStream(new File(fileName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("�޷������ļ���" + fileName + "�� ��������ϵͳ�Ѿ������ļ���");
				log.error("\n");
				System.exit(0);
			}
			log.info("��ʼ�����ļ���" + fileName);
			log.info("\n");
			ExcelDocumentBuilder edb = new ExcelDocumentBuilder(edDocument);
			edb.builder(stream);
			stream.close();
			log.info("��������ļ���" + fileName);
			log.info("\n");
		}
		cm.close();
	}
}
