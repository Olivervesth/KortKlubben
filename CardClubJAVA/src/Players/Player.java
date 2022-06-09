package Players;

import Cards.Card;
import GameEngine.EngineManager;

import java.util.List;

/**
 * Abstract class to represent a player
 */
public abstract class Player {
    /**
     * Fields for Player class
     */
    private String playerName;
    private String userName;
    private int points;
    private int cardSets;
    private List<Card> cards;
    private Player partner;

    /**
     * Constructor for Human class
     *
     * @param Username   Login name of user
     * @param PlayerName public name of user
     */
    public Player(String Username, String PlayerName) {
        this.playerName = PlayerName;
        this.userName = Username;
        this.points = 0;
    }

    /**
     * Method to get the player's username
     *
     * @return String name
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Method to get the player's player name
     *
     * @return String name
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Method to get a player's cards
     *
     * @return list of player's cards
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Method to get the partner player of a player
     *
     * @return partner player
     */
    public Player getPartner() {
        return this.partner;
    }

    /**
     * Method to get a players amount of sets
     *
     * @return amount of sets
     */
    public int getSets() {
        return cardSets;
    }

    /**
     * Method to get the player's points
     *
     * @return int points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Method to add points to the player
     *
     * @param points points to add
     */
    public void updatePoints(int points) {
        if (points >= -4 && points <= 4) {
            this.points += points;
        } else {
            EngineManager.getEngineManager().saveErrorLog("UpdatePoints",
                    this.userName + " tried to add: " + points + " points");
        }
    }

    /**
     * Method to reset points between games
     */
    public void resetPoints() {
        this.points = 0;
    }

    /**
     * Method to set a player's cards
     *
     * @param newCards new set of cards
     */
    public void setCards(List<Card> newCards) {
        this.cards = newCards;
    }

    /**
     * Method to set a players partner
     *
     * @param player partner player
     */
    public void setPartner(Player player) {
        this.partner = player;
    }

    /**
     * adds 1 to a players amount of sets
     */
    public void addToSets() {
        cardSets++;
    }

    /**
     * resets the amount of sets to 0
     */
    public void resetSets() {
        cardSets = 0;
    }

    /**
     * Method to set the public playerName of user
     *
     * @param name name to set
     */
    public void setPlayerName(String name) {
        playerName = name;
    }

}
