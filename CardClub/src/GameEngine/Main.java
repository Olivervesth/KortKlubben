package GameEngine;

import Rooms.AppConnection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Start listening for server connections
		AppConnection server = new AppConnection(5001);

	}
}
