package com.qtxu.copyfile.inter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.qtxu.copyfile.utils.FileUtil;


public abstract class AbstractCopyTemplete {
	protected String fromPath;
	protected String toPath;
	protected File content;
	private Collection<String> contents = new ArrayList<String>();
	private Collection<String> copySuccessFile = new TreeSet<String>();
	private Collection<String> copyFailFile = new TreeSet<String>();
	private String filter;
	private FileListCreater fileListCreater = null;
	/**
	 * ���췽��
	 * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 */
	public AbstractCopyTemplete(String fromPath, String toPath) {
		this.fromPath = fromPath;
		this.toPath = toPath;
	}
	
	/**
	 * ���췽��
	 * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 * @param content	���������ļ��ĸ�·�����µ�·�������ļ����ƣ����ļ�
	 */
	public AbstractCopyTemplete(String fromPath, String toPath, File content) {
		this.fromPath = fromPath;
		this.toPath = toPath;
		this.content = content;
	}
	
	/**
	 * ���췽��
	  * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 * @param content	���������ļ��ĸ�·�����µ�·�������ļ����ƣ����ļ�
	 * @param filter	Ҫ�������ļ��ĸ�ʽ,eg. .jsp;.java;
	 */
	public AbstractCopyTemplete(String fromPath, String toPath, File content, String filter) {
		this(fromPath, toPath, content);
		this.setFilter(filter);
	}
	
	/**
	 * ���췽��
	  * @param fromPath	�追���ļ��ĸ�·��
	 * @param toPath	Ҫ��������·��
	 * @param content	���������ļ��ĸ�·�����µ�·�������ļ����ƣ����ļ�
	 * @param fileListCreater	�ļ��б�����������contentΪԴ�����ù������������ع��˺���追�����ļ�
	 */
	public AbstractCopyTemplete(String fromPath, String toPath, File content, FileListCreater fileListCreater) {
		// TODO Auto-generated constructor stub
		this(fromPath, toPath, content);
		this.setFileListCreater(fileListCreater);
	}

	/**
	 * ģ�淽�����
	 * @throws Exception 
	 */
	public void excute() throws Exception{
		parseConent();
		preparePath();
		copyFiles();
		print();
	}
	
	/**
	 * �ļ���������ʽ��������д
	 */
	public abstract void copyFiles();

	/**
	 * ��ӿ����ɹ����ļ�
	 * @param file
	 */
	protected void addSuccessFile(String file){
		copySuccessFile.add(file);
	}
	
	/**
	 * ��ӿ���ʧ�ܵ��ļ�
	 * @param file
	 */
	protected void addFailFile(String file){
		copyFailFile.add(file);
	}
	
	/**
	 * ��ʼ���������ļ�����
	 */
	public void parseConent() {
		if(contents.size() == 0)
			createFileList();
		if(fileListCreater != null){
			contents = fileListCreater.createFileList(contents);
		}
		try {
			//contents = FileUtil.getInnerClassFile(fromPath, contents);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * Ĭ�Ͽ����ļ��б�����
	 * @throws ExceptionInInitializerError
	 */
	private void createFileList() throws ExceptionInInitializerError {
		try {
			BufferedReader br = new BufferedReader(new FileReader(content));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				temp = temp.trim();
				if (temp.length() > 0) {
					if(temp.startsWith("#"))
						continue;
					if(filter == null || filter.length() == 0){
						contents.add(temp);
					}else if(temp.lastIndexOf(".") > 0 && filter.indexOf(temp.substring(temp.lastIndexOf("."))+";") >= 0){
						contents.add(temp);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError("�����ļ�û�ҵ���");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError("��ȡ�����ļ�ʧ�ܣ�");
		}
	}
	
	/**
	 * ��ʼ��Ŀ��·��
	 * @throws Exception 
	 */
	public void preparePath() throws Exception {
		File fromFile = new File(fromPath);
		if (!fromFile.exists()) {
			//throw new Exception("����·�������ڣ�");
		}
		File toFile = new File(toPath);
		if (!toFile.exists()) {
			toFile.mkdirs();
		}
	}
	
	/**
	 * ��ӡ��ʽ
	 */
	protected void print() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append("���ļ�����").append(getContents().size()).append("\n");
		getStringInfo(sb, getContents());
		sb.append("�����ɹ��ļ�����").append(getCopySuccessFile().size()).append("\n");
		getStringInfo(sb, getCopySuccessFile());
		sb.append("����ʧ���ļ�����").append(getCopyFailFile().size()).append("\n");
		getStringInfo(sb, getCopyFailFile());
		System.out.println(sb.toString());
	}
	
	private void getStringInfo(StringBuffer sb, Collection<String> coll){
		for(String content : coll){
			sb.append("\t").append(content).append("\n");
		}
	}
	
	
	
	public File getContent() {
		return content;
	}

	public String getFromPath() {
		return fromPath;
	}

	public String getToPath() {
		return toPath;
	}

	public String getFilter() {
		return filter;
	}

	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

	public void setToPath(String toPath) {
		this.toPath = toPath;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Collection<String> getContents() {
		return contents;
	}

	public void setContents(Collection<String> contents) {
		this.contents = contents;
	}

	public void setContent(File content) {
		this.content = content;
	}

	public Collection<String> getCopySuccessFile() {
		return copySuccessFile;
	}

	public void setCopySuccessFile(Collection<String> copySuccessFile) {
		this.copySuccessFile = copySuccessFile;
	}

	public Collection<String> getCopyFailFile() {
		return copyFailFile;
	}

	public void setCopyFailFile(Collection<String> copyFailFile) {
		this.copyFailFile = copyFailFile;
	}

	public FileListCreater getFileListCreater() {
		return fileListCreater;
	}

	public void setFileListCreater(FileListCreater fileListCreater) {
		this.fileListCreater = fileListCreater;
	}

	public void setContent(Collection<String> coll) {
		// TODO Auto-generated method stub
		this.contents = coll;
	}
}
