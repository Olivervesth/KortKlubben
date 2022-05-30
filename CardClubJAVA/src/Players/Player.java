package Players;

import GameEngine.EngineManager;

public abstract class Player {
    /**
     * Fields for Player class
     */
    private String playername;
    private String username;
    private int points;

    /**
     * Constructor for Human class
     *
     * @param String username
     * @param Strign playername
     */
    public Player(String usernamename, String playername) {
        this.playername = playername;
        this.username = usernamename;
        this.points = 0;
    }

    /**
     * Method to get the player's username
     *
     * @return String name
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Method to get the player's player name
     *
     * @return String name
     */
    public String getPlayerName() {
        return this.playername;
    }

    /**
     * Method to get the player's points
     *
     * @return int points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Method to add points to the player
     *
     * @param int points
     */
    public void updatePoints(int points) {
        if (points >= -4 && points <= 4) {
            this.points += points;
        } else {
            EngineManager.getEngineManager().saveErrorLog("UpdatePoints",
                    this.username + " tried to add: " + points + " points");
        }
    }

    /**
     * Method to reset points between games
     */
    public void resetPoints() {
        this.points = 0;
    }

}