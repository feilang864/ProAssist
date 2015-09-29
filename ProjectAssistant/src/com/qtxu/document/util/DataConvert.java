package com.qtxu.document.util;

import java.io.Reader;
import java.sql.ResultSet;

public class DataConvert {
	private DataConvert(){
		
	}
	/**
	 * 
	 * @param obj
	 * @param className
	 * @return
	 */
	public static Object getDBValue(Object obj, String className){
		String retValue = "";
		
		return retValue;
	}
	
	public static String getSQLStringValue(Object obj, String className){
		String retValue = "";
		if(obj == null)
			return "NULL";
		if(ClassType.STRING.equals(className)){
			return "'" + obj.toString().trim().replaceAll("'", "''") + "'";
		}else if(ClassType.BIGDECIMAL.equals(className)){
			String str = obj.toString().trim();
			if(str.length() == 0){
				return "NULL";
			}
			return str.trim();
		}else if(ClassType.INT.equals(className)){
			String str = obj.toString().trim();
			if(str.length() == 0){
				return "NULL";
			}else if(str.indexOf(".") > 0){
				str = str.substring(0, str.indexOf("."));
			}
			return Integer.parseInt(str) + "";
		}else if(ClassType.CLOB_ORACLE.equals(className)){
			return obj.toString();
		}
		return retValue;
	}
	public static Object getDBColumnObject(ResultSet rs, int columnIndex, String columnClassName) throws Exception {
		// TODO Auto-generated method stub
		Object obj = null;
		if(columnClassName == null){
			obj = null;
		}else if(ClassType.CLOB_ORACLE.equals(columnClassName)){
			java.sql.Clob clob = rs.getClob(columnIndex);
			Reader inStream = clob.getCharacterStream();
			char[] c = new char[(int) clob.length()];
			inStream.read(c);
			obj = new String(c);
			inStream.close();
		}else{
			obj = rs.getObject(columnIndex);
		}
		return obj;
	}
	
}
