package com.cgtmse.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;




public class DBConnection extends Thread {

	static Connection con = null;
//	static String dbURL = "jdbc:oracle:thin:158.100.60.124:1521:CGINTRA"; //UAT
        static String dbURL = "jdbc:oracle:thin:@172.16.2.9:1521/CGINTRA_PROD.cgtmsedbvcnsubn.cgtmsedbvcn.oraclevcn.com";//PROD

	static String dbUSER = "CGTSIINTRANETUSER";
	static String dbPASS = "CGTSIINTRANETUSER$321";
	static Logger log = Logger.getLogger(DBConnection.class.getName());

	
	public static Connection getConnection() throws SQLException {    	
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Thread.sleep(50);
			System.out.println(dbURL);
			con = DriverManager.getConnection(dbURL,dbUSER,dbPASS);
//			Thread.sleep(50);	
			System.out.println("@PostConstruct getConnection():::::::");
		} catch (Exception e) {			
			System.out.println("Exception DBConnection getConnection():"+e.getMessage());
			e.printStackTrace();
		}
		return con; 
	}


	public static void freeConnection(Connection connection) 	{
		try {
			if(connection!=null  && !connection.isClosed()) {
				Thread.sleep(50);
				connection.close();
				Thread.sleep(50);
			}

		} catch (Exception e) {	
			System.out.println("Exception DBConnection freeConnection()"+e.getMessage());
			e.printStackTrace();
		} 
	}

	public static void freeStatement(Statement stmt) 	{
		try {
			if(stmt!=null  && !stmt.isClosed()) {
				Thread.sleep(50);
				stmt.close();
				Thread.sleep(50);
			}

		} catch (Exception e) {	
			System.out.println("Exception DBConnection freeConnection()"+e.getMessage());
			e.printStackTrace();
		} 
	}
	
	
	public static void freeResultPStmtConn(ResultSet resultSet, PreparedStatement pStmt, Connection connection) {
		System.out.println("@PreDestroy::::::::");
		if (resultSet != null) {
			try {
				Thread.sleep(50);
				if (!resultSet.isClosed()) {
					Thread.sleep(50);
					resultSet.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pStmt != null) {
			try {
				Thread.sleep(50);
				if (!pStmt.isClosed()) {
					Thread.sleep(50);
					pStmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				Thread.sleep(50);
				if (!connection.isClosed()) {
					Thread.sleep(50);
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main (String args) throws SQLException {
		DBConnection.getConnection();
	}
}

