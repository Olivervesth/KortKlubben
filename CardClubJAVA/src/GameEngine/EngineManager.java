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
import Rooms.ClientThread;
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
            return em;
        } else {
            return em;
        }
    }

    public void addClient(KeyValuePair client) {
        if (!clients.contains(client)) {
            this.clients.add(client);
        }
    }

    public void removeClient(KeyValuePair client) {
        if (clients.contains(client)) {
            this.clients.remove(client);

        }
    }

    public void giveCardsToClient(Player player, List<Card> cards)//stops here cant givecards to client
    {
        for (KeyValuePair client : clients) {
            if (((Player) client.getValue()).equals(player)) {
                try {
                    String hand = "";
                    for (Card card : cards) {
                        hand += card.getValue() + ";" + card.getSuit().name() + ";";
                    }
                    new DataOutputStream(((Socket) client.getKey()).getOutputStream()).writeUTF(hand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static RoomManager getRoomManager() {
        if (roomManager == null) {
            roomManager = new RoomManager();
            return roomManager;
        } else {
            return roomManager;
        }
    }

    /**
     * Method to login
     *
     * @return Player
     */
    public Player login(String username, String password) {
        // if login is success
        if (db.checkLogin(hashing.hash(username), hashing.hash(password))) {
            return playerManager.createPlayer(username, db.getPlayerName(hashing.hash(username)), true);
        }
        // if login fails
        else {
            // log failed attempt?
            return null;
        }
    }

    /**
     * Method to add played game to player statistics
     *
     * @return boolean
     */
    public boolean addGamePlayed(Player player) {
        return db.addGamePlayed(player);
    }

    /**
     * Method to add won game to player statistics
     *
     * @return boolean
     */
    public boolean addGameWon(Player player) {
        return db.addGameWon(player);
    }

    /**
     * Method to reset players points
     *
     * @return List<Player>
     */
    public static List<Player> resetPlayerPoints(List<Player> players) {
        List<Player> returnList = new ArrayList<Player>();
        for (int i = 0; i < players.size(); i++) {
            returnList.add(playerManager.resetPlayerPoints(players.get(i)));
        }
        return returnList;
    }

    /**
     * Method to create a player
     *
     * @return Player
     */
    public Player createPlayer(String username, String playername) {
        return playerManager.createPlayer(hashing.hash(username), playername, true);
    }

    /**
     * @param player
     * @param password
     * @return
     */
    public boolean createUser(Player player, String password) {
        return db.createPlayer(player, hashing.hash(password));
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
    public boolean updateUser(Player player, String newpassword) {
        return db.updatePlayer(player, newpassword);
    }

    /**
     * Method to delete a user
     *
     * @return boolean
     */
    public boolean deleteUser(Player player) {
        return db.deletePlayer(player);
    }

    /**
     * Method to save error messages to ErrorLog table in db
     *
     * @returns boolean
     */
    public boolean saveErrorLog(String action, String message) {
        return logger.saveErrorLog(action, message, db);
    }

    /**
     * Method to save messages to Log table in db
     *
     * @returns boolean
     */
    public boolean saveLog(String action, String message) {
        return logger.saveLog(action, message, db);
    }

}