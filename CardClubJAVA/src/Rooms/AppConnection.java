package Rooms;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import GameEngine.EngineManager;

/**
 * Class for handling connection between app and server
 */
public class AppConnection {

    /**
     * Constructor for AppConnection
     *
     * @param port port number
     */
    public AppConnection(int port) {
        // starts server and waits for a connection
        Socket socket = null;
        DataInputStream in = null;
        try {
            List<Thread> clientThreads = new ArrayList<>();
            EngineManager em = new EngineManager();
            RoomManager rm = EngineManager.getRoomManager();
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started: Ip " + InetAddress.getLocalHost().getHostAddress() + ",Port "
                    + server.getLocalPort() + ":");
            System.out.println("Waiting for a client ........");

            while (!server.isClosed()) {
                try {
                    socket = server.accept();
                    socket.setKeepAlive(true);
                    System.out.println("Client accepted.");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    if (server.isClosed()) {
                        System.out.println("Server Stopped.");
                    }
                    e.printStackTrace();
                }
                // Start new thread when a new client joins
                Thread t = new Thread(new ClientThread(socket, em, rm));
                t.start();
                clientThreads.add(t);

                System.out.println("Clients connected " + clientThreads.size());
            }
            // close connection
            if (socket != null) {
                socket.close();
            }
            in.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            try {
                if (socket != null) {
                    socket.close();
                }
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
