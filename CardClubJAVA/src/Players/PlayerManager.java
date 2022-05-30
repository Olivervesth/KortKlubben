package Players;

public class PlayerManager {
    /**
     * Method to create a new player instance
     *
     * @return Player
     */
    public Player createPlayer(String username, String playername, boolean human) {
        if (human) {
            return new Human(username, playername);
        } else {
            return new Computer(playername);
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
}
