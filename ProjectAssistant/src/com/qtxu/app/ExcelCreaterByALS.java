package com.qtxu.app;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;

import com.qtxu.db.util.ConnectionManager;

public class ExcelCreaterByALS {
	private Connection conn = null;
	private ResultSet rs = null;

	public ExcelCreaterByALS() {
		try {
			conn = ConnectionManager.getInstance().getConnection("jcsc65");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExcelCreaterByALS ecba = new ExcelCreaterByALS();
		System.out.println(new Date());
		ecba.createResultSet();
		ecba.createExcel();
		System.out.println(new Date());

	}

	private void createResultSet() throws Exception {
		rs = conn.createStatement().executeQuery("select serialNo, termmonth, inputdate,bailratio,businesssum from business_apply where serialNo='BA20110831000010'");
	}

	public void createExcel() throws Exception {
		FileOutputStream fileOut = new FileOutputStream("D:/456.xls");
		// int colSize = DataObject.Columns.size();
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML leftmargin='0' topmargin='0'>");
		sb.append("<HEAD>");
		sb.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=gb_2312-80\">");
		sb.append("<STYLE>");
		sb.append(".inputstring {border-style:none;border-width:thin;border-color:#e9e9e9}");
		sb.append(".table {  border: solid; border-width: 0px 0px 1px 1px; border-color: #000000 black #000000 #000000}");
		sb.append(".td  {  font-family:\u5B8B\u4F53; font-size: 9pt; text-decoration: none; border-color: #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}");
		sb.append("td   {  font-family:\u5B8B\u4F53; font-size: 9pt; text-decoration: none; }");
		sb.append(".tdH {  font-family:\u5B8B\u4F53; font-size: 9pt; text-decoration: none; background-color:#B4B4B4; }");
		sb.append(".tdV {  font-family:\u5B8B\u4F53; font-size: 9pt; text-decoration: none; }");
		sb.append(".tdS {  font-family:\u5B8B\u4F53; font-size: 9pt; text-decoration: none; background-color:#CCCCCC; }");
		sb.append(".inputnumber {border-style:none;border-width:thin;border-color:#e9e9e9;text-align:right;}");
		sb.append(".pt16songud{font-family: '\u9ED1\u4F53','\u5B8B\u4F53';font-size: 16pt;font-weight:bold;text-decoration: none}");
		sb.append(".myfont{font-family: '\u9ED1\u4F53','\u5B8B\u4F53';font-size: 9pt;font-weight:bold;text-decoration: none}");
		sb.append("</STYLE>");
		sb.append("</HEAD>");
		sb.append("<BODY bgcolor='#DEDFCE' >    ");
		sb.append("<table align=center border=1 cellspacing=0 cellpadding=0 bgcolor=#E4E4E4 bordercolor=#999999 bordercolordark=#FFFFFF>");
        sb.append("<TBODY>");
        sb.append("<TR bgColor=#cccccc height=24>");
        sb.append("<TD nowrap class=tdH noWrap width=30 align=middle>\u5E8F\u53F7</TD>");
        //biaotou
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        
        for(int i = 0; i < columnCount;){
        	sb.append("<TD nowrap class=tdH align=middle >").append(rsmd.getColumnName(++i)).append("</TD>").toString();
        }
        sb.append("</TR>");
        fileOut.write(str2byte(sb.toString()));
        sb.delete(0, sb.length());
		//data
        int j = 0;
        String strValue = "";
        while(rs.next()){
        	sb.append("<tr>");
        	sb.append((new StringBuilder("<TD nowrap   bgcolor=#E4E4E4 noWrap align=right width=35 ><font style='font-size:9pt'>")).append(j + 1).append("</font></TD>").toString());
        	for(int i = 0; i < columnCount;){
        		strValue = rs.getString(++i);
        		if(strValue == null){
        			strValue = "";
        		}
        		System.out.println(rsmd.getColumnName(i) + "\t" + rsmd.getColumnClassName(i) + "\t" + rsmd.getColumnTypeName(i) + "\t" + rsmd.getScale(i) + "\t" + rsmd.getPrecision(i));
        	}
        	if((++j)%10 == 0){
        		fileOut.write(str2byte(sb.toString()));
                sb.delete(0, sb.length());
        	}
        	if(j % 1000 == 0){
        		System.out.println("已处理行数：" + j);
        	}
        	sb.append("</tr>");
        }
        sb.append("</TBODY>").append("</TABLE>").append("</BODY>").append("</HTML>");
        fileOut.write(str2byte(sb.toString()));
		fileOut.close();
	}

	public byte[] str2byte(String s) throws UnsupportedEncodingException {
		return s.getBytes("GBK");
	}

}
