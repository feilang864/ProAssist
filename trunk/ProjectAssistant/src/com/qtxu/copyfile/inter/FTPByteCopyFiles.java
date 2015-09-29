package com.qtxu.copyfile.inter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Collection;

import org.apache.commons.net.ftp.FTPClient;

import com.qtxu.copyfile.utils.FileUtil;

public class FTPByteCopyFiles extends AbstractCopyTemplete {
	private FTPClient ftpClient = new FTPClient();
	public FTPByteCopyFiles(String server, String user, String password, String remotePath, String toPath, File content) {
		super(remotePath, toPath, content);
		initFTPClient(server, user, password);
	}

	public FTPByteCopyFiles(String server, String user, String password, String remotePath, String toPath, File content, String filter) {
		super(remotePath, toPath, content, filter);
		initFTPClient(server, user, password);
	}
	
	private void initFTPClient(String server, String user, String password){
		try {
			ftpClient.connect(server);
			ftpClient.login(user, password);
			//String remoteFileName = "/home/als6/ALS6.ear/ALS6.war/WEB-INF/web.xml";
			//fos = new FileOutputStream("web.xml");
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		//ftpClient.retrieveFile(remoteFileName, fos);
	}
	@Override
	public void copyFiles(){
		String remoteFileName = null;
		File toFile = null;
		Collection<String> contents = super.getContents();
		if(contents.size() == 0)
			return ;
		FileOutputStream fos = null;

		for(String temp : contents){
			remoteFileName = fromPath + temp;
			toFile = new File(toPath + temp);
			try {
				if(!toFile.exists()){
					new File(toFile.getParent()).mkdirs();
					//toFile.createNewFile();
				}
				fos = new FileOutputStream(toFile);
				
				if(ftpClient.retrieveFile(remoteFileName, fos)){
					addSuccessFile(temp);
				}else {
					addFailFile(temp);
					toFile.deleteOnExit();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				addFailFile(temp);
			} finally {
				try {
					if(fos != null ) fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileUtil.deleteAllEmptyDir(toFile);
	}

	
}
