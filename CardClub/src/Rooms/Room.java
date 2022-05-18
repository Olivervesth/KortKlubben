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
	private GameManager gameManager;
	
	/**
	 * Constructor for Room class
	 * @param Player owner
	 */
	public Room(Player owner, GameManager gameManager)
	{
		this.owner = owner;
		players.add(owner);
		this.gameManager = gameManager;
	}
}
