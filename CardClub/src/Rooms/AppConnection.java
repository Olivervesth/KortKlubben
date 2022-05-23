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


public class AppConnection {
	  //initialize socket and input stream
	  private Socket socket = null;
	  private ServerSocket server = null;
	  private DataInputStream in = null;
	  private EngineManager em = null;
	  // constructor with port
	  public AppConnection(int port) {
			// starts server and waits for a connection
	    try {
	    	em = new EngineManager();
	      server = new ServerSocket(port);
	      System.out.println("Server started: Ip "+InetAddress.getLocalHost().getHostAddress()+",Port "+server.getLocalPort()+":");

	      System.out.println("Waiting for a client ........");

	      while(!server.isClosed()) {
	    	  try {
	    		  socket = server.accept();
	    		  em.saveErrorLog("Websocket","New client connected: "+socket.getInetAddress()+"");
	    		  System.out.println("Client accepted.");
	    	  } catch (IOException e) {
	    		  // TODO Auto-generated catch block
	    		  if(server.isClosed()) {
	                  System.out.println("Server Stopped.") ;
	              }
	    		  throw new RuntimeException(
	    	                "Error accepting client connection", e);
	    	  }
	    	  
	    	  Thread t = new Thread(()->Threadedwebsocket(socket));
	    	  t.start();
//	    	  new Thread(new Runnable() {
//	    	         public void run() {
//	    	        	
//	    	         }
//	    	      }).start();
	    	
	    	  
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
	  public void Threadedwebsocket(Socket socket) {
		  Socket client = socket;
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
	    			  System.out.println(line);
	    			  
	    		  } catch (IOException i) {
	    			  System.out.println(i);
	    			  clientconnected = false;
	    		  }
	    	  }
	    	  System.out.println("Closing connection to "+client.getInetAddress()+"");
	  }

//	final int port = 5010
//			;
//	public void connect() {
//		try {
//			ServerSocket server = new ServerSocket(port);
//			System.out.println("Server has started on "+InetAddress.getLocalHost().getHostAddress()+":"+port+"\r\nWaiting for a connection...");
//			newClient(server);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	    
//	}
//	public void test() {
//
//			try {
//				ServerSocket serverSocket = new ServerSocket(port);
//				Socket clientSocket;
//				clientSocket = serverSocket.accept();
//				PrintWriter out =new PrintWriter(clientSocket.getOutputStream(), true);
//				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//				serverSocket.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	}
//	public void newClient(ServerSocket server ) { 
//		Thread newThread = new Thread(() -> {
//			
//			Socket client = null;
//			DataInputStream din = null;
//			try {
//				if(server != null) {
//					 
//					client = server.accept();
//					din=new DataInputStream(client.getInputStream());  
//					DataOutputStream dout=new DataOutputStream(client.getOutputStream());  
//					BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
//					  
//					String str="",str2="";  
//					while(!str.equals("stop")){  
//						System.out.println("A client with the address "+client.getInetAddress()+" is now connected");
//						str=din.readUTF();  
//						System.out.println("client says: "+str);  
//						str2=br.readLine();  
//						dout.writeUTF(str2);  
//						dout.flush();  
//						clienttest();
//					}  
//				}
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				try {
//					din.close();
//					client.close();  
//					server.close();  
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}  
//			}
//		});
//		newThread.start();
//		
//	}
//	
//	public void clienttest(){
//		Socket s = null;
//		try {
//			s = new Socket("10.108.130.90",port);
//			System.out.println("Hello");
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		DataInputStream din = null;
//		try {
//			din = new DataInputStream(s.getInputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		DataOutputStream dout = null;
//		try {
//			dout = new DataOutputStream(s.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
//		  
//		String str="",str2="";  
//		while(!str.equals("stop")){  
//		try {
//			str=br.readLine();
//			dout.writeUTF(str);  
//			dout.flush();  
//			str2=din.readUTF();  
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		System.out.println("Server says: "+str2);  
//		}  
//		  
//		try {
//			dout.close();
//			s.close();  
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		}
}
