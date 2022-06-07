package Players;

public class PlayerManager {
    /**
     * Method to create a new player instance
     *
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
     * @return Player
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
