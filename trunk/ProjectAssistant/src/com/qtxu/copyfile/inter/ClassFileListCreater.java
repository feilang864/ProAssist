package com.qtxu.copyfile.inter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ClassFileListCreater implements FileListCreater {
	private String[][] replaceContent;
	public ClassFileListCreater(){
		this(new String[][]{{"/src/", "/WebRoot/WEB-INF/classes/"}, {".java", ".class"}});
	}
	public ClassFileListCreater(String[][] replaceContent){
		this.replaceContent = replaceContent;
	}
	
	public Collection<String> createFileList(Collection<String> contents) {
		// TODO Auto-generated method stub
		Collection<String> retContents = new ArrayList<String>();
		String temp = "";
		String srcTemp = "";
		if(this.replaceContent.length == 0){
			return contents;
		}else{
			for(Iterator<String> it = contents.iterator(); it.hasNext();){
				srcTemp = ((String)it.next()).trim();
				temp = srcTemp;
				for(int i = 0; i < this.replaceContent.length; i ++){
					temp = temp.replaceAll(replaceContent[i][0], replaceContent[i][1]);
				}
				if(temp == srcTemp){
					continue;
				}
				retContents.add(temp);
			}
		}
		for(String fileName : retContents){
			contents.add(fileName);
		}
		return contents;
	}

}
