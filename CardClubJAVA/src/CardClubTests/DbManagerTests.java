package CardClubTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import GameEngine.DbManager;
import Players.Human;
import Players.Player;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DbManagerTests {

    static DbManager manager;

    @BeforeAll
    static void setUpBeforeClass() {
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
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                if (st != null) {
                    st.close();
                }
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
        assertNotNull(rs);
    }


    @Test
    @Order(2)
    void createPlayer_CreatesNewPlayer_IfNotNull() {
        // Arrange
        String password = "Chowder123";
        boolean result = false;

        // Act
        try {

            result = manager.createPlayer(testPlayer(), password);

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
        assertNotNull(resultSet);
    }


    @Test
    @Order(4)
    void checkLogin_ReturnsTrue_IfParamsEqual() {
        // Arrange
        String username = testPlayer().getUserName();
        String password = "Chowder123";

        // Act
        boolean result = manager.checkLogin(username, password);


        // Assert
        assertTrue(result);
    }

    @Test
    @Order(5)
    void updatePlayer_ChangesPlayerData_IfValid() {

        // Act
        boolean result = manager.updatePlayer(testPlayer2(), "TestPass123");

        // Assert
        assertTrue(result);
    }

    @Test
    @Order(6)
    void addGamePlayed_ShouldAdd_IfAble() {
        // Act
        boolean result = manager.addGamePlayed(testPlayer2().getUserName());

        // Assert
        assertTrue(result);
    }

    @Test
    @Order(7)
    void addGameWon_ShouldAdd_IfAble() {
        // Act
        boolean result = manager.addGameWon(testPlayer2().getUserName());

        // Assert
        assertTrue(result);
    }


    @Test
    @Order(8)
    void deletePlayer_RemovesPlayer_IfAble() {
        // Act
        boolean result = manager.deletePlayer(testPlayer2());

        // Assert
        assertTrue(result);
    }

    @Test
    @Order(9)
    void createLog_WritesLogToDb_IfValid() {
        // Arrange
        String action = "Testing Log";
        String message = "This is a test for creating logs";

        // Act
        boolean result = manager.createLog(action, message);

        // Assert
        assertTrue(result);
    }

    private Player testPlayer() {

        return new Human("LarsLummer", "Lars");
    }

    private Player testPlayer2() {

        return new Human("LarsLummer", "Bror");
    }

}
