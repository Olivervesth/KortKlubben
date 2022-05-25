package Rooms;

import java.util.ArrayList;
import java.util.List;

import Cards.CardManager;
import GameEngine.EngineManager;
import Players.Player;

public class RoomManager {
	/**
	 * Fields
	 */
	private List<Room> rooms;
	private CardManager cardManager;

	/**
	 * Constructor for RoomManager
	 */
	public RoomManager() {
		rooms = new ArrayList<Room>();
		cardManager = new CardManager();
	}

	/**
	 * Method to create a new room
	 * 
	 * @param int gameType
	 * @param Player owner
	 * @return Room
	 */
	public Room createRoom(int gameType, Player owner) {
		Room room = null;
		// What type of game is the room playing?
		// currently Whist only
		switch (gameType) {
		// Whist
		case 1:
			room = new Room(owner, new GameManager(), new CardManager());
			break;
		// Poker?
		case 2:
			break;
		// Blackjack?
		case 3:
			break;
		}
		if (room != null)
			rooms.add(room);
		return room;
	}

	/**
	 * Method to reset players in a room
	 * 
	 * @param List<Player> players
	 * @return List<Player> TODO check static
	 */
	public static List<Player> resetPlayers(List<Player> players) {
		return EngineManager.resetPlayerPoints(players);
	}

}
