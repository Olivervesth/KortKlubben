package Rooms;

import java.util.ArrayList;
import java.util.List;

import GameEngine.EngineManager;
import Players.Player;

public class RoomManager 
{
	private List<Room> rooms;
	
	/**
	 * Constructor for RoomManager
	 */
	public RoomManager() 
	{
		rooms = new ArrayList<Room>();
	}
	
	public Room createRoom(int gameType, Player player)
	{
		Room room = null;
		// What type of game is the room playing?
		// currently Whist only
		switch(gameType)
		{
		case 1:
			room = new Room(player, new GameManager());
			break;
		case 2:
			break;
		}
		return room;
	}
	
	public static List<Player> resetPlayers(List<Player> players)
	{
		return EngineManager.resetPlayerPoints(players);
	}
	
}
