import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This program tests the Twitter API by querying the server for tweets
 * with the #byui hashtag, deserializing the results via JSON, and printing
 * the results to the console.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public class Prove03 {

    private TweetLoader loader;
    private KeyCollection keys;
    public static final ConsoleWriter console = new ConsoleWriter();

    /**
     * This method will read in the keys from an external JSON file
     * and populate the local KeyCollection.
     * If an error occurs, it will be displayed to the console, and
     * the method will terminate.
     * @param fileName The file name
     * @return Whether or not the keys were processed successfully
     */
    public boolean parseKeysFile(String fileName) {
        StringBuffer buf = new StringBuffer();
        Gson gson = new Gson();

        // read in the file, one character at a time in order to
        // prevent null characters (\0) which would break GSON
        try {
            // open the file, if possible
            File file = new File(fileName);
            if (!file.exists()) {
                throw new IOException("File not found");
            } else if (file.isDirectory()) {
                throw new IOException("Is directory");
            }
            FileReader fin = new FileReader(file);

            // read until we get -1 (meaning EOF)
            int character;
            while ((character = fin.read()) >= 0) {
                buf.append((char) character);
            }
            fin.close();
        } catch (IOException ioe) {
            console.errorf("Error: %s%n", ioe.getMessage());
            return false;
        }

        // Parse the keys from the newly created JSON string
        try {
            String json = buf.toString();
            keys = gson.fromJson(json, KeyCollection.class);
        } catch (JsonParseException jpe) {
            console.errorf("Error: %s%n", jpe.getMessage());
            return false;
        }

        return true;
    }

    /**
     * This method will get the keys from the specified file,
     * query the server for tweets, and print them to the screen
     * (sorted first by username and then by follower cound).
     * @param keyFileName The file name to get the keys from
     * @return Whether or not the load and/or query were successful
     */
    public boolean run(String keysFileName) {
        if (!parseKeysFile(keysFileName)) {
            return false;
        }

        // query the Twitter server
        loader = new TweetLoader(keys);
        String hashTag = "#byui";
        Map<String, BYUITweet> tweets = loader.retrieveTweetsWithHashTag(hashTag);
        if (tweets == null) {
            return false;
        }

        // print tweets, sorted by name
        console.println(1);
        console.setColor(ConsoleColor.CYAN);
        console.println("Tweets : sorted by username");
        console.println("===========================");
        console.resetColor();
        console.println(1);
        displayTweets(tweets);

        // sort the list by user's follower count
        Map<String, BYUITweet> tweetsSorted = sortTweetsByFollowers(tweets);

        // print tweets, now sorted by followers
        console.println(2);
        console.setColor(ConsoleColor.CYAN);
        console.println("Tweets : sorted by followers");
        console.println("============================");
        console.resetColor();
        console.println(1);
        displayTweets(tweetsSorted);

        return true;
    }

    /**
     * This method sorts a tweets map by follower count using a custom
     * {@link java.util.Comparator} and returns a new {@link java.util.Map}.
     * @param tweets The {@link BYUITweet} {@link java.util.Map}
     * @return The newly sorted {@link java.util.Map}
     */
    public Map<String, BYUITweet> sortTweetsByFollowers(Map<String, BYUITweet> tweets) {
        List<Map.Entry<String, BYUITweet>> list = 
            new LinkedList<Map.Entry<String, BYUITweet>>(tweets.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, BYUITweet>>() {
            public int compare(Map.Entry<String, BYUITweet> rhs, Map.Entry<String, BYUITweet> lhs) {
                Integer followers1 = lhs.getValue().getUser().getFollowers();
                Integer followers2 = rhs.getValue().getUser().getFollowers();
                return (followers1).compareTo(followers2);
            }
        });
        Map<String, BYUITweet> tweetsSorted = new LinkedHashMap<String, BYUITweet>();
        for (Map.Entry<String, BYUITweet> entry : list) {
            tweetsSorted.put(entry.getKey(), entry.getValue());
        }

        return tweetsSorted;
    }

    /**
     * This method prints the given {@link java.util.Map} of tweets.
     * @param tweets The {@link BYUITweet} {@link java.util.Map}
     */
    public void displayTweets(Map<String, BYUITweet> tweets) {
        for (String userName : tweets.keySet()) {
            BYUITweet tweet = tweets.get(userName);
            int followers = tweet.getUser().getFollowers();
            String text = tweet.getText();
            console.setColor(ConsoleColor.GREEN);
            console.printf("%s", userName);
            console.resetColor();
            console.printf(" (");
            console.setColor(ConsoleColor.MAGENTA);
            console.printf("%d follower%s",
                followers,
                (followers == 1) ? "" : "s"
            );
            console.resetColor();
            console.printf(") - %s%n",
                text
            );
        }
    }

    /**
     * This method checks the command line arguments for a
     * keys-file name and then runs the program.
     * @param args The system's command-line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            console.warningln("Please provide a key JSON file");
            System.exit(1);
        }

        String keysFileName = args[0];
        Prove03 prove03 = new Prove03();
        if (!prove03.run(keysFileName)) {
            console.warningln("Program temrinated");
            System.exit(1);
        }
    }
}