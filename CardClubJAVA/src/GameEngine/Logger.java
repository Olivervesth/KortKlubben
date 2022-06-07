package GameEngine;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    /**
     * Method to log a system message to the database
     */
    public void saveLog(String action, String message, DbManager db) {
        if (db.createLog(action, message)) {
            System.out.println("Log Saved: " + action + " " + message);
        } else {

            saveMessageLocal(action, message);
        }
    }

    /**
     * Method to log an error message to the database
     */
    public void saveErrorLog(String action, String message, DbManager db) {
        if (db.createErrorLog(action, message)) {
            System.out.println("ErrorLog Saved: " + action + " " + message);
        } else {
            saveMessageLocal(action, message);
        }
    }

    /**
     * Method to log an error message to local file
     */
    public void saveMessageLocal(String errorAction, String errorMessage) {
        String homeDir = System.getProperty("user.home");
        try {
            // filepath and name
            FileWriter fWriter = new FileWriter((homeDir + "/CardClubErrorLog.txt"), true);
            fWriter.append(errorAction).append(" ").append(errorMessage).append(System.lineSeparator());
            fWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred during logging locally.");
            e.printStackTrace();
        }
    }
}
