import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.qtxu.db.util.ConnectionManager;


public class SortNoCreater {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = ConnectionManager.getInstance().getConnection("cedb");
		SortNoCreater snc = new SortNoCreater();
		Map<String, OrgInfo> orgMap = snc.getOrgInfo(conn);
		int i = 0;
		int level = OrgInfo.getOrgLevelList().get(i);
		Map<String, OrgInfo> orgMap2 = getLevelOrgMapRoot(level, orgMap);
		System.out.println(orgMap2.size());
		for(Iterator<String> it = orgMap2.keySet().iterator(); it.hasNext();){
			OrgInfo orgInfo = orgMap2.get(it.next());
			getLevelOrgMap((i+1), orgMap, orgInfo);
		}
		insertDB(conn, orgMap);
		conn.close();
	}


	private static void insertDB(Connection conn, Map<String, OrgInfo> orgMap)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement("insert into testaa(orgid,sortNo,orgName,orgPath ) values ( ?,?,?,?)");
		int j = 0;
		for(Iterator<String> it = orgMap.keySet().iterator(); it.hasNext();){
			OrgInfo oi = orgMap.get(it.next());
			ps.setString(1, oi.getOrgId());
			ps.setString(2, oi.getSortNo());
			ps.setString(3, oi.getOrgName());
			ps.setString(4, oi.getOrgPath());
			ps.addBatch();
			j ++;
			if(j == 10){
				ps.executeBatch();
				ps.clearBatch();
				j = 0;
			}
			
		}
		ps.executeBatch();
		ps.close();
	}
	
	
	private static void getLevelOrgMap(int i, Map<String, OrgInfo> orgMap, OrgInfo orgInfo) {
		if(i == OrgInfo.getOrgLevelList().size())
			return;
		int level = (OrgInfo.getOrgLevelList()).get(i);
		int index = 1;
		for(Iterator<String> it = orgMap.keySet().iterator(); it.hasNext();){
			OrgInfo subOrgInfo = orgMap.get(it.next());
			if(subOrgInfo.getOrgLevel() == level && (orgInfo != null && subOrgInfo.getOrgPath().startsWith(orgInfo.getOrgPath()))){
				subOrgInfo.setSortNo(orgInfo.getSortNo() + (index ++));
				getLevelOrgMap(i + 1, orgMap, subOrgInfo);
			}
		}
	}
	private static Map<String, OrgInfo> getLevelOrgMapRoot(int level, Map<String, OrgInfo> orgMap) {
		Map<String, OrgInfo> retMap = new HashMap<String, OrgInfo>();
		int index = 1;
		for(Iterator<String> it = orgMap.keySet().iterator(); it.hasNext();){
			OrgInfo subOrgInfo = orgMap.get(it.next());
			if(subOrgInfo.getOrgLevel() == level ){
				retMap.put(subOrgInfo.getOrgId(), subOrgInfo);
				subOrgInfo.setSortNo("" + (index ++));
			}
		}
		return retMap;
	}

	private Map<String, OrgInfo> getOrgInfo(Connection conn) throws Exception{
		String sql = "select decode(t1.department_id, null, t2.area_id, t1.department_id) as 部门ID,decode(t1.department_name, null, t2.area_name, t1.department_name) as 所在部门,substr(sys_connect_by_path(decode(t1.department_name, null, t2.area_name, t1.department_name), '/'), 2) as 部门Path from SMP_company_group t left join SMP_department t1 on t.department_id = t1.department_id left join SMP_area t2 on t.area_id = t2.area_id start with t.department_id = 11011251 connect by prior t.id = t.parent_id";
		Map<String, OrgInfo> orgMap = new HashMap<String, OrgInfo>(); 
		Statement state = conn.createStatement();
		
		ResultSet rs = state.executeQuery(sql);
		
		while(rs.next()){
			OrgInfo oi = new OrgInfo();
			oi.setOrgId(rs.getString(1));
			oi.setOrgName(rs.getString(2));
			oi.setOrgPath(rs.getString(3));
			if(orgMap.containsKey(oi.getOrgId())){
				throw new Exception("机构重复");
			}
			orgMap.put(oi.getOrgId(), oi);
		}
		rs.close();
		state.close();
		return orgMap;
	}
	
}
