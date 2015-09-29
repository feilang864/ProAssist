package com.qtxu.copyfile.inter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class ReadLineCopyFiles extends AbstractCopyTemplete {

	public ReadLineCopyFiles(String fromPath, String toPath, File content) {
		super(fromPath, toPath, content);
		// TODO Auto-generated constructor stub
	}

	public ReadLineCopyFiles(String fromPath, String toPath, File content,
			String filter) {
		super(fromPath, toPath, content, filter);
	}

	@Override
	public void copyFiles() {
		File fromFile = null, toFile = null;
		Collection<String> contents = super.getContents();
		if(contents.size() == 0)
			return ;
		BufferedReader br = null;
		BufferedWriter bw = null;
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
				br = new BufferedReader(new FileReader(fromFile));
				bw = new BufferedWriter(new FileWriter(toFile));
				while((temp = br.readLine()) == null){
					bw.write(temp);
				}
				addSuccessFile(temp);
			} catch (IOException e) {
				addFailFile(temp);
			} finally{
				try {
					if(br != null)	br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
				try {
					if(bw != null) bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public void print() {

	}

}
