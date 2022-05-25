package GameEngine;

import Rooms.AppConnection;

public class Main {

	/**
	 * Starting point of server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Start listening for server connections
		AppConnection server = new AppConnection(5004);
	}
}
