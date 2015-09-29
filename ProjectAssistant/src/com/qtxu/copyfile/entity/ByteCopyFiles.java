package com.qtxu.copyfile.entity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import com.qtxu.copyfile.inter.AbstractCopyTemplete;
import com.qtxu.copyfile.inter.FileListCreater;

public class ByteCopyFiles extends AbstractCopyTemplete {

	public ByteCopyFiles(String fromPath, String toPath){
		super(fromPath, toPath);
	}
	
	/**
	 * 构造方法
	 * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 * @param content	包含拷贝文件的父路径以下的路径（含文件名称）的文件
	 */
	public ByteCopyFiles(String fromPath, String toPath, File content) {
		super(fromPath, toPath, content);
	}
	
	/**
	 * 构造方法
	  * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 * @param content	包含拷贝文件的父路径以下的路径（含文件名称）的文件
	 * @param filter	要拷贝的文件的格式,eg. .jsp;.java;
	 */
	public ByteCopyFiles(String fromPath, String toPath, File content, String filter) {
		super(fromPath, toPath, content, filter);
	}
	
	/**
	 * 构造方法
	  * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 * @param content	包含拷贝文件的父路径以下的路径（含文件名称）的文件
	 * @param filter	文件列表生成器，以content为源，设置过滤条件并返回过滤后的需拷贝的文件
	 */
	public ByteCopyFiles(String fromPath, String toPath, File content, FileListCreater filter) {
		super(fromPath, toPath, content, filter);
	}
	@Override
	public void copyFiles(){
		File fromFile = null, toFile = null;
		Collection<String> contents = super.getContents();
		if(contents == null){
			System.out.println("contents is null ! ");
			System.exit(0);
		}
		if(contents.size() == 0)
			return ;
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		byte[] b = new byte[1024];
		int len = 0;
		for(String temp : contents){
			fromFile = new File(getFromPath() + temp);
			toFile = new File(getToPath() + temp);
			if(!fromFile.exists()){
				addFailFile(temp);
				continue;
			}
			try {
				if(!toFile.exists()){
					new File(toFile.getParent()).mkdirs();
					toFile.createNewFile();
				}
				bis = new BufferedInputStream(new FileInputStream(fromFile));
				bos = new BufferedOutputStream(new FileOutputStream(toFile));
				while((len = bis.read(b)) > 0){
					bos.write(b, 0, len);
				}
				addSuccessFile(temp);
			} catch (IOException e) {
				addFailFile(temp);
			}finally{
				try {
					if(bos != null) bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(bis != null) bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
