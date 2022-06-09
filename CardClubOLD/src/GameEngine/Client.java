package GameEngine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	/**
	 * Fields
	 */
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private DataInputStream res = null;

	/**
	 * Constructor for Client
	 * 
	 * @param String address
	 * @param int    port
	 */
	public Client(String address, Integer port) {

		// code to establish a connection
		try {
			socket = new Socket(address, port);
			input = new DataInputStream(System.in);
			res = new DataInputStream(socket.getInputStream());

			// sends output to the socket
			output = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String line = "";
		// below line is to read message from input
		while (!(line.equals("Done"))) {
			try {

				System.out.println(res.readUTF());
				line = input.readLine();
				output.writeUTF(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// below code to close the connection
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}