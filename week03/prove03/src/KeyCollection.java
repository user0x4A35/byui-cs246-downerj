/**
 * Stores the Twitter API keys.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public class KeyCollection {

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String accessLevel;

    /**
     * Returns the Consumer Key.
     * @return The consumer key
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * Returns the Consumer Secret.
     * @return The consumer secret
     */
    public String getConsumerSecret() {
        return consumerSecret;
    }

    /**
     * Returns the Access Token.
     * @return The access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Returns the Access Token Secret.
     * @return The access token secret
     */
    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    /**
     * Returns the Access Level.
     * @return The access level
     */
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * Sets the Consumer Key.
     * @param key The consumer key
     */
    public void setConsumerKey(String key) {
        consumerKey = key;
    }

    /**
     * Sets the Consumer Secret.
     * @param secret The consumer secret
     */
    public void setConsumerSecret(String secret) {
        consumerSecret = secret;
    }

    /**
     * Sets the Access Token.
     * @param token The access token
     */
    public void setAccessToken(String token) {
        accessToken = token;
    }

    /**
     * Sets the Access Token Secret.
     * @param secret The access token secret
     */
    public void setAccessTokenSecret(String secret) {
        accessTokenSecret = secret;
    }

    /**
     * Sets the Access Level.
     * @param level The access level
     */
    public void setAccessLevel(String level) {
        accessLevel = level;
    }
}