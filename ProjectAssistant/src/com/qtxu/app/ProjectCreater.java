package com.qtxu.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.qtxu.copyfile.entity.ByteCopyFiles;
import com.qtxu.copyfile.inter.AbstractCopyTemplete;
import com.qtxu.copyfile.inter.ClassFileListCreater;
import com.qtxu.copyfile.inter.FileListCreater;
import com.qtxu.copyfile.utils.FileUtil;
import com.qtxu.db.excuter.SelectToInsert;

public class ProjectCreater {
	
	private String fromPath = "E:/Workspaces_8.5";
	private String toPath = "E:/02_SXJC/���°�/20140715_��������Ż�/��������Ż����� ������/2��Ӧ������/AmarExportJC";
	private File content = new File("E:/Workspaces_8.5/devlog/qtxu/20140905_���������ļ�/srclist.txt"); 
//	private String fromPath = "E:/Workspaces_10/HeBeiJincheng/ALS6JC_ZONGHEEDU";
//	private String toPath = "E:/ɽ����������/���°�/20140604_NewALS6JC/ALS6JC";
//	private File content = new File("E:/Workspaces_10/HeBeiJincheng/devlog/filelist.txt");
//	
//	private String fromPath = "E:/Workspaces_10/HeBeiJincheng/ALS6TEST";
//	private String toPath = "E:/ɽ����������/���°�/ALS6TEST";
//	private File content = new File("E:/Workspaces_10/HeBeiJincheng/devlog/filelist.txt");
		
//	private String fromPath = "E:/Workspaces_10/HeBeiJincheng";
//	private String toPath = "E:/ɽ����������/���°�/�������(EDU)new3";
//	private File content = new File("etc/laozhufilelist.txt");
	
	private String selectSqlFile = "E:/Workspaces_8.5/devlog/qtxu/20140826_����/ddl.sql";
	private String insertSqlFile = "E:/02_SXJC/���°�/20140715_��������Ż�/��������Ż����� ������/2��Ӧ������/AmarExportJC/DML65.sql";
	private String dataSource = "jcsc65";//"36creditcredit";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ProjectCreater pc = new ProjectCreater();
		
		// ժȡSQL
		pc.getSQLFile();
		
		//ժȡ����
		//pc.getProjectFile();
		
		// ����java�ļ���class��
		//pc.copyJavaFile();
	}

	private void copyJavaFile() throws Exception {
		// TODO Auto-generated method stub
		Set<String> set = new HashSet<String>();
		List<String> list = new ArrayList<String>();
		FileUtil.getFilePath(new File(toPath), new String[]{".java"}, true, set);
		for(Iterator<String> it = set.iterator(); it.hasNext();){
			list.add(FileUtil.replaceAll(it.next().replaceAll(toPath, ""), new String[][]{
				{"/ALS6TEST/src/", ""}
			}));
		}
		AbstractCopyTemplete copy = new ByteCopyFiles(toPath + "/ALS6JC_ZONGHEEDU/src/", toPath + "/ALS6JC_ZONGHEEDU/WebRoot/WEB-INF/classes/");
		copy.setContent(list);
		copy.excute();
	}

	

	private void getProjectFile() throws Exception {

		AbstractCopyTemplete copy = new ByteCopyFiles(fromPath, toPath, content);
		String[][] replaceContent = new String[][]{
				{".java", ".class"}, 
				{"/ALS6JC_JITUAN/src/", "/ALS6JC_JITUAN/WebRoot/WEB-INF/classes/"}, 
				{"/ploan_JC/src/", "/ploan_JC/classes/"}};
		copy.setFileListCreater(new ClassFileListCreater(replaceContent));
		
		copy.excute();
	}

	
	private void getSQLFile() throws Exception {
		SelectToInsert sti = new SelectToInsert(new File(selectSqlFile), dataSource);
		sti.setInsertFile(new File(insertSqlFile));
		sti.setSort(true);
		sti.excute();
	}

}
