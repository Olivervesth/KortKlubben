package Rooms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class AppConnection {
	   	
	public void connect() {
		ServerSocket server = null;
		final int port = 5001;
		try {
			server = new ServerSocket(port);
			System.out.println("Server has started on "+InetAddress.getLocalHost().getHostAddress()+":"+port+"\r\nWaiting for a connection...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
	    	if(server != null) {
	    		Socket client = server.accept();
	    		client.close();
	    		System.out.println("A client connected.");
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
}
}
