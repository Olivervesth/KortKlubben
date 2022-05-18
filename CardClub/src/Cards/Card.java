package Cards;

/**
 * @author Martin
 *
 */
public class Card
{
	/**
	 * Fields for Card class
	 */
	private int value;
	private String suit;
	
	/**
	 * Constructor for Card class
	 * @param int value
	 * @param String color
	 */
	public Card(int value, String suit)
	{
		this.value = value;
		this.suit = suit;
	}
	
	/**
	 * Method to get the card's value
	 * @return int value
	 */
	public int getValue()
	{
		return this.value;
	}
	
	/**
	 * Method to the the card's suit
	 * @return String suit
	 */
	public String getSuit()
	{
		return this.suit;
	}
}
