package GameEngine;

import java.sql.*;

public class DbManager {
	/**
	 * Fields for DbManager move this to a application setting?
	 */
	private String database = "cardclub_db";
	private String databaseUsername = "martin";
	private String databasePassword = "Kode1234!";
	private String connectionString = "jdbc:mysql://10.108.130.218:3306/" + database + "";

	/**
	 * Constructor for DbManager
	 * 
	 * @param String connectionString
	 */
	public DbManager() {
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
			con = DriverManager.getConnection(connectionString, databaseUsername, databasePassword);
			return con;
		} catch (Exception e) {
			EngineManager.getEngineManager().saveErrorLog("connectDB", e.getMessage());
		}
		if (con != null) {
			try {
				if (con.isClosed() == false) {
					con.close();
				}
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("connectDB", e.getMessage());
			}

		}
		return null;
	}

	/**
	 * Method to create a new player
	 * 
	 * @param String name
	 * @param String username
	 * @param String Password
	 * @return boolean
	 */
	public boolean createPlayer(String name, String username, String Password) {
		Connection con = connectDb();
		CallableStatement st = null;
		boolean result = false;
		try {
			st = con.prepareCall("{call SP_CreatePlayer(?, ?, ?, ?)}");
			st.setString(1, name);
			st.setString(2, username);
			st.setString(3, Password);
			st.registerOutParameter(4, Types.INTEGER);

			st.executeUpdate();

			if (st.getInt(4) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("create Player", e.getMessage());
			return result;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("create Player", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("create Player", e.getMessage());
			}
		}
	}

	/**
	 * Method to update player information
	 * 
	 * @param String newplayername
	 * @param String username
	 * @param String newusername
	 * @param String newpassword
	 * @return boolean
	 */
	public boolean updatePlayer(String newplayername, String username, String newusername, String newpassword) {
		Connection con = connectDb();
		CallableStatement st = null;
		boolean result = false;

		try {
			st = con.prepareCall("{call SP_UpdatePlayer(?, ?, ?, ?, ?)}");
			st.setString(1, newplayername);
			st.setString(2, username);
			st.setString(3, newusername);
			st.setString(4, newpassword);
			st.registerOutParameter(5, Types.INTEGER);

			st.executeUpdate();

			if (st.getInt(5) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Update Player", e.getMessage());
			return result;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Update Player", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Update Player", e.getMessage());
			}
		}
	}

	/**
	 * Method to delete a player
	 * 
	 * @param String username
	 * @return boolean
	 */
	public boolean deletePlayer(String username) {
		Connection con = connectDb();
		CallableStatement st = null;
		boolean result = false;

		try {

			st = con.prepareCall("{call SP_DeletePlayer(?, ?)}");
			st.setString(1, username);
			st.registerOutParameter(2, Types.INTEGER);

			st.executeUpdate();

			if (st.getInt(2) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Delete Player", e.getMessage());
			return result;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Delete Player", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Delete Player", e.getMessage());
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
	public boolean checkLogin(String userName, String password) {
		Connection con = connectDb();
		CallableStatement st = null;

		try {
			st = con.prepareCall("{call SP_CheckLogin(?, ?, ?)}");
			st.setString(1, userName);
			st.setString(2, password);
			st.registerOutParameter(3, Types.BIT);

			st.executeUpdate();

			boolean result = st.getBoolean(3);
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Check Login", e.getMessage());
			return false;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Check Login", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Check Login", e.getMessage());
			}
		}
	}

	/**
	 * Method to get a users stats
	 * 
	 * @param String userName
	 * @return String[]
	 */
	public String[] getStats(String username) {
		Connection con = connectDb();
		CallableStatement st = null;
		try {
			st = con.prepareCall("{call SP_GetStats(?, ?, ?)}");
			st.setString(1, username);
			st.registerOutParameter(2, Types.INTEGER);
			st.registerOutParameter(3, Types.INTEGER);

			st.executeUpdate();

			String[] result = new String[] { st.getString(2), st.getString(3) };
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Get Stats", e.getMessage());
			return null;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Get Stats", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Get Stats", e.getMessage());
			}
		}
	}

	/**
	 * Method to add a played game to the players statistics
	 * 
	 * @param String username
	 * @return boolean
	 */
	public boolean addGamePlayed(String username) {
		Connection con = connectDb();
		CallableStatement st = null;
		boolean result = false;

		try {
			st = con.prepareCall("{call SP_AddGamePlayed(?, ?)}");
			st.setString(1, username);
			st.registerOutParameter(2, Types.INTEGER);

			st.executeUpdate();

			if (st.getInt(2) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Add Game Played", e.getMessage());
			return result;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Add Game Played", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Add Game Played", e.getMessage());
			}
		}
	}

	/**
	 * Method to add a win to the players statistics
	 * 
	 * @param String username
	 * @return boolean
	 */
	public boolean addGameWon(String username) {
		Connection con = connectDb();
		CallableStatement st = null;
		boolean result = false;

		try {
			st = con.prepareCall("{call SP_AddGameWon(?, ?)}");
			st.setString(1, username);
			st.registerOutParameter(2, Types.INTEGER);

			st.executeUpdate();

			if (st.getInt(2) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Add Game Won", e.getMessage());
			return result;
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Add Game Won", e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Add Game Won", e.getMessage());
			}
		}
	}

	/**
	 * Method to create error log on db
	 * 
	 * @param String errorAction
	 * @param String errormessage
	 * @return boolean
	 */
	public boolean createLog(String errorAction, String errormessage) {
		Connection con = connectDb();
		CallableStatement st = null;
		boolean result = false;

		try {
			st = con.prepareCall("{call SP_CreateLog(?, ?, ?)}");
			st.setString(1, errorAction);
			st.setString(2, errormessage);
			st.registerOutParameter(3, Types.INTEGER);

			st.executeUpdate();

			if (st.getInt(3) > 0) {
				result = true;
			}
			return result;
		} catch (SQLException e) {
			EngineManager.getEngineManager().saveErrorLog("Create Log", e.getMessage());
			return result;
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Create Log", e.getMessage());
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				EngineManager.getEngineManager().saveErrorLog("Create Log", e.getMessage());
			}
		}
	}

}