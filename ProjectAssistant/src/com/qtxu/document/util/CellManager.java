package com.qtxu.document.util;

import org.apache.poi.ss.usermodel.Cell;

import com.qtxu.document.dao.ExcelDocumentColumn;

public class CellManager {
	/**
	 * 对cell的数据进行转换
	 * @param cell
	 * @param className
	 * @param object
	 */
	public static void setCellData(Cell cell, String className, Object object) {
		// TODO Auto-generated method stub
		//setCellType(cell, className);
		if(object == null){
			cell.setCellValue("");
		}else if(ClassType.STRING.equals(className)){
			cell.setCellValue(object.toString());
		}else if(ClassType.INT.equals(className)){
			cell.setCellValue(Integer.parseInt(object.toString()));
		}else if(ClassType.DOUBLE.equals(className)){
			cell.setCellValue(Double.parseDouble(object.toString()));
		}else if(ClassType.BIGDECIMAL.equals(className)){
			cell.setCellValue(Double.parseDouble(object.toString()));
		}else{
			cell.setCellValue(object.toString());
		}
	}
	/**
	 * 
	 * @param cell
	 * @param className
	 */
	public static void setCellType(Cell cell, String className){
		if(ClassType.STRING.equals(className)){
			cell.setCellType(1);
		}else if(ClassType.INT.equals(className)){
			cell.setCellType(0);
		}else if(ClassType.DOUBLE.equals(className)){
			cell.setCellType(0);
		}else if(ClassType.BIGDECIMAL.equals(className)){
			cell.setCellType(0);
		}
	}
	
}
