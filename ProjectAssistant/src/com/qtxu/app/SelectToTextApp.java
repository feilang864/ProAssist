package com.qtxu.app;

import com.qtxu.db.excuter.SelectToText;

public class SelectToTextApp {
	public static void main(String[] args) throws Exception {
		
		
		String startTime = "2014-07-31/19:00:00";
		String endTime = "";
		String sql = "select FIRST_LOAD_TIME,sql_fulltext from v$sqlarea where 1=1 " + getTimeCondition(startTime, endTime) +" order by first_load_time";
		//String sql = " select * from r_sheet_item";
		SelectToText stt = new SelectToText("36czkf", sql);
		stt.setConsole(false);
		stt.setOutFile("jk.sql");
		stt.excute();
	}

	private static String getTimeCondition(String startTime, String endTime) {
		String startCond = "";
		String endCond = "";
		if(startTime.length() > 0){
			startCond = " and FIRST_LOAD_TIME > '" + startTime + "'";
		}
		if(endTime.length() > 0){
			endCond = " and FIRST_LOAD_TIME < '" + endTime + "'";
		}
		return startCond + endCond;
	}

}
