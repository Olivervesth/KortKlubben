package Rooms;

import Cards.Card;
import Cards.Suit;
import DataModels.KeyValuePair;
import GameEngine.EngineManager;
import Players.Player;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;

import java.time.*;
import java.util.List;

public class ClientThread extends Thread {
    /**
     * Fields
     */
    private EngineManager em = null;
    private RoomManager rm = null;
    private DataInputStream in = null;
    Socket client;
    Room activeroom = null;
    private Player clientPlayer = null;
    DataOutputStream output = null;
    private LocalDateTime lastactivetime;


    /**
     * Constructor for ClientThread
     *
     * @param cli           socket
     * @param engineManager engineManager
     * @param roomManager   roomManager
     */
    public ClientThread(Socket cli, EngineManager engineManager, RoomManager roomManager) {

        em = engineManager;
        rm = roomManager;
        client = cli;
        //Thread t = new Thread((Runnable) lastactivetime);
    }

    /**
     * Run method, starts the thread
     */
    public void run() {
        try {
            output = new DataOutputStream(client.getOutputStream());
            output.writeUTF("true");
        } catch (Exception e) {
            em.saveErrorLog("ClientThread run", "Dataoutputstream" + e);
            e.printStackTrace();
        }
        em.saveLog("Websocket", "New client connected: " + client.getInetAddress());
        System.out.println("New client connected: " + client.getInetAddress() + "");
        boolean clientConnected = true;
        System.out.println("New thread.");
        // takes input from the client socket
        try {
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        } catch (Exception e) {
            em.saveErrorLog("ClientThread run", "DataInputStream" + e);
            e.printStackTrace();
        }
        String line = "";

        // reads message from client until "Done" is sent
        while (clientConnected == true) {
            try {
                line = in.readUTF();
                if (activeroom != null) {
                    if (rm.getRoom(activeroom.getOwner()) == null) {//Checks if the room still exists in room manager
                        activeroom = null;
                    }
                }
                if (activeroom == null) {
                    System.out.println("UserAction activated");
                    output.writeUTF(userActions(line));
                } else {
                    output.writeUTF(playerInGameActions(line));
                }
                System.out.println(line);
            } catch (EOFException eoe) {

            } catch (Exception i) {
                em.saveErrorLog("ClientThread run", "Client loop" + i);
                System.out.println("Client flow whileloop : " + i);
                clientConnected = false;
            }
        }
        try {
            em.removeClient(new KeyValuePair(client, clientPlayer));
        } catch (Exception e) {
            em.saveErrorLog("Thread disconnection error", clientPlayer.getUserName() + " disconnected with error " + e.getMessage());
        }

        em.saveLog("Disconnected", client.getInetAddress().toString());//Log disconnect

    }

