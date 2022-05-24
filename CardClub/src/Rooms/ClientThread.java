package Rooms;
import GameEngine.EngineManager;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
	Socket client;
	 private EngineManager em = null;
	 private RoomManager rm = null;
	private DataInputStream in = null;
	public ClientThread(Socket cli) {
		em = new EngineManager();
		rm = new RoomManager();
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
	    			  output.writeUTF("200");
	    			  line = in.readUTF();
	    			  PlayerActions(line);
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
	public String PlayerActions(String command) {
		String[] data = command.split(";");
		
		switch(data[0]) {
		case "login":
			System.out.println("in login case");
			/**
			 * data[1] = playername
			 * data[2] = username
			 * data[3] = psw
			 */
			try{
				if(data[1] != null && data[2] != null && data[3] != null) {
					
					System.out.println("data send in login case");
					return em.login(data[2], data[3])? "true" : "false";
				}
				
			}catch(ArrayIndexOutOfBoundsException e) {
				em.saveErrorLog(this.getName(), e.getLocalizedMessage()+""+e.getMessage());
			}
			
			break;
			
		case "register":
			try{
				if(data[1] != null && data[2] != null && data[3] != null) {
					
					return em.createUser(data[1], data[2], data[3])? "true" : "false";
				}
				
			}catch(ArrayIndexOutOfBoundsException e) {
				em.saveErrorLog(this.getName(), e.getLocalizedMessage()+""+e.getMessage());
			}
					break;
		case "createroom":
//			try{
//				if(data[1] != null && data[2] != null && data[3] != null) {
//					return rm.createRoom(1,data[1])? "true" : "false";
//				}
//				
//			}catch(ArrayIndexOutOfBoundsException e) {
//				em.saveErrorLog(this.getName(), e.getLocalizedMessage()+""+e.getMessage());
//			}
//			break;

		default:
			return "";
		}
	}
}
