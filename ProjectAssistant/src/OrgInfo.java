import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class OrgInfo {
	private static Set<Integer> set = new TreeSet<Integer>();
	private static List<Integer> list = null;
	private String orgId;
	private String orgName;
	private String sortNo;
	private String orgPath;
	private int orgLevel;
	
	public static Set<Integer> getOrgLevelSet(){
		return set;
	}
	public static List<Integer> getOrgLevelList(){
		if(list == null){
			list = new ArrayList();
			for(Iterator<Integer> it = set.iterator(); it.hasNext(); ){
				list.add(it.next());
			}
		}
		return list;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSortNo() {
		return sortNo;
	}
	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	public String getOrgPath() {
		return orgPath;
	}
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
		setOrgLevel((orgPath.split("/")).length);
		set.add((orgPath.split("/")).length);
	}
	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}
	public int getOrgLevel() {
		return orgLevel;
	}
	public String toString(){
		return this.orgId + "\t" + this.sortNo + "\t" + this.orgName + "\t" + this.orgPath;
	}
}
