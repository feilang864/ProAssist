package com.qtxu.excel.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qtxu.document.dao.ExcelDocumentColumn;
import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.excel.headler.ValueHeadler;

public class ExcelValueManager {
	public static Logger logger = Logger.getLogger(ExcelValueManager.class);
	public static void action(ExcelDocumentSheet edSheet, SheetConfig sc) {
		Map<String, ExcelDocumentColumn> map = edSheet.getColumns();
		ColumnConfig cc = null;
		LinkedList ll = null;
		for(Iterator<String> keyIt = map.keySet().iterator(); keyIt.hasNext();){
			String key = keyIt.next();
			cc = sc.getColumnByDBColumn(key);
			ll = map.get(key).getData();
			replaceAllEasyValue(cc, ll);
			replaceAllHardValue(cc, edSheet);
		}
		
	}

	private static void replaceAllHardValue(ColumnConfig cc, ExcelDocumentSheet edSheet) {
		// TODO Auto-generated method stub
		String classValue = cc.getClassValue();
		if(null != classValue){
			ValueHeadler vh = null;
			try {
				vh = (ValueHeadler) Class.forName(classValue).newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("∑¥±‡“Î£∫" + classValue + "\t“Ï≥££°");
				System.exit(0);
			} 
			vh.setEDSheet(edSheet);
			vh.setColumnConfig(cc);
			vh.excute();
		}
	}

	private static void replaceAllEasyValue(ColumnConfig cc, LinkedList ll) {
		// TODO Auto-generated method stub
		replaceDefaultValue(cc, ll);
		replaceValueMap(cc, ll);
	}

	private static void replaceValueMap(ColumnConfig cc, LinkedList ll) {
		// TODO Auto-generated method stub
		String valueMapStr = cc.getValueMap();
		
		if(null != valueMapStr){
			String[] strArr = valueMapStr.split(";");
			Map<String, String> valueMap = new HashMap<String, String>();
			for(int i = 0; i < strArr.length; i ++){
				int index = strArr[i].indexOf(":");
				valueMap.put(strArr[i].substring(0, index).trim(), strArr[i].substring(index + 1).trim());
			}
			for(int index = (ll.size() - 1); index >= 0; index --){
				String srcValue = (String) ll.get(index);
				String toValue = valueMap.get(srcValue);
				if(toValue != null)
					ll.set(index, toValue);
			}
		}
	}

	private static void replaceDefaultValue(ColumnConfig cc, LinkedList ll) {
		// TODO Auto-generated method stub
		String defaultVale = cc.getDefaultValue();
		if(null != defaultVale){
			System.out.println(ll.size());
			for(int index = (ll.size() - 1); index >= 0; index --){
				String value = (String) ll.get(index);
				if(value == null || value.trim().length() == 0)
					ll.set(index, defaultVale);
			}
		}
	}

}
