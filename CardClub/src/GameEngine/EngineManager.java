package GameEngine;

import java.util.ArrayList;
import java.util.List;

import Players.Player;
import Players.PlayerManager;
import Rooms.RoomManager;

public final class EngineManager {
	/**
	 * Used classes in enginemanager
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
	
	public static EngineManager getEngineManager()
	{
		if(em == null)
		{
			em = new EngineManager();
			return em;
		}
		else
		{
			return em;
		}
	}

	/**
	 * Attempts to hash, ask database and validate the response
	 * 
	 * @param String username
	 * @param String password
	 * @return String Player name
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
	 * @param String username
	 * @return boolean
	 */
	public boolean addGamePlayed(Player player) {
		return db.addGamePlayed(player);
	}

	/**
	 * Method to add won game to player statistics
	 * 
	 * @param String username
	 * @return boolean
	 */
	public boolean addGameWon(Player player) {
		return db.addGameWon(player);
	}

	/**
	 * Method to reset player points
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
	 * Method to create a player for rooms
	 * 
	 * @param String name
	 * @return Player
	 */
	public Player createPlayer(String username,String playername) {
		return playerManager.createPlayer(hashing.hash(username),playername, true);
	}

	/**
	 * Method to create a user in Db
	 * 
	 * @param String name
	 * @param String password
	 * @return boolean
	 */
	public boolean createUser(Player player, String password) {
		return db.createPlayer(player ,hashing.hash(password));
	}

	/**
	 * Method to get user stats
	 * @param String username
	 * @return String[]
	 */
	public String[] getStats(Player player)
	{
		return db.getStats(player);
	}
	
	/**
	 * Method to update a user
	 * 
	 * @param String newplayername
	 * @param String username
	 * @param String newpassword
	 * @return boolean
	 */
	public boolean updateUser(Player player, String newPlayername, String newpassword) {
		return db.updatePlayer(player, newpassword);
	}

	/**
	 * Method to save error messages to log table in db
	 * 
	 * @param String action
	 * @param String message
	 * 
	 * @returns boolean
	 */
	public boolean saveErrorLog(String action, String message) {
		return logger.saveLog(action, message, db);
	}

}