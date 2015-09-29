package com.qtxu.copyfile.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileUtil {
	public static final String DIR = "Dir";
	public static final String FILE = "File";
	private FileUtil(){
		
	}
	
	public static void deleteAllEmptyDir(String parentPath){
		deleteAllEmptyDir(new File(parentPath));
	}
	
	public static void deleteAllEmptyDir(File fileParent){
		if(fileParent.isFile())
			return ;
		fileParent.isDirectory();
		File[] files = fileParent.listFiles();
		if(files.length == 0)
			fileParent.deleteOnExit();
		for(File file : fileParent.listFiles()){
			deleteAllEmptyDir(file);
		}
	}
	
	public static void createNewFileAndDir(File file, String fileType) throws Exception{
		if(!file.exists()){
			createNewFileAndDir(file.getParentFile(), DIR);
			if(DIR.equals(fileType)){
				file.mkdirs();
			}else if(FILE.equals(fileType)){
				file.createNewFile();
			}
		}
	}
	
	public static Collection<String> getInnerClassFile(String fromPath, Collection<String> contents) throws Exception{
		if(contents == null || contents.size() == 0){
			return contents;
		}
		String fileName = null;
		ArrayList<String> tempContents = new ArrayList<String>();
		Map<String, ArrayList<String>> fileMap = new HashMap<String, ArrayList<String>>();
		try {
			for(String tempFileName : (ArrayList<String>)contents){
				fileName = tempFileName;
				if(fileName.endsWith(".class")){
					String key = fromPath + fileName.substring(0, fileName.lastIndexOf("/"));
					if(fileMap.get(key) == null){
						ArrayList<String> value = new ArrayList<String>();
						for(File keyFile : new File(key).listFiles()){
							if(keyFile.getName().indexOf("$") > -1){
								value.add(keyFile.getName().substring(keyFile.getName().lastIndexOf("/") + 1));
							}
						}
						fileMap.put(key, value);
					}
					ArrayList<String> value = fileMap.get(key);
					if(value.size() == 0)
						continue;
					for(String samePathFileName : value){
						if(samePathFileName.startsWith(fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf(".")) + "$")){
							tempContents.add(key.substring(fromPath.length()) + "/" + samePathFileName);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("获取内部类文件出错"+fileName);
		}
		if(tempContents.size() > 0){
			for(String tempFileName : tempContents){
				contents.add(tempFileName);
			}
		}
		return contents;
	}
	
	public static void getFilePath(String file, Set<String> set){
		getFilePath(new File(file), new String[]{}, true, set);
	}
	
	public static void getFilePath(File file, Set<String> set){
		getFilePath(file, new String[]{}, true, set);
	}
	public static void getFilePath(File file, String[] filetype, boolean isFileType, Set<String> set) {
		
		for(File file0 : file.listFiles()){
			if(file0.isDirectory()){
				getFilePath(file0, filetype, isFileType, set);
			}else{
				if((filetype.length > 0 && isFiletype(file0.getName(), filetype, isFileType))){
					set.add(file0.getPath().replaceAll("\\\\", "/"));
				}else if(filetype.length == 0){
					set.add(file0.getPath().replaceAll("\\\\", "/"));
				}
			}
		}
	}
	
	private static boolean isFiletype(String fileName, String[] fileType, boolean isFileType){
		boolean flag = false;
		for(String temp : fileType){
			if(fileName.endsWith(temp)){
				flag = true;
			}
		}
		return isFileType?flag:!flag;
	}
	
	public static String replaceAll(String str, String[][] replaceArr) {
		// TODO Auto-generated method stub
		if(replaceArr == null || replaceArr.length == 0)
			return str;
		for(int i = 0; i < replaceArr.length; i ++){
			str = str.replaceAll(replaceArr[i][0], replaceArr[i][1]);
		}
		return str;
	}
}
