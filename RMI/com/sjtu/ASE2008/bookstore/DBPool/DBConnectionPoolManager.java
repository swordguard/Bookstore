package com.sjtu.ASE2008.bookstore.DBPool;

import java.io.*;
import java.sql.*;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DBConnectionPoolManager {

	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private int maxConn = -1;
	private String configFile = "DatabaseServersConfig.xml";

	Connection conn = null;
	static private DBConnectionPoolManager dbman = null;

	public DBConnectionPoolManager() {
		// TODO Auto-generated constructor stub
		initParam();
	}
	
	public static DBConnectionPoolManager getInstance(){
		if(dbman == null){
			dbman = new DBConnectionPoolManager();
		}
		return dbman;
	}

	public Connection getConnection(){
		if(conn == null){


			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}

		return conn;
	}


	public void closeConnection(){
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void initParam(){
		File file = null;
		File dot = new File(".");
		try {
			file = new File(dot.getCanonicalFile() + File.separator + configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(configFile + " Not Found.");
			e.printStackTrace();
		}
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		List poolsList = root.elements("pool");
		for(Object o : poolsList){
			Element el = (Element)o;
			this.driver = el.element("driver").getText().trim();
			this.url = el.element("url").getTextTrim();
			this.username = el.element("username").getTextTrim();
			this.password = el.element("password").getTextTrim();
			this.maxConn = Integer.parseInt(el.element("maxconn").getTextTrim());
		}
	}

	
	public static void main(String args[]){
		DBConnectionPoolManager dbman = DBConnectionPoolManager.getInstance();
		System.out.println(dbman.url);
	}
	
	
}
