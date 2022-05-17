package GameEngine;

/**
 * @author Martin
 */
public class Player 
{
	/**
	 * Fields for Player class
	 */
	private String name;
	private int points;
	
	/**
	 * Constructor for Player class
	 * @param String name
	 */
	public Player(String name)
	{
		this.name = name;
		this.points = 0;
	}
	
	/**
	 * Method to get the player's name
	 * @return String name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Method to get the player's points
	 * @return int points
	 */
	public int getPoints()
	{
		return this.points;
	}
	
	/**
	 * Method to add points to the player
	 * Checks for valid amount of points in one addition
	 * If points not valid, prints player name and points attempted to add, to terminal
	 * !TODO Log the "error" for handling/registrering cheaters?
	 * @param int points
	 */
	public void addPoints(int points)
	{
		if(points >= -4 && points <= 4)
		{
			this.points += points;
		}
		else
		{
			System.out.println("Player: "+this.name+" tried to add: "+ points+" points");
		}
	}
}
