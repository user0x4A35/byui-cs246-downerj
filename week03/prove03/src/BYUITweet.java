import com.google.gson.annotations.SerializedName;

/**
 * A basic data structure to hold tweet data.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public class BYUITweet {

    private User user;
    @SerializedName("full_text")
    private String text;
    
    /**
     * Gets the tweeter.
     * @return The {@link User}
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Gets the tweet text.
     * @return The text {@link java.lang.String}
     */
    public String getText() {
        return text;
    }
    
    /**
     * Sets the tweeter.
     * @param user The {@link User}
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Sets the tweet text.
     * @param text The text {@link java.lang.String}
     */
    public void setText(String text) {
        this.text = text;
    }
}