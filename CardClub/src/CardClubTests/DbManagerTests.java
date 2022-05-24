package CardClubTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import GameEngine.DbManager;

class DbManagerTests {

	static DbManager manager;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		manager = new DbManager();
	}

	
	@Test
	@Order(1)
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
	@Order(2)
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
	@Order(3)
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
	
	
	@Test
	@Order(4)
	void checkLogin_ReturnsTrue_IfParamsEqual() {
		//Arrange
		String username = "RasRasRasputin";
		String password = "QueenLover12";
		boolean result = false;
		
		//Act
		result = manager.checkLogin(username, password);
		
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	@Order(5)
	void updatePlayer_ChangesPlayerData_IfValid() {
		//Arrange
		String username = "PaulChowder";
		
		boolean result = false;
		
		//Act
		result = manager.updatePlayer("Lars", username, "LarsLarsen", "TestPass123");
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	@Order(6)
	void deletePlayer_RemovesPlayer_IfAble() {
		//Arrange
		String username = "LarsLarsen";
		boolean result = false;
		
		//Act
		result = manager.deletePlayer(username);
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	@Order(7)
	void createLog_WritesLogToDb_IfValid() {
		//Arrange
		String action = "Testing Log";
		String message = "This is a test for creating logs";
		boolean result = false;
		
		//Act
		result = manager.createLog(action, message);
		
		//Assert
		assertTrue(result);
	}

}
