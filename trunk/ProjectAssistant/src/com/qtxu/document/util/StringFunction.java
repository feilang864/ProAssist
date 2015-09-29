package com.qtxu.document.util;

public final class StringFunction {
	private StringFunction(){
		
	}
	
	/**
	 * ��ȡ���ݿ��ִ�е�SQL���
	 * @param srcSql
	 * @return
	 */
	public static String getDBSql(String srcSql){
		if(srcSql == null || (srcSql = srcSql.trim()).length() == 0 || srcSql.startsWith("--"))
			return "";
		return srcSql.endsWith(";")?srcSql.substring(0, srcSql.length() - 1):srcSql;
	}
}
