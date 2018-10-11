import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private Player player;
    private static Gson gson;
    
    public Game(Player player) {
        this.player = player;
        gson = new Gson();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public boolean saveGame(String fileName) {
        boolean readSuccess;
        String json = gson.toJson(player);
        File saveFile = new File(fileName);
        
        if (saveFile.isDirectory()) {
            readSuccess = false;
        } else {
            try {
                FileWriter fout = new FileWriter(saveFile);
                fout.write(json);
                fout.close();
                readSuccess = true;
            } catch (IOException ioe) {
                System.err.printf("Error: %s%n", ioe.getMessage());
                readSuccess = false;
            }
        }

        return readSuccess;
    }
    
    public static Game loadGame(String fileName) {
        boolean readSuccess;
        Game game = null;
        File saveFile = new File(fileName);
        StringBuilder buf = new StringBuilder();

        if (!saveFile.exists() || saveFile.isDirectory()) {
            readSuccess = false;
        } else {
            try {
                // read a JSON string from the file
                FileReader fin = new FileReader(fileName);
                int character = 0;
                while ((character = fin.read()) >= 0) {
                    buf.append((char) character);
                }
                fin.close();

                // parse the JSON string
                String json = buf.toString();
                Player player = gson.fromJson(json, Player.class);

                // create the game from the new Player
                game = new Game(player);
                readSuccess = true;
            } catch (IOException ioe) {
                System.err.printf("Error: %s%n", ioe.getMessage());
                readSuccess = false;
            } catch (JsonParseException jpe) {
                System.err.printf("Error: %s%n", jpe.getMessage());
                readSuccess = false;
            }
        }

        return (readSuccess) ? game : null;
    }
}