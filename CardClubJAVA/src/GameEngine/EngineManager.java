package GameEngine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Cards.Card;
import DataModels.KeyValuePair;
import Players.Player;
import Players.PlayerManager;
import Rooms.RoomManager;

public final class EngineManager {
    /**
     * Fields
     */
    private static Hashing hashing;
    private static Logger logger;
    private static DbManager db;
    private static PlayerManager playerManager;
    private static RoomManager roomManager;
    private static EngineManager em;
    private List<KeyValuePair> clients = null;

    /**
     * Constructor for EngineManager
     */
    public EngineManager() {
        em = this;
        clients = new ArrayList<>();
        logger = new Logger();
        hashing = new Hashing();
        db = new DbManager();
        playerManager = new PlayerManager();
        roomManager = new RoomManager();
    }

    public static EngineManager getEngineManager() {
        if (em == null) {
            em = new EngineManager();
        }
        return em;
    }

    /**
     * Adds a client to the list of clients
     *
     * @param client client to add
     */
    public void addClient(KeyValuePair client) {
        if (!clients.contains(client)) {
            this.clients.add(client);
        }
    }

    /**
     * Removes a client from the list of clients
     *
     * @param client client to remove
     */
    public void removeClient(KeyValuePair client) {
        if (clients.contains(client)) {
            this.clients.remove(client);

        }
    }

    /**
     * Method to deal cards to a specific player
     *
     * @param player player to give cards to
     * @param cards  cards for the player
     */
    public void giveCardsToClient(Player player, List<Card> cards)//stops here can't give cards to client
    {
        for (KeyValuePair client : clients) {
            if (((Player) client.getValue()).getUserName().equals(player.getUserName())) {
                try {
                    String hand = "";
                    for (Card card : cards) {
                        hand += card.getValue() + ";" + card.getSuit().name() + ";";
                    }
                    System.out.println(player.getPlayerName()+";"+hand);
                    new DataOutputStream(((Socket) client.getKey()).getOutputStream()).writeUTF(hand);
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to register that a card has been played
     *
     * @param player player playing a card
     * @param card   the card being played
     */
    public void cardPlayed(Player player, Card card) {
        for (KeyValuePair client : clients) {
            if (!((Player) client.getValue()).getUserName().equals(player.getUserName())) {
                try {
                    String response = card.getValue() + ";" + card.getSuit().name();
                    DataOutputStream output = new DataOutputStream(((Socket) client.getKey()).getOutputStream());
                    output.writeUTF(response);
                } catch (IOException e) {
                    em.saveErrorLog("EngineManager cardPlayed",e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to message a single player
     *
     * @param player player to message
     * @param msg the message
     */
    public void msgPlayer(Player player, String msg) {
        for (KeyValuePair client : clients) {
            if (((Player) client.getValue()).getUserName().equals(player.getUserName())) {
                try {
                    new DataOutputStream(((Socket) client.getKey()).getOutputStream()).writeUTF(msg);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to instantiate a new RoomManager
     *
     * @return RoomManager
     */
    public static RoomManager getRoomManager() {
        if (roomManager == null) {
            roomManager = new RoomManager();
        }
        return roomManager;
    }

    /**
     * Method to login
     *
     * @return Player
     */
    public Player login(String username, String password) {
        // if login is success
        if (db.checkLogin(hashing.hash(username), hashing.hash(password))) {
            saveLog("Login", "Player " + username + " logged in successfully");
            return playerManager.createPlayer(username, db.getPlayerName(hashing.hash(username)), true);
        }
        // if login fails
        else {
            saveErrorLog("Login", "Player " + username + " failed to login");
            return null;
        }
    }

    /**
     * Method to add played game to player statistics
     *
     * @return boolean
     */
    public boolean addGamePlayed(Player player) {
        return db.addGamePlayed(hashing.hash(player.getUserName()));
    }

    /**
     * Method to add won game to player statistics
     *
     * @return boolean
     */
    public boolean addGameWon(Player player) {
        return db.addGameWon(hashing.hash(player.getUserName()));
    }

    /**
     * Method to reset players points
     *
     * @return List<Player>
     */
    public static List<Player> resetPlayerPoints(List<Player> players) {
        List<Player> returnList = new ArrayList<>();
        for (Player player : players) {
            returnList.add(playerManager.resetPlayerPoints(player));
        }
        return returnList;
    }

    /**
     * Method to create a player
     *
     * @return Player
     */
    public Player createPlayer(String username, String playerName) {
        return playerManager.createPlayer(hashing.hash(username), playerName, true);
    }

    /**
     * Method for saving a new user to the db
     *
     * @param player   the user to be created
     * @param password string of new password
     * @return boolean
     */
    public boolean createUser(Player player, String password) {
        if (db.createPlayer(player, hashing.hash(password))) {
            saveLog("CreateUser", "User " + player.getUserName() + " create successfully");
            return true;
        } else {
            saveErrorLog("CreateUser", "Failed to create user " + player.getUserName());
            return false;
        }

    }

    /**
     * Method to get player stats
     *
     * @return String[]
     */
    public String[] getStats(Player player) {
        return db.getStats(player);
    }

    /**
     * Method to update a user
     *
     * @return boolean
     */
    public boolean updateUser(Player player, String newPassword) {
        boolean result = false;
        if(newPassword.equals("")){
            result = db.updatePlayer(player, null);
        }
        else{
            result = db.updatePlayer(player, hashing.hash(newPassword));
        }

        if (result) {
            saveLog("UpdateUser", "User " + player.getUserName() + " updated successfully");
            return true;
        } else {
            saveErrorLog("UpdateUser", "User " + player.getUserName() + "failed to update");
            return false;
        }
    }

    /**
     * Method to delete a user
     *
     * @return boolean
     */
    public boolean deleteUser(Player player) {
        if (db.deletePlayer(player)) {
            saveLog("DeletePlayer", "User " + player.getUserName() + " was deleted");
            return true;
        } else {
            saveErrorLog("DeletePlayer", "Failed to delete user " + player.getUserName());
            return false;
        }
    }

    /**
     * Method to save error messages to db
     */
    public void saveErrorLog(String action, String message) {
        logger.saveErrorLog(action, message, db);
    }

    /**
     * Method to save messages to db
     */
    public void saveLog(String action, String message) {
        logger.saveLog(action, message, db);
    }

}