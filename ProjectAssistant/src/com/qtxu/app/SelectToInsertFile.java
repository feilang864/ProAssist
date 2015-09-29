package com.qtxu.app;

import java.io.File;

import com.qtxu.db.excuter.SelectToInsert;

public class SelectToInsertFile {
	public static void main(String[] args) throws Exception{
//		String sql = "select * from dataobject_library where dono ='PutOutInfo104' order by colindex";
//		SelectToInsert sti = new SelectToInsert(sql, "36creditkf");
//		sti.setInsertFile(new File("36creditkf.sql"));
//		sti.excute();
//		sti = new SelectToInsert(sql, "36creditcs");
//		sti.setInsertFile(new File("36creditcs.sql"));
//		sti.excute();
//		sti = new SelectToInsert(sql, "jcals6jccs");
//		sti.setInsertFile(new File("36jcals6jccs.sql"));
//		sti.excute();
		//System.out.println("12");
		//jcals6jccs
		SelectToInsert sti = new SelectToInsert(new File("select.sql"), "testkf");
		sti.setInsertFile(new File("E://STORE_RELATION.sql")); 
		sti.setSort(true);
		sti.excute();
		
	}
}
