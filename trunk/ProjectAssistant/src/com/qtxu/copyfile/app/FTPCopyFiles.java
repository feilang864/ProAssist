package com.qtxu.copyfile.app;

import java.io.File;

import com.qtxu.copyfile.inter.AbstractCopyTemplete;
import com.qtxu.copyfile.inter.FTPByteCopyFiles;

public class FTPCopyFiles {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String server = "172.168.2.213";
		String user = "jccredit";
		String password = "jccredit";
		String remotePath = "/appfs/jccredit/webapp";
		String toPath = "E:/ɽ����������/���°�";
		File content = new File("filelist.txt");
		AbstractCopyTemplete copy = new FTPByteCopyFiles(server, user, password, remotePath, toPath, content);
		copy.excute();
	}
}
