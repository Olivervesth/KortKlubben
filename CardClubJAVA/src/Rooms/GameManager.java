package Rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Cards.Card;

public class GameManager {
    /**
     * Fields for GameManager
     */
    private List<List<Card>> playerCards;
    private List<Card> playedCards;
    private int[] playerPoints;
    private String trumphSuit;
    private String playingSuit;

    /**
     * Constructor for GameManager
     */
    public GameManager() {
        playerCards = new ArrayList<List<Card>>();
        playedCards = new ArrayList<Card>();
    }

    /**
     * Method to give players cards
     *
     * @param List<Card> cards
     */
    public void giveCards(List<Card> cards) {
        for (int i = 0; i < 4; i++) {
            List<Card> cardList = new ArrayList<Card>();
            for (int o = 0; o < 13; o++) {
                int r = new Random().nextInt(0 - cards.size());
                Card c = (Card) cards.toArray()[r];
                cards.remove(c);
                cardList.add(c);
            }
            playerCards.add(cardList);
        }
    }

    /**
     * Method to play a card
     *
     * @param int  playerIndex
     * @param Card card
     */
    public void playCard(int playerIndex, Card card) {
        // TODO
        // Replace card with string suit string value? to remove connections
        List<Card> pCards = playerCards.get(playerIndex);
        pCards.remove(card);
        playerCards.remove(playerIndex);
        playerCards.add(playerIndex, pCards);
        playedCards.add(playerIndex, card);
    }

    /**
     * Method to see who won the round
     *
     * @return int playerIndex
     */
    public int checkSet() {
        int highestCard = 0;
        int playerIndex = -1;

        for (int i = 0; i < 4; i++) {
            String suit = ((Card) (playedCards.toArray()[i])).getSuit();
            int value = ((Card) (playedCards.toArray()[i])).getValue();

            if (suit.equals(playingSuit) || suit.equals(trumphSuit)) {
                if (value > highestCard) {
                    highestCard = value;
                    playerIndex = i;
                }
            }
        }
        return playerIndex;
    }
}
