package GameEngine;

import java.sql.*;

public class DbManager {
	/**
	 * Fields for DbManager move this to a application setting?
	 */
	private String database = "cardclub_db";
	private String username = "martin";
	private String password = "Kode1234!";
	private String connectionString = "jdbc:mysql://10.108.130.218:3306/" + database + "";

	/**
	 * Constructor for DbManager
	 * 
	 * @param String connectionString
	 */
//	public DbManager(String connectionString) original
//	{
//		this.connectionString = connectionString;
//	}
	public DbManager() {
	}

	// Use stored procedure on Db, instead of sql call here
	public boolean getUserName(String userName) {
		int count = 0;
		// count -> SELECT count(*) FROM Users WHERE Name = username
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Use stored procedure on Db, instead of sql call here
	public boolean getPassword(String password) {
		int count = 0;
		// count -> SELECT count(*) FROM Users WHERE Password = password
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String getStats(String userName) {
		// TODO implement get player stats
		return "";
	}

<<<<<<< HEAD
	public Connection ConnectDb() {
=======
	public Connection getConection()
	{
		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionString, username, password);
			return con;
		} catch (SQLException e) {
			EngineManager.saveErrorMessage(e.getMessage());
			return null;
		}
	}
	
	public Statement ConnectDb() {
>>>>>>> main
		Connection con = null;
		try {
			// establish the connection
			con = DriverManager.getConnection(connectionString, username, password);
			return con;

			// create JDBC statement object
//			Statement st = con.createStatement();

			// prepare SQL query
//		      String query = "call SP_CreatePlayer('Jesper','Jesperplayer','password');";
//		      ResultSet rs = st.executeQuery(query);
//		      System.out.println(rs);
//		      con.close();
			
			//EXAMPLES HERE !!!
			// send and execute SQL query in Database software
			// process the ResultSet object
//		      boolean flag = false;
//		      while (rs.next()) {
//		         flag = true;
//		         System.out.println(rs.getInt(1) + " " + rs.getString(2) + 
//		                  " " + rs.getString(3));
//		      }

//		      if (flag == true) {
//		         System.out.println("\nRecords retrieved and displayed");
//		      } else {
//		         System.out.println("Record not found");
//		      }

			// close JDBC objects
//		      rs.close();
//		      st.close();
//		      con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		if (con != null) {
			try {
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}
}
