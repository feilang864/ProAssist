package com.qtxu.app;

import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;

import com.qtxu.copyfile.utils.FileUtil;

public class FilePathGetter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "D:/lfploan_YXD";
		TreeSet set = new TreeSet();
		//FileUtil.getFilePath(filePath, set);
		FileUtil.getFilePath(new File(filePath), set);
		for(Iterator it = set.iterator(); it.hasNext(); System.out.println(it.next().toString())){}
	}

}
