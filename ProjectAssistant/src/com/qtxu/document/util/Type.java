package com.qtxu.document.util;

public enum Type {
	EXCEL_XLS(".xls"), EXCEL_XLSX(".xlsx");
	private String type;
	private Type(String type){
		this.setType(type);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static Type getFileType(String fileType){
		
		if(EXCEL_XLS.getType().equalsIgnoreCase(fileType)){
			return EXCEL_XLS;
		}else if(EXCEL_XLSX.getType().equalsIgnoreCase(fileType)){
			return EXCEL_XLSX;
		}
		return null;
	}
}
