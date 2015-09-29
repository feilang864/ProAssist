package com.qtxu.db.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.digester3.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtxu.db.dao.DataSource;

public class ConnectionManager {
	public static ConnectionManager connManager = null;
	private HashMap<String, DataSource> dataSources = new HashMap<String, DataSource>();
	private HashMap<String, Connection> conns = new HashMap<String, Connection>();
	private Log logger = LogFactory.getLog(ConnectionManager.class);
	private ConnectionManager() throws Exception {
		parseDataSource("etc/datasource.xml");
		registerDriver();
		//setConnections();
	}

	private void parseDataSource(String file) throws Exception {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.push(this);
		digester.addCallMethod("datasources/datasource", "addDataSource", 6);

		digester.addCallParam("datasources/datasource/name", 0);
		digester.addCallParam("datasources/datasource/driver", 1);
		digester.addCallParam("datasources/datasource/url", 2);
		digester.addCallParam("datasources/datasource/username", 3);
		digester.addCallParam("datasources/datasource/password", 4);
		digester.addCallParam("datasources/datasource/internallogon", 5);
		digester.parse(new File(file));
	}

	public static ConnectionManager getInstance() throws Exception {
		if (connManager == null) {
			connManager = new ConnectionManager();
		}
		return connManager;
	}

	public void addDataSource(String name, String driver, String url,
			String username, String password, String internallogon) {
		DataSource dataSource = new DataSource();
		dataSource.setName(name);
		dataSource.setDriver(driver);
		dataSource.setURL(url);
		dataSource.setUserName(username);
		dataSource.setPassword(password);
		dataSource.setInternallogon(internallogon);
		addDataSource(dataSource);
	}

	private void addDataSource(DataSource dataSource) {
		dataSources.put(dataSource.getName(), dataSource);
	}

	private void setConnections(String dsName) {
		addConnection(dataSources.get(dsName));
	}

	private void addConnection(DataSource dataSource) {
		conns.put(dataSource.getName(), getConnection(dataSource));
	}
	
	public Connection getConnection(String name){
		Connection conn = conns.get(name);
		if(conn == null){
			setConnections(name);
		}
		return conns.get(name);
	}
	
	private Connection getConnection(DataSource dataSource) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dataSource.getURL(),
					dataSource.getProperties());
		} catch (SQLException e) {
			logger.error("数据源<" + dataSource.getName() + ">不能建立连接，请检查数据源。。。。" + e);
			System.exit(0);
		}
		return conn;
	}

	public HashMap<String, DataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(HashMap<String, DataSource> dataSources) {
		this.dataSources = dataSources;
	}
	
	public void close(){
		if(connManager != null){
			for(Iterator<String> it = conns.keySet().iterator(); it.hasNext();){
				try {
					conns.get(it.next()).close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connManager = null;
	}
	public void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.info("关闭ResultSet出错");
			logger.info(e.toString());
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.info("关闭Statement出错");
				logger.info(e.toString());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					logger.info("关闭Connection出错");
					logger.info(e.toString());
				}
			}
		}
	}
	
	public void free(ResultSet rs, PreparedStatement psmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.info("关闭ResultSet出错");
			logger.info(e.toString());
		} finally {
			try {
				if (psmt != null)
					psmt.close();
			} catch (SQLException e) {
				logger.info("关闭PreparedStatement出错");
				logger.info(e.toString());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					logger.info("关闭Connection出错");
					logger.info(e.toString());
				}
			}
		}
	}

	
	public void registerDriver() throws Exception{
		HashMap<String, DataSource> dataSources = getDataSources();
		for(Iterator<String> it = dataSources.keySet().iterator(); it.hasNext();){
			Class.forName(dataSources.get(it.next()).getDriver());
		}
	}
}
