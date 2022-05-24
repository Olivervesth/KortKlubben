package GameEngine;

//import java.io.FileWriter;
//import java.io.IOException;

/**
 * Class to log errors
 * 
 * @author Martin, Jesper
 */
public class Logger {
	
	public boolean saveLog(String action, String message, DbManager db) {
		return db.createLog(action, message);
	}
	

	/**
	 * Method to log an error message to local file
	 * 
	 * @param String errormessage
	 */
//	public void saveMessage(String errormessage, DbManager db) {
//		String homeDir = System.getProperty("user.home");
//		try {
//			// filepath and name
//			FileWriter fWriter = new FileWriter((homeDir + "/CardClubErrorLog.txt"), true);
//			fWriter.append(errormessage + System.lineSeparator());
//			fWriter.close();
//			System.out.println("Successfully wrote to the file.");
//		} catch (IOException e) {
//			System.out.println("An error occurred.");
//			e.printStackTrace();
//		}
//	}
}
