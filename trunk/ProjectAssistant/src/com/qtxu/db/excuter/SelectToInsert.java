package com.qtxu.db.excuter;
/**
 * ��select�Ľ����ת����insert into �����
 * ��Ҫ����������
 * 		1.sql�ĵ���������sql��ѯ�����ļ�
 * 		2.��src/datasource.xml�����õ�����Դ����
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtxu.copyfile.utils.FileUtil;
import com.qtxu.document.dao.ExcelDocumentColumn;
import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.document.util.DataConvert;
import com.qtxu.document.util.StringFunction;

public class SelectToInsert extends DBAction {
	private String sql;
	private String dataSource;
	private File selectFile;
	private File insertFile;
	private BufferedWriter bw; 
	private boolean sort;
	private Log log = LogFactory.getLog(SelectToInsert.class);
	/**
	 * ִ�е���sql��ѯ���Ĺ��췽��
	 * @param sql
	 * @param dataSource
	 * @throws Exception
	 */
	public SelectToInsert(String sql, String dataSource) throws Exception{
		this.sql = sql;
		this.dataSource = dataSource;
	}
	
	/**
	 * ִ�ж��sql�����Դ����ļ�
	 * @param selectFile
	 * @param dataSource
	 * @throws Exception
	 */
	public SelectToInsert(File selectFile, String dataSource) throws Exception{
		this.selectFile = selectFile;
		this.dataSource = dataSource;
	}
	public static void main(String[] args) throws Exception{
		SelectToInsert sti = new SelectToInsert(new File("E:/Workspaces_10/HeBeiJincheng/devlog/select.sql"), "36creditkf");
		sti.setInsertFile(new File("E:/ɽ����������/���°�/NewALS6JC/SQL/DML_INSERT.sql"));
		sti.setSort(true);
		sti.excute();
	}
	/**
	 * ��Ҫ��ʵ��ִ�з���
	 */
	public void excute() throws Exception{
		if(insertFile == null)
			insertFile = new File("insert.sql");
		if(!insertFile.exists())
			FileUtil.createNewFileAndDir(insertFile, FileUtil.FILE);
		bw = new BufferedWriter(new FileWriter(insertFile));
		//bw = new BufferedWriter(System.out);
		getInsertByList();
		bw.close();
	}
	
	/**
	 * ͨ��sql������ɵĽ���������ɵ�insert�������ļ�������
	 */
	private void getInsertBySql(){
		sql = StringFunction.getDBSql(sql);
		if(sql.length() == 0) return;
		log.info("��ʼ������䣺" + sql);
		log.info("\n");
		try {
			ExcelDocumentSheet edSheet = getExcelDocumentSheet(dataSource, sql);
			String columnName = getColumnName(edSheet);
			if(columnName.length() == 0){
				log.error("���ɵ������ַ���Ϊ�գ�");
				System.exit(0);
			}
			bw.write("--" + sql + ";");
			bw.newLine();
			bw.write("--" + formatDeleteSql() + ";");
			bw.newLine();
			int rowcount = edSheet.getRowCount();
			String insertColumnName = new StringBuffer("INSERT INTO ").append(edSheet.getName()).append(" (").append(columnName).append(") VALUES (").toString();
			log.info("��ѯ���������Ϊ��" + rowcount);
			log.info("\n");
			for(int i = 0; i < rowcount; i ++){
				Map<String, ExcelDocumentColumn> map = edSheet.getColumns();
				StringBuffer sb = new StringBuffer("");
				String title = "";
				for(int j = 0; j < edSheet.getTitles().length; j ++){
					title = edSheet.getTitles()[j];
					String dbValue = DataConvert.getSQLStringValue(map.get(title).getData().get(i), map.get(title).getClassName());
					sb.append(", ").append(dbValue);
				}
				sb.append(");");
				bw.write(insertColumnName + sb.toString().substring(2));	
				bw.newLine();
			}
			bw.newLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		log.info("��ɽ�����䣺" + sql);
		log.info("\n");
		
	}
	/**
	 * �����ɽ������select Sql��䷴������delete Sql���
	 * ���sql�д���ORDER BY�ؼ��֣�����ȥORDER BY ��֮�����䲿��
	 * @return select��Ӧ��delete���
	 */
	private String formatDeleteSql() {
		String toUpperSql = sql.toUpperCase();
		int index = 0;
		return "DELETE " + sql.substring(toUpperSql.indexOf("FROM"), (index = toUpperSql.indexOf(" ORDER BY")) > 0?index : toUpperSql.length());
	}
	
	/**
	 * ��ȡ������ֶΣ��ԡ�, ���ָ�
	 * @param edSheet
	 */
	private String getColumnName(ExcelDocumentSheet edSheet) {
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < edSheet.getTitles().length; i ++){
			sb.append(", ").append(edSheet.getTitles()[i]);
		}
		return sb.length() > 2?sb.toString().substring(2):"";
	}
	
	/**
	 * ͨ���ļ�������ȡÿ��sql��䣬Ȼ��ִ�и���sql�������insert������
	 * @throws Exception
	 */
	private void getInsertByList() throws Exception{
		ArrayList<String> sqlList = getSqlList();
		//log.info("��ʼ�����ļ�:" + selectFile.getName());
		log.info("\n");
		for(String sql : sqlList){
			this.sql = sql;
			getInsertBySql();
		}
		//log.info("��ɽ����ļ���" + selectFile.getName());
		log.info("\n");
		bw.write("COMMIT;");
	}
	
	private ArrayList<String> getSqlList() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> sqlList = new ArrayList<String>();
		if(selectFile != null){
			BufferedReader br = new BufferedReader(new FileReader(selectFile));
			while((sql = br.readLine()) != null){
				if(sql.length() == 0 || sql.startsWith("--")){
					continue;
				}
				sqlList.add(sql);
			}
			br.close();
		}else{
			sqlList.add(sql);
		}
		if(sort)
			Collections.sort(sqlList);
		return sqlList;
	}

	/**
	 * ��������insert����Ŀ���ļ�
	 * @param fileName
	 */
	public void setInsertFile(String fileName){
		setInsertFile(new File(fileName));
	}
	
	/**
	 * ��������insert����Ŀ���ļ�
	 * @param fileName
	 */
	public void setInsertFile(File insertFile){
		this.insertFile = insertFile;
	}
	
	/**
	 * ���������ļ�������������ʱ�Ƿ�����Ĭ�ϲ�����
	 * @param b
	 */
	public void setSort(boolean b) {
		// TODO Auto-generated method stub
		this.sort = b;
	}
	
}