    /**
     * Method to read user actions
     *
     * @param command the input from the user
     * @return String
     */
    public String userActions(String command) {
        String[] data = command.split(";");
        System.out.println("data 0 :" + data[0]);
        switch (data[0]) {
            case "login":
                //data[1] = username data[2] = psw
                System.out.println("in login case");
                if (clientPlayer == null) {
                    try {
                        if (data[1] != null && data[2] != null) {
                            System.out.println("data send in login case");
                            clientPlayer = em.login(data[1], data[2]);
                            em.saveLog("Login", client.getInetAddress().toString());
                            if (clientPlayer != null) {
                                em.addClient(new KeyValuePair(client, clientPlayer));
                                System.out.println(clientPlayer.getUserName() + " Logged in");
                                return "true";
                            } else {
                                return "false";
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
                    }
                } else {
                    return "false";
                }
                break;
            case "register":
                //data[1] = playername data[2] = username data[3] = psw
                if (clientPlayer == null) {
                    System.out.println("Register case");
                    try {
                        if (data[1] != null && data[2] != null && data[3] != null) {
                            System.out.println("Creating new user");
                            clientPlayer = em.createPlayer(data[2], data[1]);
                            return String.valueOf(em.createUser(clientPlayer, data[3]));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
                    }
                }
                break;
            case "createroom":
                //data[1] = playername data[2] = username data[3] = psw
                if (clientPlayer != null) {
                    try {
                        if (activeroom == null) {
                            activeroom = rm.createRoom(1, clientPlayer);//fix
                            return "true";
                        } else {
                            return "false";
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
                    }
                } else {
                    return "false";
                }
                break;
            case "getrooms":
                String result = "";
                for (Room room : rm.getRooms()) {
                    result += "Room;" + room.getOwner() + ";" + room.getPlayerCount() + ":";
                }
                if (result == null) {
                    return "false";
                } else if (result != null) {
                    return result;
                }
            case "joinroom":
                /**
                 * data[1] owner name
                 */
                activeroom = rm.getRoom(data[1]);
                if (activeroom != null) {
                    rm.joinRoom(activeroom, clientPlayer);
                    return "true";
                } else if (activeroom == null) {
                    return "false";
                }
                break;
            case "deleteuser":
                /**
                 * data[1] = username
                 */
                if (clientPlayer == null) {
                    if (clientPlayer.getUserName().equals(data[1])) {
                        System.out.println("Delete case");
                        try {
                            if (data[1] != null) {
                                return String.valueOf(em.deleteUser(clientPlayer));
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
                        }
                    }
                }
                break;
            case "updateuser":
                /**
                 * data[1] = type data[2] = playername data[3] = username data[4] = psw
                 * data[1] = type data[2] = playername data[3] = username
                 */
                if (clientPlayer == null) {
                    if (clientPlayer.getUserName().equals(data[3])) {
                        System.out.println("Update Case");
                        try {
                            switch (data[1]) {
                                case "psw":
                                    if (data[4] != null) {
                                        return String.valueOf(em.updateUser(clientPlayer, data[4]));
                                    }
                                    break;
                                case "Playername":
                                    if (data[2] != null) {
                                        clientPlayer.setPlayerName(data[2]);
                                        return String.valueOf(em.updateUser(clientPlayer, null));
                                    }
                                    break;
                            }
                            if (data[1] != null && data[2] != null && data[3] != null) {
                            }
                        } catch (Exception e) {
                            em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
                        }
                    }
                }
                break;
            default:
                break;
        }
        return "false";
    }

    /**
     * Method to read player actions in a game
     *
     * @param command
     */
    public String playerInGameActions(String command) {

        String[] data = command.split(";");
        switch (data[0]) {
            case "leave":
                rm.destroyRoom(activeroom);
                activeroom = null;
                return "true";
            case "playcard":
                /**
                 * data[1] = card number
                 * data[2] = suit
                 */
                try {
                    activeroom.checkSuit(data[2]);
                    String d2 = data[2].substring(0, 1).toUpperCase() + data[2].substring(1);
                    Card card = new Card(Integer.valueOf(data[1]), Suit.valueOf(d2));
                    activeroom.playCard(clientPlayer, card);
                    return "true";
                } catch (Exception e) {
                    e.printStackTrace();
                    em.saveErrorLog("clientThread activeroom.playCard", e.getMessage());
                }
                break;
            default:
                break;
        }
        return "false";
    }

    /**
     * Method to grant cards
     *
     * @param cards
     * @return
     */
    public boolean giveCards(List<Card> cards) {
        try {
            String hand = "";
            for (Card card : cards) {
                hand += card.getValue() + ";" + card.getSuit().name() + ";";
            }
            output.writeUTF("hand;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to get the client player
     *
     * @return Player clientPlayer
     */
    public Player getClientPlayer() {
        return clientPlayer;
    }

//    public void lastActive(){
//        while(true){//Check if player is deleted
//            LocalDateTime d = LocalDateTime.now();
//            long diff = MINUTES.between(d, lastactivetime);
//            if (diff > 2){
//
//            }
//        }
//
//    }
}
