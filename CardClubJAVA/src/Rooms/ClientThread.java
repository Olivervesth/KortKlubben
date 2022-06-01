package Rooms;

import Cards.Card;
import GameEngine.EngineManager;
import Players.Player;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
    private Player clientplayer = null;
    DataOutputStream output = null;

    /**
     * Constructor for ClientThread
     * @param cli
     * @param enginemanager
     * @param roommanager
     */
    public ClientThread(Socket cli, EngineManager enginemanager, RoomManager roommanager) {

        em = enginemanager;
        rm = roommanager;
        client = cli;
    }
    /**
     * Run method, starts the thread
     */
    public void run() {
        try {
            output = new DataOutputStream(client.getOutputStream());
            output.writeUTF("true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        em.saveErrorLog("Websocket", "New client connected: " + client.getInetAddress() + "");
        System.out.println("New client connected: " + client.getInetAddress() + "");
        boolean clientconnected = true;
        System.out.println("New thread.");
        // takes input from the client socket
        try {
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String line = "";

        // reads message from client until "Done" is sent


        while (!line.equals("Done") && clientconnected == true) {
            try {
                //Send message to client

                line = in.readUTF();
                if(activeroom == null) {
                    System.out.println("UserAction activated");
                            output.writeUTF(userActions(line));
                }else {
                    output.writeUTF(playerInGameActions(line));
                }
                System.out.println(line);

            } catch (IOException i) {
                System.out.println(i);
                clientconnected = false;
            }
        }
        em.removeClient(client);
        em.saveLog("Disconnected", client.getInetAddress().toString());//Log disconnect
    }

    /**
     * @param command
     * @return
     */
    public String userActions(String command) {
        String[] data = command.split(";");
        System.out.println("data 0 :" + data[0]);
        switch (data[0]) {
            case "login":
                /**
                 * data[1] = username data[2] = psw
                 */
                System.out.println("in login case");
                if(clientplayer == null) {
                    try{
                        if(data[1] != null && data[2] != null) {
                            System.out.println("data send in login case");
                            clientplayer = em.login(data[1], data[2]);
                            em.saveLog("Login", client.getInetAddress().toString());
                            if(clientplayer != null) {
                                System.out.println(clientplayer.getUserName()+" Logged in");
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
                /**
                 * data[1] = playername data[2] = username data[3] = psw
                 */
                if (clientplayer == null) {
                    System.out.println("Register case");
                    try {
                        if (data[1] != null && data[2] != null && data[3] != null) {
                            System.out.println("Creating new user");
                            clientplayer = em.createPlayer(data[2], data[1]);
                            return String.valueOf(em.createUser(clientplayer, data[3]));
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
                    }

                }

                break;
            case "createroom":
                /**
                 * data[1] = playername data[2] = username data[3] = psw
                 */

                if(clientplayer != null) {
                    try{
                        if(activeroom == null) {
                            activeroom = rm.createRoom(1,clientplayer);//fix
                            return "true";
                        }else {
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
                for (Room room:rm.getRooms()) {
                    result += "Room;"+room.getOwner()+";"+room.getPlayerCount()+":";
                }
//                if (result == null){
//                    return "false";
//                }else if (result != null){
//                }
                return result;
            case "joinroom":
                /**
                 * data[1] owner name
                 */
                activeroom =  rm.getRoom(data[1]);
                if(activeroom != null){
                    rm.joinRoom(activeroom,clientplayer);
                    return "true";
                }else if(activeroom == null){
                    return "false";
                }
                break;
            default:
                break;
        }
        return "false";
    }

    /**
     *
     * @param command
     */
    public String playerInGameActions(String command) {

        String[] data = command.split(";");
		switch(data[0]) {
            case "leave":
                rm.destroyRoom(activeroom,clientplayer);
                activeroom = null;
                return "true";
            case "playcard":
                /**
                 * data[1] = card
                 */
                    clientplayer.getCards().contains(data[1]);
//                    rm.playCard(clientplayer,data[1]);
                    break;
            default:
                break;



		}
        return "false";
    }
    public boolean giveCards(List<Card> cards){
        try {
            String hand = "";
            for (Card card:cards) {
                hand+=card.getValue()+";"+card.getSuit().name()+";";
            }
            output.writeUTF("hand;");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public Player getClientPlayer(){
        return clientplayer;
    }
}
