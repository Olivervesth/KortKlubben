package GameEngine;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    /**
     * Method to log a system message to the database
     *
     * @param String action
     * @param String message
     *
     * @returns Boolean
     */
    public boolean saveLog(String action, String message, DbManager db) {
        try {

            System.out.println("Log Saved: " + action + " " + message);
            return db.createLog(action, message);
        } catch (Exception ex) {
            saveMessageLocal(action, message);
            return true;
        }
    }

    /**
     * Method to log an error message to the database
     *
     * @param String action
     * @param String message
     *
     * @returns Boolean
     */
    public boolean saveErrorLog(String action, String message, DbManager db) {
        try {
            System.out.println("ErrorLog Saved: " + action + " " + message);
            return db.createErrorLog(action, message);
        } catch (Exception ex) {
            saveMessageLocal(action, message);
            return true;
        }
    }

    /**
     * Method to log an error message to local file
     *
     * @param String errorAction
     * @param String errorMessage
     */
    public void saveMessageLocal(String errorAction, String errorMessage) {
        String homeDir = System.getProperty("user.home");
        try {
            // filepath and name
            FileWriter fWriter = new FileWriter((homeDir + "/CardClubErrorLog.txt"), true);
            fWriter.append(errorAction + " " + errorMessage + System.lineSeparator());
            fWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred during loggin locally.");
            e.printStackTrace();
        }
    }
}
