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
import Players.Human;
import Players.Player;

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
		Player test = testPlayer();
		String password = "Chowder123";
		boolean result = false;

		// Act
		try {

			result = manager.createPlayer(test, password);

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

			resultSet = manager.getStats(testPlayer());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		// Assert
		assertFalse(resultSet == null);
	}
	
	
	@Test
	@Order(4)
	void checkLogin_ReturnsTrue_IfParamsEqual() {
		// Arrange
		String password = "Chowder123";
		boolean result = false;
		
		// Act
		result = manager.checkLogin(testPlayer(), password);
		
		
		// Assert
		assertTrue(result);
	}
	
	@Test
	@Order(5)
	void updatePlayer_ChangesPlayerData_IfValid() {
		// Arrange
		boolean result = false;
		
		// Act
		result = manager.updatePlayer(testPlayer2(), "TestPass123");
		
		// Assert
		assertTrue(result);
	}
	
	@Test
	@Order(6)
	void addGamePlayed_ShouldAdd_IfAble() {
		// Arrange
		boolean result = false;
		
		// Act
		result = manager.addGamePlayed(testPlayer2());
		
		// Assert
		assertTrue(result);
	}
	
	@Test
	@Order(7)
	void addGameWon_ShouldAdd_IfAble() {
		// Arrange
		boolean result = false;
		
		// Act
		result = manager.addGameWon(testPlayer2());
		
		// Assert
		assertTrue(result);
	}
	
	
	@Test
	@Order(8)
	void deletePlayer_RemovesPlayer_IfAble() {
		// Arrange
		boolean result = false;
		
		// Act
		result = manager.deletePlayer(testPlayer2());
		
		// Assert
		assertTrue(result);
	}
	
	@Test
	@Order(9)
	void createLog_WritesLogToDb_IfValid() {
		// Arrange
		String action = "Testing Log";
		String message = "This is a test for creating logs";
		boolean result = false;
		
		// Act
		result = manager.createLog(action, message);
		
		// Assert
		assertTrue(result);
	}
	
	private Player testPlayer() {
		Player player = new Human("Lars", "LarsLummer");
		
		return player;
	}
	
	private Player testPlayer2() {
		Player player = new Human("Bror", "LarsLummer");
		
		return player;
	}

}
