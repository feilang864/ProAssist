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
	 * ���췽��
	 * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 * @param content	���������ļ��ĸ�·�����µ�·�������ļ����ƣ����ļ�
	 */
	public ByteCopyFiles(String fromPath, String toPath, File content) {
		super(fromPath, toPath, content);
	}
	
	/**
	 * ���췽��
	  * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 * @param content	���������ļ��ĸ�·�����µ�·�������ļ����ƣ����ļ�
	 * @param filter	Ҫ�������ļ��ĸ�ʽ,eg. .jsp;.java;
	 */
	public ByteCopyFiles(String fromPath, String toPath, File content, String filter) {
		super(fromPath, toPath, content, filter);
	}
	
	/**
	 * ���췽��
	  * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 * @param content	���������ļ��ĸ�·�����µ�·�������ļ����ƣ����ļ�
	 * @param filter	�ļ��б�����������contentΪԴ�����ù������������ع��˺���追�����ļ�
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
