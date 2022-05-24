package Rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Cards.Card;

public class GameManager 
{
	/**
	 * Fields for GameManager
	 */
	private List<List<Card>> playerCards;
	
	/**
	 * Constructor for GameManager
	 */
	public GameManager()
	{
		playerCards = new ArrayList<List<Card>>();
	}
	
	/**
	 * Method to give players cards
	 * @param List<Card> cards
	 */
	public void giveCards(List<Card> cards)
	{
		for(int i = 0; i < 4; i++)
		{
			List<Card> cardList = new ArrayList<Card>();
			for(int o = 0; o < 13; o++)
			{
				int r = new Random().nextInt(0 - cards.size());
				Card c = (Card) cards.toArray()[r];
				cards.remove(c);
				cardList.add(c);
			}
			playerCards.add(cardList);
		}
	}
}
