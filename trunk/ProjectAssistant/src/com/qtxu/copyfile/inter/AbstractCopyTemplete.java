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
	 * 构造方法
	 * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 */
	public AbstractCopyTemplete(String fromPath, String toPath) {
		this.fromPath = fromPath;
		this.toPath = toPath;
	}
	
	/**
	 * 构造方法
	 * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 * @param content	包含拷贝文件的父路径以下的路径（含文件名称）的文件
	 */
	public AbstractCopyTemplete(String fromPath, String toPath, File content) {
		this.fromPath = fromPath;
		this.toPath = toPath;
		this.content = content;
	}
	
	/**
	 * 构造方法
	  * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 * @param content	包含拷贝文件的父路径以下的路径（含文件名称）的文件
	 * @param filter	要拷贝的文件的格式,eg. .jsp;.java;
	 */
	public AbstractCopyTemplete(String fromPath, String toPath, File content, String filter) {
		this(fromPath, toPath, content);
		this.setFilter(filter);
	}
	
	/**
	 * 构造方法
	  * @param fromPath	需拷贝文件的父路径
	 * @param toPath	要拷贝到的路径
	 * @param content	包含拷贝文件的父路径以下的路径（含文件名称）的文件
	 * @param fileListCreater	文件列表生成器，以content为源，设置过滤条件并返回过滤后的需拷贝的文件
	 */
	public AbstractCopyTemplete(String fromPath, String toPath, File content, FileListCreater fileListCreater) {
		// TODO Auto-generated constructor stub
		this(fromPath, toPath, content);
		this.setFileListCreater(fileListCreater);
	}

	/**
	 * 模版方法入口
	 * @throws Exception 
	 */
	public void excute() throws Exception{
		parseConent();
		preparePath();
		copyFiles();
		print();
	}
	
	/**
	 * 文件拷贝的形式，子类重写
	 */
	public abstract void copyFiles();

	/**
	 * 添加拷贝成功的文件
	 * @param file
	 */
	protected void addSuccessFile(String file){
		copySuccessFile.add(file);
	}
	
	/**
	 * 添加拷贝失败的文件
	 * @param file
	 */
	protected void addFailFile(String file){
		copyFailFile.add(file);
	}
	
	/**
	 * 初始化拷贝的文件集合
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
	 * 默认拷贝文件列表生成
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
			throw new ExceptionInInitializerError("内容文件没找到！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError("读取内容文件失败！");
		}
	}
	
	/**
	 * 初始化目标路径
	 * @throws Exception 
	 */
	public void preparePath() throws Exception {
		File fromFile = new File(fromPath);
		if (!fromFile.exists()) {
			//throw new Exception("拷贝路径不存在！");
		}
		File toFile = new File(toPath);
		if (!toFile.exists()) {
			toFile.mkdirs();
		}
	}
	
	/**
	 * 打印形式
	 */
	protected void print() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append("总文件数：").append(getContents().size()).append("\n");
		getStringInfo(sb, getContents());
		sb.append("拷贝成功文件数：").append(getCopySuccessFile().size()).append("\n");
		getStringInfo(sb, getCopySuccessFile());
		sb.append("拷贝失败文件数：").append(getCopyFailFile().size()).append("\n");
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
