import Rooms.AppConnection;

public class Main {
    public static void main(String[] args) {
        // Start listening for server connections
        AppConnection server = new AppConnection(5004);
    }
}