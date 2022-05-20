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
	public DbManager() {
	}

	/**
	 * Method to create a new player
	 * 
	 * @param String username
	 * @param String Password
	 * @return boolean
	 */
	public boolean createPlayer(String username, String Password) {
		Connection con = connectDb();
		Statement st = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			st = con.createStatement();
			String query = "call SP_CreatePlayer('" + username + "', '" + password + "')";
			rs = st.executeQuery(query);
			if (rs.getInt(0) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.saveErrorMessage(e.getMessage());
			return result;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 * Method to validate user login
	 * 
	 * @param String userName
	 * @param String password
	 * @return boolean
	 */
	public boolean getUserValidation(String userName, String password) {
		Connection con = connectDb();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			String query = "call SP_CheckLogin('" + username + "', '" + password + "')";
			rs = st.executeQuery(query);
			boolean result = rs.getBoolean(0);
			return result;
		} catch (SQLException e) {
			EngineManager.saveErrorMessage(e.getMessage());
			return false;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 * Method to get a users stats
	 * 
	 * @param String userName
	 * @return String[]
	 */
	public String[] getStats(String userName) {
		Connection con = connectDb();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			String query = "call SP_GetStats('" + username + "')";
			rs = st.executeQuery(query);
			String[] result = new String[] { rs.getString(0), rs.getString(1) };
			return result;
		} catch (SQLException e) {
			EngineManager.saveErrorMessage(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 * Method to get database connection
	 * 
	 * @return Connection
	 */
	public Connection connectDb() {

		Connection con = null;
		try {
			// establish the connection
			con = DriverManager.getConnection(connectionString, username, password);
			return con;
		} catch (Exception e) {
			EngineManager.saveErrorMessage(e.getMessage());
		}
		if (con != null) {
			try {
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException e) {
				EngineManager.saveErrorMessage(e.getMessage());
			}

		}
		return null;
	}
}