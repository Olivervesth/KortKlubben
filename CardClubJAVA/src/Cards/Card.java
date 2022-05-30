package Cards;

public class Card {
    /**
     * Fields for Card
     */
    private int value;
    private String suit;

    /**
     * Constructor for Card class
     *
     */
    public Card(int value, String suit) {
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
     * Method to the the card's suit
     *
     * @return String suit
     */
    public String getSuit() {
        return this.suit;
    }
}
