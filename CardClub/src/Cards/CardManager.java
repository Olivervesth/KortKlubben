package Cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manager class to handle the playing cards
 * 
 * @author Martin
 */
public class CardManager {
	/**
	 * Fields for CardManager
	 */
	private List<Card> cards;

	/**
	 * Constructor for CardManager
	 */
	public CardManager() {
		cards = new ArrayList<>();
		// can expand with multiple decks / CardManager
		generateCardDeckNoJokers();
	}

	/**
	 * Method to generate a new deck of cards This deck of cards do not contain
	 * jockers Generated cards are added to the managers cards list
	 */
	public void generateCardDeckNoJokers() {
		String suit = "";
		// for each suit
		for (int s = 0; s < 4; s++) {
			switch (s) {
			case 0:
				suit = "Hearts";
				break;
			case 1:
				suit = "Spades";
				break;
			case 2:
				suit = "Diamonds";
				break;
			case 3:
				suit = "Clubs";
				break;
			}
			// for each value
			for (int v = 2; v < 14; v++) {
				cards.add(new Card(v, suit));
			}
		}
	}

	/**
	 * Method to draw a card from the cardmanager.
	 * 
	 * @return Card
	 */
	public Card giveCard() {
		// TODO entire method
		int random = new Random().nextInt(0 - cards.size());
		Card drawnCard = (Card) cards.toArray()[random];
		cards.remove(drawnCard);
		return drawnCard;
	}
}
