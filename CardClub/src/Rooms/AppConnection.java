package Rooms;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import GameEngine.EngineManager;
import Rooms.RoomManager;

public class AppConnection {
	/**
	 * Fields
	 */
	private Socket socket = null;
	private EngineManager em = null;
	private RoomManager rm = null;
	private ServerSocket server = null;
	private DataInputStream in = null;

	int clientsconnected = 0;

	// constructor with port
	/**
	 * Constructor for AppConnection
	 * 
	 * @param int port
	 */
	public AppConnection(int port) {
		// starts server and waits for a connection
		try {
			em = new EngineManager();
			rm = new RoomManager();
			server = new ServerSocket(port);
			println("Server started: Ip " + InetAddress.getLocalHost().getHostAddress() + ",Port "
					+ server.getLocalPort() + ":");
			System.out.println("Waiting for a client ........");

			while (!server.isClosed()) {
				try {
					socket = server.accept();
					System.out.println("Client accepted.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if (server.isClosed()) {
						System.out.println("Server Stopped.");
					}
					throw new RuntimeException("Error accepting client connection", e);
				}
				// Start new thread when a new client joins
				new Thread(new ClientThread(socket, em, rm)).start();
				System.out.println("" + clientsconnected + "");
			}
			// close connection
			socket.close();
			in.close();
		} catch (IOException i) {
			System.out.println(i);
			try {
				socket.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void println(String string) {
		System.out.println(string);
	}

}
