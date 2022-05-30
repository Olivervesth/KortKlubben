package Rooms;

import java.util.ArrayList;
import java.util.List;

import Cards.Card;
import Cards.CardManager;
import Players.Player;

public class Room
{
    /**
     * Fields for Room class
     */
    private List<Player> players;
    private Player owner = null;
    private GameManager gameManager;
    private CardManager cardManager;

    /**
     * Constructor for Room class
     * @param owner
     * @param gameManager
     * @param cardManager
     */
    public Room(Player owner, GameManager gameManager, CardManager cardManager)
    {
        players = new ArrayList<Player>();
        this.owner = owner;
        players.add(owner);
        this.gameManager = gameManager;
        this.cardManager = cardManager;
        cardManager.generateCardDeckNoJokers();
    }

    /**
     * Method to give players cards
     */
    public void giveCards()
    {
        gameManager.giveCards(players, cardManager.generateCardDeckNoJokers());
    }

}
