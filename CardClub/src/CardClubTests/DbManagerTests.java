package CardClubTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import GameEngine.DbManager;
import GameEngine.EngineManager;

class DbManagerTests {
	
	static DbManager manager;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		manager = new DbManager();
	}

	@Test
	void connectDb_ConnectsToDb_IfAvailable() {
		//Arrange
		Connection con = manager.connectDb();
		Statement st = null;	
		ResultSet rs = null;
		
		//Act
		try {
			st = con.createStatement();
			String query = "SELECT * FROM Players";
			rs = st.executeQuery(query);	
		} catch (SQLException e) {
			EngineManager.saveErrorMessage(e.getMessage());
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
		
		//Assert
		assertFalse(rs == null);
	}
	
	@Test
	void getStats_ReturnsStats_IfPlayerExists() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
