public class GSONTest {
    public static final String SAVE_FILE = "player.json";

    public static void main(String[] args) {
        // Create the player
        System.out.println("Creating player...");
        Player player = new Player("Ascender X", 100, 50, 30);
        player.addEquipment("sword", 1);
        player.addEquipment("elixir", 2);
        player.addEquipment("bomb", 10);
        System.out.printf("%s%n%n", player);

        // Create the game
        System.out.println("Creating game...");
        Game game = new Game(player);

        // Save the game
        System.out.println("Saving game...");
        game.saveGame(SAVE_FILE);

        // Load the game
        System.out.println("Loading game...");
        Game game2 = Game.loadGame(SAVE_FILE);
        if (game2 == null) {
            System.err.println("Game not loaded");
        } else {
            System.out.printf("%s%n", game2.getPlayer());
        }
    }
}