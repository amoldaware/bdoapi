package com.cgtmse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public Connection Get_Connection() throws Exception
	{
		try
		{
			Connection connection = null;
			/*String connectionURL = "jdbc:sqlserver://HEX-MUM2-0331:1433;databaseName=Test";			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();*/
			
			String connectionURL = "jdbc:mysql://localhost:3306/hibernate4";			
			Class.forName("com.mysql.jdbc.Driver").newInstance();			
			connection = DriverManager.getConnection(connectionURL, "root", "root");
			
			return connection;
		}
		catch (SQLException e)
		{
			System.out.println("Class : Database :: Get_Connection() :: SQLException occured :\n"+e.getMessage());
			e.getStackTrace();
			throw e;	
		}
		catch (Exception e)
		{
			System.out.println("Class : Database :: Get_Connection() :: Exception occured :\n"+e.getMessage());
			e.getStackTrace();
			throw e;	
		}
	}
}
