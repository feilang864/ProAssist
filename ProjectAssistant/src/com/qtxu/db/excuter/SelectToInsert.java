package com.qtxu.db.excuter;
/**
 * 将select的结果集转换成insert into 的语句
 * 需要两个参数：
 * 		1.sql的单独语句或含有sql查询语句的文件
 * 		2.在src/datasource.xml中配置的数据源名称
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
	 * 执行单个sql查询语句的构造方法
	 * @param sql
	 * @param dataSource
	 * @throws Exception
	 */
	public SelectToInsert(String sql, String dataSource) throws Exception{
		this.sql = sql;
		this.dataSource = dataSource;
	}
	
	/**
	 * 执行多个sql，可以传入文件
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
		sti.setInsertFile(new File("E:/山西晋城银行/更新包/NewALS6JC/SQL/DML_INSERT.sql"));
		sti.setSort(true);
		sti.excute();
	}
	/**
	 * 主要的实际执行方法
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
	 * 通过sql语句生成的结果集将生成的insert语句读入文件输入流
	 */
	private void getInsertBySql(){
		sql = StringFunction.getDBSql(sql);
		if(sql.length() == 0) return;
		log.info("开始解析语句：" + sql);
		log.info("\n");
		try {
			ExcelDocumentSheet edSheet = getExcelDocumentSheet(dataSource, sql);
			String columnName = getColumnName(edSheet);
			if(columnName.length() == 0){
				log.error("生成的列名字符串为空！");
				System.exit(0);
			}
			bw.write("--" + sql + ";");
			bw.newLine();
			bw.write("--" + formatDeleteSql() + ";");
			bw.newLine();
			int rowcount = edSheet.getRowCount();
			String insertColumnName = new StringBuffer("INSERT INTO ").append(edSheet.getName()).append(" (").append(columnName).append(") VALUES (").toString();
			log.info("查询结果集行数为：" + rowcount);
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
		log.info("完成解析语句：" + sql);
		log.info("\n");
		
	}
	/**
	 * 对生成结果集的select Sql语句反向生成delete Sql语句
	 * 如果sql中存在ORDER BY关键字，就舍去ORDER BY 及之后的语句部分
	 * @return select相应的delete语句
	 */
	private String formatDeleteSql() {
		String toUpperSql = sql.toUpperCase();
		int index = 0;
		return "DELETE " + sql.substring(toUpperSql.indexOf("FROM"), (index = toUpperSql.indexOf(" ORDER BY")) > 0?index : toUpperSql.length());
	}
	
	/**
	 * 获取表的列字段，以“, ”分隔
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
	 * 通过文件解析获取每个sql语句，然后执行根据sql语句生成insert的任务
	 * @throws Exception
	 */
	private void getInsertByList() throws Exception{
		ArrayList<String> sqlList = getSqlList();
		//log.info("开始解析文件:" + selectFile.getName());
		log.info("\n");
		for(String sql : sqlList){
			this.sql = sql;
			getInsertBySql();
		}
		//log.info("完成解析文件：" + selectFile.getName());
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
	 * 设置生成insert语句的目标文件
	 * @param fileName
	 */
	public void setInsertFile(String fileName){
		setInsertFile(new File(fileName));
	}
	
	/**
	 * 设置生成insert语句的目标文件
	 * @param fileName
	 */
	public void setInsertFile(File insertFile){
		this.insertFile = insertFile;
	}
	
	/**
	 * 设置生成文件结果集插入语句时是否排序，默认不排序
	 * @param b
	 */
	public void setSort(boolean b) {
		// TODO Auto-generated method stub
		this.sort = b;
	}
	
}
