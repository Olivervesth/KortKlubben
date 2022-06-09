package Rooms;

import java.util.ArrayList;
import java.util.List;

import Cards.CardManager;
import GameEngine.EngineManager;
import Players.Player;

/**
 * Class for handling and creating Rooms
 */
public class RoomManager {
    /**
     * Fields
     */
    private List<Room> rooms;

    /**
     * Constructor for RoomManager
     */
    public RoomManager() {
        rooms = new ArrayList<>();
    }

    /**
     * Method to create a new room
     *
     * @param gameType int to determine card game
     * @param owner    Creator of room
     * @return Room
     */
    public Room createRoom(int gameType, Player owner) {
        Room room = null;
        // What type of game is the room playing?
        // currently Whist only
        switch (gameType) {
            // Whist
            case 1:
                room = new Room(owner, new GameManager(), new CardManager());
                break;
            // Poker?
            case 2:
                break;
            // Blackjack?
            case 3:
                break;
        }
        if (room != null)
            rooms.add(room);
        return room;
    }

    /**
     * Method to reset players in a room
     *
     * @param players players to reset
     * @return List of players that have been reset
     */
    public List<Player> resetPlayers(List<Player> players) {
        return EngineManager.resetPlayerPoints(players);
    }

    /**
     * Method for getting a list of all available rooms
     *
     * @return List of Room
     */
    public List<Room> getRooms() {
        List<Room> roomList = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getPlayerCount() < 4) {
                roomList.add(room);
            }
        }
        return roomList;
    }

    /**
     * Method to get room object from owner
     *
     * @param owner creator of room
     * @return Room
     */
    public Room getRoom(String owner) {
        for (Room room : rooms) {
            if (room.getOwner().equals(owner)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Method for a player to join a room
     *
     * @param room   room to join
     * @param player player joining
     */
    public void joinRoom(Room room, Player player) {
        room.addPlayer(player);
    }

    /**
     * Method to destroy an active room
     *
     * @param room room to be destroyed
     */
    public void destroyRoom(Room room) {
        rooms.remove(room);
    }


}
