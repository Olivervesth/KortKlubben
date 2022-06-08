package GameEngine;

import java.sql.*;
import java.util.Objects;

import Players.Player;

public class DbManager {

    /**
     * Constructor for DbManager
     */
    public DbManager() {
    }

    /**
     * Method to get database connection
     *
     * @return Connection
     */
    public Connection connectDb() {
        //TODO: Move database connection settings into readable file
        String databaseUsername = "martin";
        String databasePassword = "Kode1234!";
        String database = "cardclub_db";
        String connectionString = "jdbc:mysql://10.108.130.218:3306/" + database + "";

        Connection con = null;
        try {
            // establish the connection
            con = DriverManager.getConnection(connectionString, databaseUsername, databasePassword);
            return con;
        } catch (Exception e) {
            EngineManager.getEngineManager().saveErrorLog("connectDB", e.getMessage());
        }
        return null;
    }

    /**
     * Method to create a new player
     *
     * @return boolean
     */
    public boolean createPlayer(Player player, String Password) {
        Connection con = connectDb();
        CallableStatement st = null;
        boolean result = false;
        try {
            st = con.prepareCall("{call SP_CreatePlayer(?, ?, ?, ?)}");
            st.setString(1, player.getPlayerName());
            st.setString(2, player.getUserName());
            st.setString(3, Password);
            st.registerOutParameter(4, Types.INTEGER);

            st.executeUpdate();

            if (st.getInt(4) > 0) {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("create Player", e.getMessage());
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
     * @return boolean
     */
    public boolean updatePlayer(Player player, String newPassword) {
        Connection con = connectDb();
        CallableStatement st = null;
        boolean result = false;

        try {
            st = con.prepareCall("{call SP_UpdatePlayer(?, ?, ?, ?)}");
            st.setString(1, player.getPlayerName());
            st.setString(2, player.getUserName());
            st.setString(3, newPassword);
            st.registerOutParameter(4, Types.INTEGER);

            st.executeUpdate();

            if (st.getInt(4) > 0) {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Update Player", e.getMessage());
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
     * @return boolean
     */
    public boolean deletePlayer(Player player) {
        Connection con = connectDb();
        CallableStatement st = null;
        boolean result = false;

        try {

            st = con.prepareCall("{call SP_DeletePlayer(?, ?)}");
            st.setString(1, player.getUserName());
            st.registerOutParameter(2, Types.INTEGER);

            st.executeUpdate();

            if (st.getInt(2) > 0) {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Delete Player", e.getMessage());
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
     * @return boolean
     */
    public boolean checkLogin(String username, String password) {
        Connection con = connectDb();
        CallableStatement st = null;

        try {
            st = con.prepareCall("{call SP_CheckLogin(?, ?, ?)}");
            st.setString(1, username);
            st.setString(2, password);
            st.registerOutParameter(3, Types.BIT);

            st.executeUpdate();

            return st.getBoolean(3);
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Check Login", e.getMessage());
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
     * Method to get a players stats
     *
     * @return String[]
     */
    public String[] getStats(Player player) {
        Connection con = connectDb();
        CallableStatement st = null;
        try {
            st = con.prepareCall("{call SP_GetStats(?, ?, ?)}");
            st.setString(1, player.getUserName());
            st.registerOutParameter(2, Types.INTEGER);
            st.registerOutParameter(3, Types.INTEGER);

            st.executeUpdate();

            return new String[]{st.getString(2), st.getString(3)};
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Get Stats", e.getMessage());
            return null;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
     * Method to get a players playerName
     *
     * @return String
     */
    public String getPlayerName(String username) {
        Connection con = connectDb();
        CallableStatement st = null;
        try {
            st = con.prepareCall("{CALL SP_GetPlayerName(?, ?)}");
            st.setString(1, username);
            st.registerOutParameter(2, Types.VARCHAR);

            st.executeUpdate();

            String result = st.getString(2);

            return Objects.requireNonNullElse(result, "fakename");
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Get Player Name", e.getMessage());
            return null;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                EngineManager.getEngineManager().saveErrorLog("Get Player Name", e.getMessage());
            }
            try {
                con.close();
            } catch (SQLException e) {
                EngineManager.getEngineManager().saveErrorLog("Get Player Name", e.getMessage());
            }
        }
    }

    /**
     * Method to add a played game to the players statistics
     *
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
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
            return false;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
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
     * @return boolean
     */
    public boolean createLog(String errorAction, String errorMessage) {
        Connection con = connectDb();
        CallableStatement st = null;
        boolean result = false;

        try {
            st = con.prepareCall("{call SP_CreateLog(?, ?, ?)}");
            st.setString(1, errorAction);
            st.setString(2, errorMessage);
            st.registerOutParameter(3, Types.INTEGER);

            st.executeUpdate();

            if (st.getInt(3) > 0) {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Create Log", e.getMessage());
            return false;
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


    /**
     * Method to create error log on db
     *
     * @return boolean
     */
    public boolean createErrorLog(String errorAction, String errorMessage) {
        Connection con = connectDb();
        CallableStatement st = null;
        boolean result = false;

        try {
            st = con.prepareCall("{call SP_CreateErrorLog(?, ?, ?)}");
            st.setString(1, errorAction);
            st.setString(2, errorMessage);
            st.registerOutParameter(3, Types.INTEGER);

            st.executeUpdate();

            if (st.getInt(3) > 0) {
                result = true;
            }
            return result;
        } catch (SQLException e) {
            EngineManager.getEngineManager().saveErrorLog("Create Log", e.getMessage());
            return false;
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