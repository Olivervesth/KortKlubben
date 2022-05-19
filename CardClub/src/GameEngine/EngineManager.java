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
	private Hashing hashing;
	private Logger logger;
	private DbManager db;
	private Authentication auth;
	private static PlayerManager playerManager;
	private static RoomManager roomManager;

	/**
	 * Constructor for EngineManager
	 */
	public EngineManager() {
		logger = new Logger();
		hashing = new Hashing();
		db = new DbManager("ConnectionString");
		auth = new Authentication();
		playerManager = new PlayerManager();
		roomManager = new RoomManager();
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
		if (auth.validateUserInfo(db.getUserName(hashing.hash(username)), db.getPassword(hashing.hash(password)))) {
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

}
