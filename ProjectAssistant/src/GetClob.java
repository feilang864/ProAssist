import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;

import com.qtxu.db.dao.SQLQuery;
import com.qtxu.db.util.ConnectionManager;


public class GetClob {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File tableList = new File("tablelistcss.sql");
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("ddlcss.sql")));
//		BufferedReader br = new BufferedReader(new FileReader(tableList));
		Connection conn = ConnectionManager.getInstance().getConnection("testcs");
		SQLQuery sqlQuery = new SQLQuery(conn);
		
		String sql = "select 'select dbms_metadata.get_ddl(''TABLE'','''||table_name||''',''TESTCS'') from dual' from user_tables order by table_name";
		ResultSet rs = sqlQuery.getResultSet(sql);
		ResultSet rs1 = null;
		while(rs.next()){
			sql = rs.getString(1);
			if(sql.trim().length() == 0)
				continue;
			try {
				rs1 = sqlQuery.getResultSet(sql);
				String detailinfo = "";
				if(rs1.next()){
					Clob clob = rs1.getClob(1);//java.sql.Clob
					if(clob != null){
						detailinfo = clob.getSubString((long)1,(int)clob.length());
					}
					bw.write(detailinfo);
					bw.newLine();
					bw.newLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {
				ConnectionManager.getInstance().free(rs1, rs1.getStatement(), null);
			}
			 
		}
		ConnectionManager.getInstance().free(rs, rs.getStatement(), null);
		
		
		
//		while((sql = br.readLine())!= null){
//			if(sql.trim().length() == 0)
//				continue;
//			rs = sqlQuery.getResultSet(sql);
//			String detailinfo = "";
//			if(rs.next()){
//				Clob clob = rs.getClob(1);//java.sql.Clob
//				if(clob != null){
//					detailinfo = clob.getSubString((long)1,(int)clob.length());
//				}
//				bw.write(detailinfo);
//				bw.newLine();
//				bw.newLine();
//			}
//			 ConnectionManager.getInstance().free(rs, rs.getStatement(), null);
//		}
		ConnectionManager.getInstance().free(rs, rs.getStatement(), conn);
//		br.close();
		bw.close();
		
	}

}
