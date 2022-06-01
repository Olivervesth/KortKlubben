package Players;

import Cards.Card;
import GameEngine.EngineManager;

import java.util.List;

public abstract class Player {
    /**
     * Fields for Player class
     */
    private String playername;
    private String username;
    private int points;
    private int cardSets;
    private List<Card> cards;
    private Player partner;

    /**
     * Constructor for Human class
     */
    public Player(String Username, String PlayerName) {
        this.playername = PlayerName;
        this.username = Username;
        this.points = 0;
    }

    /**
     * Method to get the player's username
     *
     * @return String name
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Method to get the player's player name
     *
     * @return String name
     */
    public String getPlayerName() {
        return this.playername;
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
     * Method to set a player's cards
     *
     * @param newCards
     */
    public void setCards(List<Card> newCards) {
        this.cards = newCards;
    }

    /**
     * Method to set a players partner
     *
     * @param player
     */
    public void setPartner(Player player) {
        this.partner = player;
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
     * Method to get the player's points
     *
     * @return int points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Method to add points to the player
     */
    public void updatePoints(int points) {
        if (points >= -4 && points <= 4) {
            this.points += points;
        } else {
            EngineManager.getEngineManager().saveErrorLog("UpdatePoints",
                    this.username + " tried to add: " + points + " points");
        }
    }

    /**
     * Method to reset points between games
     */
    public void resetPoints() {
        this.points = 0;
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
     * adds 1 to a players amount of sets
     *
     * @param set
     */
    public void setSets(int set) {
        cardSets++;
    }

    /**
     * resets the amount of sets to 0
     */
    public void resetSets() {
        cardSets = 0;
    }

}
