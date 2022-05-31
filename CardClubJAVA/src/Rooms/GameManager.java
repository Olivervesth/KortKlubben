package Rooms;

import java.util.*;

import Cards.Card;
import GameEngine.EngineManager;
import Players.Player;

public class GameManager {
    /**
     * Fields for GameManager
     */
    private List<Card> playedCards;
    private int[] playerPoints;
    private String trumpSuit;
    private String playingSuit;

    /**
     * Constructor for GameManager
     */
    public GameManager() {
        playedCards = new ArrayList<Card>();
    }

    /**
     * Method to give players cards
     *
     * @param players
     * @param cards
     */
    public void giveCards(List<Player> players, List<Card> cards) {
        for (int i = 0; i < 4; i++) {
            List<Card> cardList = new ArrayList<Card>();
            for (int o = 0; o < 13; o++) {
                int r = new Random().nextInt(0 - cards.size());
                Card c = (Card) cards.toArray()[r];
                cards.remove(c);
                cardList.add(c);
            }
            players.get(i).setCards(cardList);
        }
    }

    /**
     * Method to play a card
     *
     * @param card
     */
    public void playCard(int playerIndex, Card card) {
        // TODO
        // Replace card with string suit string value? to remove connections
        /*List<Card> pCards = playerCards.get(playerIndex);
        pCards.remove(card);
        playerCards.remove(playerIndex);
        playerCards.add(playerIndex, pCards);
        playedCards.add(playerIndex, card);*/
    }

    /**
     * Method to see who WON
     *
     * @param playerList
     */
    public void checkGame(List<Player> playerList) {
        for (Player player : playerList) {//Go through the list of players

            EngineManager.getEngineManager().addGamePlayed(player);//Adds a played game to player data
            if (player.getPoints() >= 5) {//If a player has 5 or more points the player wins
                EngineManager.getEngineManager().addGameWon(player);//Adds a game won to player data
            }
        }
    }

    /**
     * returns the points for players set count
     *
     * @param set
     * @return
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
     * @param playerList
     */
    public void checkRound(List<Player> playerList) {
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
    }


    /**
     * Method to find partners
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
     * @param playerList
     */
    public void checkForSet(List<Player> playerList) {
        int highestCard = 0;
        int playerIndex = -1;

        for (int i = 0; i < 4; i++) {
            String suit = ((Card) (playedCards.toArray()[i])).getSuit();
            int value = ((Card) (playedCards.toArray()[i])).getValue();

            if (suit.equals(playingSuit) || suit.equals(trumpSuit)) {
                if (value > highestCard) {
                    highestCard = value;
                    playerIndex = i;
                }
            }
        }
        playerList.get(playerIndex).setSets(1);
    }

    /**
     * Sets all players' partner to null
     *
     * @param players
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
     * @param players
     */
    private void sortListBySets(List<Player> players) {
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player a1, Player a2) {
                return a1.getSets() - a2.getSets();
            }
        }.reversed());
    }
}
