package Rooms;

import java.util.ArrayList;
import java.util.List;

import Cards.Card;
import Cards.CardManager;
import GameEngine.EngineManager;
import Players.Player;

/**
 * Object class representing a room to play in
 */
public class Room {
    /**
     * Fields for Room class
     */
    private List<Player> players;
    private final Player owner;
    private final GameManager gameManager;
    private final CardManager cardManager;
    private int playerTurn;
    private boolean firstCard;
    private boolean gameDone = false;

    /**
     * Constructor for Room class
     *
     * @param owner       user that created the room
     * @param gameManager managed game logic
     * @param cardManager manages card creation
     */
    public Room(Player owner, GameManager gameManager, CardManager cardManager) {
        players = new ArrayList<>();
        this.owner = owner;
        players.add(owner);
        this.gameManager = gameManager;
        this.cardManager = cardManager;
        this.firstCard = true;
    }

    /**
     * Method to give players cards
     */
    private void giveCards() {
        players = gameManager.giveCards(players, cardManager.generateCardDeckNoJokers());
        for (Player player : players) {
            EngineManager.getEngineManager().giveCardsToClient(player, player.getCards());
        }
    }

    /**
     * Method to check a suit
     *
     * @param input the suit of the card
     */
    public void checkSuit(String input) {
        gameManager.checkSuit(input);
    }

    /**
     * Method to get the amount of players
     *
     * @return int playerCount
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Get the room owners playerName
     *
     * @return string
     */
    public String getOwner() {
        return owner.getPlayerName();
    }

    /**
     * Add player to room
     *
     * @param player player to add
     */
    public void addPlayer(Player player) {
        // TODO handle 4 players not 2
        if (players.size() < 4) {
            if (!players.contains(player)) {
                players.add(player);
                if (players.size() == 2) {
                    giveCards();
                    // TODO better turn msg/handling
                    EngineManager.getEngineManager().msgPlayer(players.get(playerTurn), "YourTurn");
                }
            }
        }
    }

    /**
     * Remove player from room
     *
     * @param player player to remove
     */
    public void removePlayer(Player player) {
        if (players.size() > 0) {
            if (players.contains(player)) {
                players.remove(player);
                //TODO version 1.2 don't destroy room on player leaving/kicked
                EngineManager.getRoomManager().destroyRoom(this);
            }
        }
    }

    /**
     * Method that handles a player playing a card
     *
     * @param player player playing a card
     * @param card   card being played
     */
    public void playCard(Player player, Card card) {
        if (firstCard) {
            gameManager.findPartners(player, card, players);
            firstCard = false;
        }
        if (players.get(playerTurn).getUserName().equals(player.getUserName())) { //"Method will fail" at third playerTurn
            for (Card c : player.getCards()) {
                if (c.getValue() == card.getValue() && c.getSuit() == card.getSuit()) {
                    gameManager.playCard(player, card);
                    changeTurn();
                    break;
                }
            }
        }
    }

    /**
     * Method to change turn
     * Handles calls for checking rounds and game
     */
    public void changeTurn() {
        // TODO handle 4 players not 2
        if (!gameDone){
            if (playerTurn >= 2) {
                gameManager.checkForSet(players);
                if (players.get(0).getCards().size() == 0) {
                    gameDone = gameManager.checkRound(players);
                    if (!gameDone){
                        for (Player player : players) {
                            System.out.println(player.getPlayerName() + " has: " + player.getPoints() + " points.");
                            System.out.println(player.getPlayerName() + " has: " + player.getSets() + " sets.");
                            EngineManager.getEngineManager().msgPlayer(player, String.valueOf(player.getSets()));
                            player.resetSets();
                        }
                        firstCard = true;
                        giveCards();

                    }
                }
                if(!gameDone){
                    playerTurn = -1;
                    changeTurn();
                }
            } else {
                // TODO handle turn string
                playerTurn++;
                if (playerTurn < 2) {
                    EngineManager.getEngineManager().msgPlayer(players.get(playerTurn), "YourTurn");
                } else {
                    changeTurn();
                }
            }

        }
    }

}
