package com.qtxu.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.qtxu.copyfile.entity.ByteCopyFiles;
import com.qtxu.copyfile.inter.AbstractCopyTemplete;
import com.qtxu.copyfile.inter.ClassFileListCreater;
import com.qtxu.copyfile.utils.FileUtil;
import com.qtxu.db.excuter.SelectToInsert;

public class ProjectCreaterOfEDu {
	
//	private String fromPath = "E:/Workspaces_10/HeBeiJincheng";
//	private String toPath = "E:/山西晋城银行/更新包/20140604_个贷核算更新包——1";
//	private File content = new File("etc/laozhufilelist.txt"); 
	private String toParentPath = "E:/学习资料/99_Amarsoft/Customer/河北廊坊银行/壹信贷/更新包";
	private String fromPath = "D:/Workspaces/Work/lfploan";
	private String toPath = toParentPath + "/lfploan_YXD_20150523_001";
	private File content = new File(toParentPath + "/url.txt");

	private String selectSqlFile = toParentPath + "/select.sql";
	private String insertSqlFile = toPath + "/insert.sql";
	private String dataSource = "lftestgbk219";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ProjectCreaterOfEDu pc = new ProjectCreaterOfEDu();
		
		// 摘取SQL
		pc.getSQLFile();
		
		//摘取程序
		pc.getProjectFile();
		
		// 复制java文件到class下
		//pc.copyJavaFile();
	}

	private void copyJavaFile() throws Exception {
		// TODO Auto-generated method stub
		Set<String> set = new HashSet<String>();
		List<String> list = new ArrayList<String>();
		FileUtil.getFilePath(new File(toPath), new String[]{".java"}, true, set);
		for(Iterator<String> it = set.iterator(); it.hasNext();){
			list.add(FileUtil.replaceAll(it.next().replaceAll(toPath, ""), new String[][]{
				{"/ploan_JC/src/", ""}
			}));
		}
		AbstractCopyTemplete copy = new ByteCopyFiles(toPath + "/ALS6JC_ZONGHEEDU/src/", toPath + "/ALS6JC_ZONGHEEDU/WebRoot/WEB-INF/classes/");
		copy.setContent(list);
		copy.excute();
	}

	

	private void getProjectFile() throws Exception {

		AbstractCopyTemplete copy = new ByteCopyFiles(fromPath, toPath, content);
		copy.setFileListCreater(new ClassFileListCreater(new String[][]{
				{".java", ".class"}, 
				{"/src/", "/WebRoot/WEB-INF/classes/"},
				}));
		copy.excute();
	}

	
	private void getSQLFile() throws Exception {
		SelectToInsert sti = new SelectToInsert(new File(selectSqlFile), dataSource);
		sti.setInsertFile(new File(insertSqlFile));
		sti.setSort(true);
		sti.excute();
	}

}
