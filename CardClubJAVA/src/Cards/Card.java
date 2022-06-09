package Cards;

/***
 * Object class for a Card
 */
public class Card {
    /**
     * Fields for Card
     */
    private int value;
    private Suit suit;

    /**
     * Constructor for Card class
     *
     * @param value value of card
     * @param suit suit of card
     */
    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Method to get the card's value
     *
     * @return int value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Method to get the card's suit
     *
     * @return String suit
     */
    public Suit getSuit() {
        return this.suit;
    }
}
