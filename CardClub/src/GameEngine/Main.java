package GameEngine;

import Rooms.AppConnection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Start listening for server connections
		//AppConnection server = new AppConnection(5004);
		Client c = new Client("10.108.137.77",5004);
	}
}
