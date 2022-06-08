package Rooms;

import java.util.*;

import Cards.Card;
import Cards.Suit;
import GameEngine.EngineManager;
import Players.Player;

public class GameManager {
    /**
     * Fields for GameManager
     */
    private List<Card> playedCards;
    private int[] playerPoints;
    private Suit trumpSuit;
    private Suit playingSuit;

    /**
     * Constructor for GameManager
     */
    public GameManager() {
        playedCards = new ArrayList<>();
    }

    /**
     * Method to give players cards
     *
     * @param players players to give cards
     * @param cards cards for players
     * @return List<Player>
     */
    public List<Player> giveCards(List<Player> players, List<Card> cards) {
        for (Player player : players) {
            List<Card> cardList = new ArrayList<>();
            for (int o = 0; o < 13; o++) {
                int r = new Random().nextInt(0, cards.size());
                Card c = (Card) cards.toArray()[r];
                cards.remove(c);
                cardList.add(c);
            }
            player.setCards(cardList);
        }
        return players;
    }

    /**
     * Method to see who WON
     *
     * @param playerList list of players in game
     */
    public void checkGame(List<Player> playerList) {
        for (Player player : playerList) {//Go through the list of players
            EngineManager.getEngineManager().addGamePlayed(player);//Adds a played game to player data
                if (player.getPoints() >= 1) {//If a player has 5 or more points the player wins
                    EngineManager.getEngineManager().addGameWon(player);//Adds a game won to player data,
                // TODO win msg
                System.out.println(player.getPlayerName() + " is the winner!");
                EngineManager.getEngineManager().msgPlayer(player, "You Won");
            } else {
                // TODO loose msg
                System.out.println(player.getPlayerName() + " lost the game.");
                EngineManager.getEngineManager().msgPlayer(player, "You Lost");
            }
        }
    }

    /**
     * returns the points for players set count
     *
     * @param set amounts of sets
     * @return int
     */
    public int pointSystem(int set) {
        int points = 0;
        switch (set) {
            case 0:
                // -4
                points = -4;
                break;
            case 1:
            case 2:
                // -3
                points = -3;
                break;
            case 3:
            case 4:
                // -2
                points = -2;
                break;
            case 5:
            case 6:
                // -1
                points = -1;
                break;
            case 7:
            case 8:
                // +1
                points = 1;
                break;
            case 9:
            case 10:
                // +2
                points = 2;
                break;
            case 11:
            case 12:
                // +3
                points = 3;
                break;
            case 13:
                // +4
                points = 4;
                break;
        }
        return points;
    }

    /**
     * Method to see who have most sets
     *
     * @param playerList list of players in game
     */
    public boolean checkRound(List<Player> playerList) {
        Player player1 = playerList.get(0);
        if (player1.getPartner() != null) {//Playing grand
            Player partner = player1.getPartner();
            int set = player1.getSets() + partner.getSets();
            int points = pointSystem(set);

            for (Player player : playerList) {
                if (player.equals(player1) || player.equals(partner)) {
                    player.updatePoints(points);
                } else {
                    player.updatePoints(-1 * points);
                }
            }

        } else {//Playing pass "else without if error"
            for (Player player : playerList) {
                player.updatePoints(pointSystem(player.getSets()));
            }
        }
        trumpSuit = null;
        boolean winConditionMet = false;
        for (Player p : playerList)
        {
            if (p.getPoints() >= 1 )
            {
                winConditionMet = true;
                break;
            }
        }
        if (winConditionMet)
        {
            checkGame(playerList);
            return true;
        }
        return false;
    }


    /**
     * Method to find partners
     *
     * @param cardPlayer the player playing a card
     * @param playedCard the card being played
     * @param players list of players in game
     */
    public void findPartners(Player cardPlayer, Card playedCard, List<Player> players) {
        Card matchingAce = new Card(14, playedCard.getSuit());

        for (Player player : players) {
            for (Card card : player.getCards()) {
                if (card == matchingAce) {

                    //Set Partner
                    player.setPartner(cardPlayer);

                    if (player.getPartner() == player) // played own card
                    {
                        // all players as own partner
                        nullAllPartners(players);
                    } else {
                        Player p1 = null;
                        Player p2 = null;
                        for (Player p : players) {
                            if (p != cardPlayer && p != player && p1 == null) {
                                p1 = p;
                            } else if (p != cardPlayer && p != player && p != p1) {
                                p2 = p;
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * Method to see who won the set
     *
     * @param playerList list of players in game
     */
    public void checkForSet(List<Player> playerList) {
        int highestCard = 0;
        int playerIndex = -1;
        try{
            for (int i = 0; i < playerList.size(); i++) {
                Suit suit = ((Card) (playedCards.toArray()[i])).getSuit();
                int value = ((Card) (playedCards.toArray()[i])).getValue();

                if (suit.equals(playingSuit) || suit.equals(trumpSuit)) {
                    if (value > highestCard) {
                        highestCard = value;
                        playerIndex = i;
                    }
                }
            }
            playerList.get(playerIndex).addToSets();
            playedCards.clear();
            // crashes ?
            playingSuit = null;

        }catch(ArrayIndexOutOfBoundsException e){
            //log
            e.printStackTrace();
        }
    }

    /**
     * Sets all players' partner to null
     *
     * @param players list of players to set partner to null
     */
    private void nullAllPartners(List<Player> players) {
        for (Player player : players) {
            player.setPartner(null);
        }
    }

    /**
     * Sorts a list of players based on theirs sets.
     * Highest to lowest.
     *
     * @param players list of players to sort by amount of sets
     */
    private void sortListBySets(List<Player> players) {
        players.sort(Comparator.comparingInt(Player::getSets).reversed());
    }

    /**
     * Removes the played card and call the cardPlayed method
     *
     * @param player player playing a card
     * @param card   card being played
     */
    public void playCard(Player player, Card card) {
        for (Card pcard : player.getCards()) {
            if (pcard.getValue() == card.getValue() && pcard.getSuit() == card.getSuit()) {
                if (playingSuit == null)
                {
                    this.playingSuit = card.getSuit();
                }
                if (trumpSuit == null)
                {
                    this.trumpSuit = card.getSuit();
                }
                List<Card> playerCards = player.getCards();
                playerCards.remove(pcard);
                player.setCards(playerCards);
                playedCards.add(card);
                EngineManager.getEngineManager().cardPlayed(player, card);
                break;
            }
        }
        String temphand=player.getPlayerName()+" cards - ";
        for (Card pcard : player.getCards()){
            temphand += pcard.getValue()+";"+pcard.getSuit()+" ";
        }
        System.out.println(temphand);
    }

    /**
     * Checks that the suit exists in the suit enum
     *
     * @param playerSuit suit of card
     * @return boolean
     */
    public boolean checkSuit(String playerSuit) {
        boolean suitExists = false;
        for (Suit suit : Suit.values()) {
            if (playerSuit.equalsIgnoreCase(suit.name())) {
                suitExists = true;
                break;
            }
        }
        return suitExists;
    }
}
