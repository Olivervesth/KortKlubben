package Rooms;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

import Cards.Card;
import Cards.CardManager;
import GameEngine.EngineManager;
import Players.Player;

public class RoomManager {
    /**
     * Fields
     */
    private List<Room> rooms;

    /**
     * Constructor for RoomManager
     */
    public RoomManager() {
        rooms = new ArrayList<Room>();
    }

    /**
     * Method to create a new room
     * @param gameType
     * @param owner
     * @return
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
     * @param players
     * @return
     */
    public static List<Player> resetPlayers(List<Player> players) {
        return EngineManager.resetPlayerPoints(players);
    }
    public List<Room> getRooms(){
        List<Room> roomlist = new ArrayList<>();
        for (Room room: rooms) {
            if (room.getPlayerCount() <4){
                roomlist.add(room);
            }
        }
        return roomlist;
    }
    public Room getRoom(String owner){
        for (Room room:rooms) {
            if(room.getOwner().equals(owner)){
                return room;
            }
        }
        return null;
    }
    public boolean joinRoom(Room room,Player player){
        return room.addPlayer(player);
    }
    public boolean destroyRoom(Room room,Player player){
        rooms.remove(room);
        return true;
    }


}
