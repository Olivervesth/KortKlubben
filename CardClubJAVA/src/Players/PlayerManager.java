package Players;

/**
 * Class for handling and creating players
 */
public class PlayerManager {
    /**
     * Method to create a new player instance
     *
     * @param username   Login name of user
     * @param playerName Public name of player
     * @param human      boolean to determine if Human or CPU player
     * @return Player
     */
    public Player createPlayer(String username, String playerName, boolean human) {
        if (human) {
            return new Human(username, playerName);
        } else {
            return new Computer(playerName);
        }
    }

    /**
     * Method to reset a players points
     *
     * @param player player to reset
     * @return Player player after reset
     */
    public Player resetPlayerPoints(Player player) {
        player.resetPoints();
        return player;
    }

    /**
     * Method to change a players point total
     *
     * @param player player to change points
     * @param points points to change
     */
    public void setPlayerPoints(Player player, int points) {
        player.updatePoints(points);
    }
}
