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
import com.qtxu.copyfile.inter.FTPByteCopyFiles;
import com.qtxu.copyfile.utils.FileUtil;
import com.qtxu.db.excuter.SelectToInsert;

public class ProjectCreaterFromFTP {
	
//	private String fromPath = "E:/Workspaces_10/HeBeiJincheng";
//	private String toPath = "E:/山西晋城银行/更新包/20140604_个贷核算更新包——1";
//	private File content = new File("etc/laozhufilelist.txt"); 
	private String toParentPath = "E:/山西晋城银行/更新包/20140605_NewALS6JC2";
	private String fromPath = "E:/Workspaces_10/HeBeiJincheng/ALS6JC_ZONGHEEDU";
	private String toPath = toParentPath + "/ALS6JC";
	private File content = new File("filelist.txt");

	private String selectSqlFile = "E:/Workspaces_10/HeBeiJincheng/devlog/select.sql";
	private String insertSqlFile = toParentPath + "/SQL/DML_INSERT.sql";
	private String dataSource = "36creditkf";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ProjectCreaterFromFTP pc = new ProjectCreaterFromFTP();
		
		// 摘取SQL
		//pc.getSQLFile();
		
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
				{"/ALS6TEST/src/", ""}
			}));
		}
		AbstractCopyTemplete copy = new ByteCopyFiles(toPath + "/ALS6JC_ZONGHEEDU/src/", toPath + "/ALS6JC_ZONGHEEDU/WebRoot/WEB-INF/classes/");
		copy.setContent(list);
		copy.excute();
	}

	

	private void getProjectFile() throws Exception {
		String server = "172.168.2.213";
		String user = "jccredit";
		String password = "jccredit";
		String remotePath = "/appfs/jccredit/webapp/jccs";
		String toPath = "E:/山西晋城银行/更新包/20140729_提前还款_jccs";
		AbstractCopyTemplete copy = new FTPByteCopyFiles(server, user, password, remotePath, toPath, content);
//		copy.setFileListCreater(new ClassFileListCreater(new String[][]{
//				{".java", ".class"}, 
//				{"/src/", "/WEB-INF/classes/"},
//				{"/WebRoot/", "/"}}));
		copy.excute();
	}

	
	private void getSQLFile() throws Exception {
		SelectToInsert sti = new SelectToInsert(new File(selectSqlFile), dataSource);
		sti.setInsertFile(new File(insertSqlFile));
		sti.setSort(true);
		sti.excute();
	}

}
