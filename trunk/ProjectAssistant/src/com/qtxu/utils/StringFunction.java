package com.qtxu.utils;

public class StringFunction {
	private StringFunction(){
		
	}
	
	public static int compareToIgnoreCase(String a, String b){
		return a.replaceAll(" ", "").compareToIgnoreCase(a.replaceAll(" ", ""));
	}
	
	public static void main(String[] args){
		String a = "select * from DataObject_Library where DoNo='ApplyInfo0420' and ColIndex='0097';";
		String b = "selEct * from DataObject_Library  where DoNo='ApplyInfo0420' and ColIndex='0097';";
		System.out.println(StringFunction.compareToIgnoreCase(a, b));
	}
}
