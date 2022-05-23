package CardClubTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import GameEngine.DbManager;

class DbManagerTests {

	static DbManager manager;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		manager = new DbManager();
	}

	
	@Test
	void connectDb_ConnectsToDb_IfAvailable() {
		// Arrange
		Connection con = manager.connectDb();
		Statement st = null;
		ResultSet rs = null;

		// Act
		try {
			st = con.createStatement();
			String query = "SELECT * FROM Players";
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				st.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		// Assert
		assertFalse(rs == null);
	}

	
	@Test
	void createPlayer_CreatesNewPlayer_IfNotNull() {
		// Arrange
		String playername = "Paul";
		String username = "PaulChowder";
		String password = "Chowder123";
		boolean result = false;

		// Act
		try {

			result = manager.createPlayer(playername, username, password);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		// Assert
		assertTrue(result);
	}

	
	@Test
	void getStats_ReturnsStats_IfPlayerExists() {
		// Arrange
		String[] resultSet = null;

		// Act
		try {

			resultSet = manager.getStats("PaulChowder");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		// Assert
		assertFalse(resultSet == null);
	}
	
	
//	@Test
//	void checkLogin_ReturnsTrue_IfParamsEqual() {
//		assertFalse(true);
//	}
//	
//	@Test
//	void updatePlayer_ChangesPlayerData_IfValid() {
//		assertFalse(true);
//	}
//	
//	@Test
//	void deletePlayer_RemovesPlayer_IfAble() {
//		assertFalse(true);
//	}
//	
//	@Test
//	void createLog_WritesLogToDb_IfValid() {
//		assertFalse(true);
//	}

}
