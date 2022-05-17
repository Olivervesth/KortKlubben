package Rooms;

import java.util.List;

import Players.Player;

public class Room 
{
	/**
	 * Fields for Room class
	 */
	private List<Player> players;
	private Player owner;
	
	/**
	 * Constructor for Room class
	 * @param Player owner
	 */
	public Room(Player owner)
	{
		this.owner = owner;
		players.add(owner);
	}
}
