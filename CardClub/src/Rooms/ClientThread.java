package Rooms;
import GameEngine.EngineManager;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
	private EngineManager em = null;
	private RoomManager rm = null;
	private DataInputStream in = null;
	Socket client;
	Room activeroom = null;
	public ClientThread(Socket cli,EngineManager enginemanager,RoomManager roommanager ) {
		em = enginemanager;
		rm = roommanager;
		client = cli;
	}
	public void run() {
		em.saveErrorLog("Websocket","New client connected: "+client.getInetAddress()+"");
     	 System.out.println("New client connected: "+client.getInetAddress()+"");
     	 boolean clientconnected = true;
     	 System.out.println("New thread.");
	    	  // takes input from the client socket
	    	  try {
				in = new DataInputStream(
						  new BufferedInputStream(client.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	    	  String line = "";
	    	  
	    	  // reads message from client until "Done" is sent
	    	  
	    	  while (!line.equals("Done") && clientconnected == true) {
	    		  try {
	    			  //Send message to client
	    			 
	    			  DataOutputStream output = new DataOutputStream(client.getOutputStream());
	    			  line = in.readUTF();
	    			  if(activeroom == null) {
	    				  System.out.println("UserAction activated");
	    				  if(UserActions(line)) {
	    	    			  output.writeUTF("true");
	    				  }else {
	    					  output.writeUTF("false");
	    				  }
	    			  }else {
	    				  PlayerInGameActions(line);
	    			  }
	    			  System.out.println(line);
	    			  
	    		  } catch (IOException i) {
	    			  System.out.println(i);
	    			  clientconnected = false;
	    		  }
	    	  }
	    	  System.out.println("Closing connection to "+client.getInetAddress()+"");
	}
	/**
	 * 
	 * @param command
	 * @return
	 */
	public boolean UserActions(String command) {
		String[] data = command.split(";");
		System.out.println("data 0 :"+data[0]);
		switch(data[0]) {
		case "login":
			/**
			 * data[1] = username
			 * data[2] = psw
			 */
			System.out.println("in login case");
			
			try{
				if(data[1] != null && data[2] != null) {
					
					System.out.println("data send in login case");
					return em.login(data[1], data[2]);
				}
				
			}catch(ArrayIndexOutOfBoundsException e) {
				em.saveErrorLog(this.getName(), e.getLocalizedMessage()+""+e.getMessage());
			}
			
			break;
			
		case "register":
			/**
			 * data[1] = playername
			 * data[2] = username
			 * data[3] = psw
			 */
			System.out.println("Register case");
			try{
				if(data[1] != null && data[2] != null && data[3] != null) {
					System.out.println("Creating new user");
					return em.createUser(data[1], data[2], data[3]);
				}
				
			}catch(ArrayIndexOutOfBoundsException e) {
				em.saveErrorLog(this.getName(), e.getLocalizedMessage()+""+e.getMessage());
			}
					break;
		case "createroom":
			try{
				if(data[1] != null) {
					activeroom = rm.createRoom(1,em.createPlayer(data[1]));
					if(activeroom != null) {
						return true;
					}
				}
				
			}catch(ArrayIndexOutOfBoundsException e) {
				em.saveErrorLog(this.getName(), e.getLocalizedMessage()+""+e.getMessage());
			}
			break;

		default:
			break;
		}
		return false;
	}
	
	public void PlayerInGameActions(String command) {
		String[] data = command.split(";");
//		switch(data[0]) {
//			activeroom.giveCards();
//		
//		}
	} 
}
