import Rooms.AppConnection;

/**
 * Main class of the system
 */
public class Main {
    /**
     * The starting point of everything
     * @param args args
     */
    public static void main(String[] args) {
        // Start listening for server connections
        AppConnection server = new AppConnection(5004);
    }
}