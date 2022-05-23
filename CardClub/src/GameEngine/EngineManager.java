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
	 * @return true/false
	 */
	public boolean login(String username, String password) {
		// if login is success
		if (db.checkLogin(hashing.hash(username), hashing.hash(password))) {
			// create user?
			return true;
		}
		// if login fails
		else {
			// log failed attempt?
			return false;
		}
	}

	/**
	 * Method to add played game to player statistics
	 * 
	 * @param String username
	 * @return boolean
	 */
	public boolean addGamePlayed(String username) {
		return db.addGamePlayed(username);
	}

	/**
	 * Method to add won game to player statistics
	 * 
	 * @param String username
	 * @return boolean
	 */
	public boolean addGameWon(String username) {
		return db.addGameWon(username);
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
	public Player createPlayer(String name) {
		return playerManager.createPlayer(name, true);
	}

	/**
	 * Method to create a user in Db
	 * 
	 * @param String name
	 * @param String password
	 * @return boolean
	 */
	public boolean createUser(String playername, String username, String password) {
		return db.createPlayer(playername, hashing.hash(username) ,hashing.hash(password));
	}

	/**
	 * Method to get user stats
	 * @param String username
	 * @return String[]
	 */
	public String[] getStats(String username)
	{
		return db.getStats(username);
	}
	
	/**
	 * Method to update a user
	 * 
	 * @param String playername
	 * @param String newplayername
	 * @param String username
	 * @param String newusername
	 * @param String password
	 * @param String newpassword
	 * @return boolean
	 */
	public boolean updateUser(String newplayername, String username, String newusername, String newpassword) {
		return db.updatePlayer(newplayername, username, newusername, newpassword);
	}

	/**
	 * Method to save error messages to local file
	 * 
	 * @param String errormessage
	 */
	public void saveErrorMessage(String errormessage) {
		logger.saveMessage(errormessage);
	}

	/**
	 * Method to save error message to db log
	 * 
	 * @param String errormessage
	 * @return boolean
	 */
	public boolean saveErrorLog(String erroraction, String errormessage) {
		return db.createLog(erroraction, errormessage);
	}
}