package Rooms;

import java.util.ArrayList;
import java.util.List;

import Cards.Card;
import Cards.CardManager;
import GameEngine.EngineManager;
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
    }

    /**
     * Method to give players cards
     */
    private void giveCards()
    {
       players = gameManager.giveCards(players, cardManager.generateCardDeckNoJokers());
       for (Player player : players)
       {
           EngineManager.getEngineManager().giveCardsToClient(player,player.getCards());
       }
    }
    public int getPlayerCount(){
        return players.size();
    }

    /**
     * Get the room owners playername
     * @return
     */
    public String getOwner(){
        return owner.getPlayerName();
    }

    /**
     * Add player to room
     * @param player
     * @return
     */
    public boolean addPlayer(Player player){
        if(players.size()<4){
            if(!players.contains(player)){
                players.add(player);
                if(players.size() == 2){
                    giveCards();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Remove player from room
     * @param player
     * @return
     */
    public boolean removePlayer(Player player){
        if (players.size() >0){
            if(players.contains(player)){
                players.remove(player);
                return true;
            }
        }
        return false;
    }
    public void playCard(Player player, Card card){
        gameManager.playCard(player,card);
    }
}
