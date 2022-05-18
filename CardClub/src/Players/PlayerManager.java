package Players;

public class PlayerManager 
{
	/**
	 * Method to create a new player instance
	 * @param String name
	 * @param boolean human
	 * @return Player
	 */
	public Player createPlayer(String name, boolean human)
	{
		if(human)
		{
			return new Human(name);
		}
		else
		{
			return new Computer(name);
		}
	}
	
	/**
	 * Method to reset a players points
	 * @param Player player
	 * @return Player
	 */
	public Player resetPlayerPoints(Player player)
	{
		player.resetPoints();
		return player;
	}
}
