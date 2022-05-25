package GameEngine;

import java.util.ArrayList;
import java.util.List;

import Players.Player;
import Players.PlayerManager;
import Rooms.RoomManager;

public final class EngineManager {
	/**
	 * Fields
	 */
	private static Hashing hashing;
	private static Logger logger;
	private static DbManager db;
	private static PlayerManager playerManager;
	private static RoomManager roomManager;
	private static EngineManager em;

	/**
	 * Constructor for EngineManager
	 */
	public EngineManager() {
		em = this;
		logger = new Logger();
		hashing = new Hashing();
		db = new DbManager();
		playerManager = new PlayerManager();
		roomManager = new RoomManager();
	}

	public static EngineManager getEngineManager() {
		if (em == null) {
			em = new EngineManager();
			return em;
		} else {
			return em;
		}
	}

	/**
	 * Method to login
	 * 
	 * @param String username
	 * @param String password
	 * @return Player
	 */
	public Player login(String username, String password) {
		// if login is success
		if (db.checkLogin(hashing.hash(username), hashing.hash(password))) {
			return playerManager.createPlayer(username, db.getPlayerName(username), true);
		}
		// if login fails
		else {
			// log failed attempt?
			return null;
		}
	}

	/**
	 * Method to add played game to player statistics
	 * 
	 * @param Player player
	 * @return boolean
	 */
	public boolean addGamePlayed(Player player) {
		return db.addGamePlayed(player);
	}

	/**
	 * Method to add won game to player statistics
	 * 
	 * @param Player player
	 * @return boolean
	 */
	public boolean addGameWon(Player player) {
		return db.addGameWon(player);
	}

	/**
	 * Method to reset players points
	 * 
	 * @param List<Player> players
	 * @return List<Player>
	 */
	public static List<Player> resetPlayerPoints(List<Player> players) {
		List<Player> returnList = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			returnList.add(playerManager.resetPlayerPoints(players.get(i)));
		}
		return returnList;
	}

	/**
	 * Method to create a player
	 * 
	 * @param String username
	 * @param String playername
	 * @return Player
	 */
	public Player createPlayer(String username, String playername) {
		return playerManager.createPlayer(hashing.hash(username), playername, true);
	}

	/**
	 * Method to create a user in Db
	 * 
	 * @param Player player
	 * @param String password
	 * @return boolean
	 */
	public boolean createUser(Player player, String password) {
		return db.createPlayer(player, hashing.hash(password));
	}

	/**
	 * Method to get player stats
	 * 
	 * @param Player player
	 * @return String[]
	 */
	public String[] getStats(Player player) {
		return db.getStats(player);
	}

	/**
	 * Method to update a user
	 * 
	 * @param Player player
	 * @param String newpassword
	 * @return boolean
	 */
	public boolean updateUser(Player player, String newpassword) {
		return db.updatePlayer(player, newpassword);
	}
	
	/**
	 * Method to delete a user
	 * 
	 * @param Player player
	 * @return boolean
	 */
	public boolean deleteUser(Player player) {
		return db.deletePlayer(player);
	}

	/**
	 * Method to save error messages to ErrorLog table in db
	 * 
	 * @param String action
	 * @param String message
	 * 
	 * @returns boolean
	 */
	public boolean saveErrorLog(String action, String message) {
		return logger.saveErrorLog(action, message, db);
	}
	
	/**
	 * Method to save messages to Log table in db
	 * 
	 * @param String action
	 * @param String message
	 * 
	 * @returns boolean
	 */
	public boolean saveLog(String action, String message) {
		return logger.saveLog(action, message, db);
	}

}