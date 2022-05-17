package Players;

/**
 * This class is not the be implemented in 1.0
 * @author Martin
 *
 */
public class Computer implements Player
{
	/**
	 * Fields for computer class
	 */
	private String name;
	private int points;
	
	/**
	 * Constructor for computer class
	 * @param String name
	 */
	public Computer(String name)
	{
		this.name = name;
		this.points = 0;
	}

	/**
	 * Method to get the computer's name
	 * @return String name
	 */
	public String getName() 
	{
		return this.name;
	}

	/**
	 * Method to get the computer's points
	 * @return int points
	 */
	public int getPoints() 
	{
		return this.points;
	}

	/**
	 * Method to add points to the computer
	 * @param int points
	 */
	public void addPoints(int points) 
	{
		this.points += points;
	}

	/**
	 * Method to reset points between games
	 */
	public void resetPoints() 
	{
		this.points = 0;
	}

}
