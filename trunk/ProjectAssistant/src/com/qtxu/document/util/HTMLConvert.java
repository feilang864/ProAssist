package com.qtxu.document.util;

public class HTMLConvert {
	public static String[][] charArr = new String[][]{
		{"&quot;", "\""},
		{"&lt;", "<"},
		{"&gt;", ">"},
	};
	
	public static String replaceAllByHTMLChar(String str){
		for(int i = 0; i < charArr.length; i ++){
			str = str.replaceAll(charArr[i][0], charArr[i][1]);
		}
		return str;
	}
}
