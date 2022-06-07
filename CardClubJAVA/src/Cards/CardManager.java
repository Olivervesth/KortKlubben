package Cards;

import java.util.ArrayList;
import java.util.List;

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
    }

        /**
     * Method to generate a new deck of cards This deck of cards do not contain
     * jokers Generated cards are added to the managers cards list
     *
     * @return List<Card>
     */
    public List<Card> generateCardDeckNoJokers() {
        Suit suit = null;
        // for each suit
        for (int s = 0; s < 4; s++) {
            switch (s) {
                case 0 -> suit = Suit.Hearts;
                case 1 -> suit = Suit.Spades;
                case 2 -> suit = Suit.Diamonds;
                case 3 -> suit = Suit.Clubs;
            }
            // for each value
            for (int v = 2; v < 15; v++) {
                cards.add(new Card(v, suit));
            }
        }
        return cards;
    }
}
