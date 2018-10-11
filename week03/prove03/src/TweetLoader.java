import com.google.gson.Gson;
import java.util.Map;
import java.util.TreeMap;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class handles tweets via RESTful API calls to the Twitter server.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public class TweetLoader {

    private Twitter twitter;
    private KeyCollection keys;
    private static final ConsoleWriter console = new ConsoleWriter();
    
    /**
     * Constructs the TweetLoader.
     * @param keys The API keys
     */
    public TweetLoader(KeyCollection keys) {
        this.keys = keys;
        configureKeys();
    }
    
    /**
     * Calls the Twitter API to query the server for tweets with a 
     * specified hashtag and returns the result as a {@link java.util.Map}.
     * The tweets are sorted by username.
     * @param hashtag The specified hashtag
     * @return The {@link java.util.Map} with the username as the key and the
     * {@link BYUITweet} as the value.
     */
    public Map<String, BYUITweet> retrieveTweetsWithHashTag(String hashtag) {
        Map<String, BYUITweet> tweets = new TreeMap<>();
        Query query = new Query(hashtag);
        Gson gson = new Gson();
        QueryResult result;

        // call the Twitter API
        try {
            result = twitter.search(query);
        } catch (TwitterException te) {
            console.errorf("Error: %s%n", te.getMessage());
            return null;
        }

        // parse the JSON results and add the tweets to the map
        for (Status status : result.getTweets()) {
            String json = TwitterObjectFactory.getRawJSON(status);
            //System.out.printf("%s%n%n", json);
            BYUITweet tweet = gson.fromJson(json, BYUITweet.class);
            tweets.put(tweet.getUser().getName(), tweet);
        }

        return tweets;
    }

    /**
     * Configures the API keys and initializes the API service.
     */
    private void configureKeys() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(keys.getConsumerKey())
            .setOAuthConsumerSecret(keys.getConsumerSecret())
            .setOAuthAccessToken(keys.getAccessToken())
            .setOAuthAccessTokenSecret(keys.getAccessTokenSecret())
            .setJSONStoreEnabled(true)
            .setTweetModeExtended(true);

        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }
}