package com.qtxu.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class FileToInsert {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String file = "E:\\学习资料\\99_Amarsoft\\Customer\\河北廊坊银行\\批量2015\\F_PT_PER_KCFB_CFDSJC_20150711.dat\\F_PT_PER_KCFB_CFDSJC_20150711.dat";
		String filename = "INSERT_20150711";
		String filekk = ".sql";
		String sqlfile = "E:\\学习资料\\99_Amarsoft\\Customer\\河北廊坊银行\\批量2015\\F_PT_PER_KCFB_CFDSJC_20150711.dat\\";
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		BufferedWriter bw = null;
		String temp = "";
		String[] splitTemp = new String[]{};
		Map<String, String> map = new HashMap<String, String>();
		int i = 0;
		while((temp = br.readLine()) != null){
			try {
//				if(i++%5000 == 1){
//					if(bw != null) bw.close();
//					bw = new BufferedWriter(new FileWriter(new File(sqlfile + filename + (i/5000) + filekk)));
//				}
//				if(temp.trim().length() > 0){
//					splitTemp = temp.split("@");
//					bw.write("insert into customer_info_hx (certtype, certid, mfcustomerid, customerid) values ('"+splitTemp[0]+"','"+splitTemp[1]+"','"+splitTemp[2]+"','');");
//					bw.newLine();
//					
//				}
				if(temp.trim().length() > 0){
					splitTemp = temp.split("@");
					map.put(splitTemp[1].trim(), splitTemp[2].trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(temp);
			}
		}
		bw.close();
		br.close();
	}

}
