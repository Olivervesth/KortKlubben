package GameEngine;

import Rooms.AppConnection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DbManager db = new DbManager();
//		db.ConnectDb();
		AppConnection websocket = new AppConnection();
		websocket.connect();
	}

}
