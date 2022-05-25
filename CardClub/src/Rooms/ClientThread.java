package Rooms;

import GameEngine.EngineManager;
import Players.Player;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
	/**
	 * Fields
	 */
	private EngineManager em = null;
	private RoomManager rm = null;
	private DataInputStream in = null;
	Socket client;
	Room activeroom = null;
	Player clientplayer = null;

	/**
	 * Constructor for ClientThread
	 * 
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
				// Send message to client

				DataOutputStream output = new DataOutputStream(client.getOutputStream());
				line = in.readUTF();
				if (activeroom == null) {
					System.out.println("UserAction activated");
					if (userActions(line)) {
						output.writeUTF("true");
					} else {
						output.writeUTF("false");
					}
				} else {
					playerInGameActions(line);
				}
				System.out.println(line);

			} catch (IOException i) {
				System.out.println(i);
				clientconnected = false;
			}
		}
		System.out.println("Closing connection to " + client.getInetAddress() + "");
	}

	/**
	 * 
	 * @param String command
	 * @return Different response from server
	 */
	public boolean userActions(String command) {
		String[] data = command.split(";");
		System.out.println("data 0 :" + data[0]);
		switch (data[0]) {
		case "login":
			/**
			 * data[1] = username data[2] = psw
			 */
			System.out.println("in login case");
			if (clientplayer == null) {
				try {
					if (data[1] != null && data[2] != null) {

						System.out.println("data send in login case");
						clientplayer = em.login(data[1], data[2]);
						System.out.println(clientplayer.getUserName() + " Logged in");
						if (clientplayer != null) {
							return true;

						} else {
							return false;
						}
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
				}

			} else {
				return false;
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
						return em.createUser(clientplayer, data[3]);
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
			if (clientplayer != null) {
				try {
					activeroom = rm.createRoom(1, clientplayer);// fix
					if (activeroom != null) {
						return true;
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					em.saveErrorLog(this.getName(), e.getLocalizedMessage() + "" + e.getMessage());
				}

			} else {
				return false;
			}
			break;

		default:
			break;
		}
		return false;
	}

	/**
	 * 
	 * @param String command
	 */
	public void playerInGameActions(String command) {
		String[] data = command.split(";");
//		switch(data[0]) {
//			activeroom.giveCards();
//		
//		}
	}
}
