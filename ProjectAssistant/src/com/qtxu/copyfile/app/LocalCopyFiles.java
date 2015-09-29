package com.qtxu.copyfile.app;

import java.io.File;

import com.qtxu.copyfile.entity.ByteCopyFiles;
import com.qtxu.copyfile.inter.AbstractCopyTemplete;
import com.qtxu.copyfile.inter.ClassFileListCreater;

public class LocalCopyFiles {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//String fromPath = "E:/Workspaces_10/HeBeiJincheng/ALS6TEST";
		String fromPath = "E:/Workspaces_10/HeBeiJincheng/ALS6JC_ZONGHEEDU";
		String toPath = "E:/山西晋城银行/更新包/ALS6JC";
		File content = new File("filelist.txt");
		AbstractCopyTemplete copy = new ByteCopyFiles(fromPath, toPath, content);
		copy.setFileListCreater(new ClassFileListCreater());
		copy.excute();
	}
}
