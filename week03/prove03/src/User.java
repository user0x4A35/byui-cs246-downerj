import com.google.gson.annotations.SerializedName;

/**
 * A basic data structure representing a Twitter user.
 * 
 * @author James D. Downer
 * @since October 4th, 2018
 * @version 1.0
 */
public class User {

    private String name;
    @SerializedName("followers_count")
    private int followers;
    
    /**
     * Gets the username.
     * @return The username {@link java.lang.String}
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the user's follower count.
     * @return The number of followers
     */
    public int getFollowers() {
        return followers;
    }
    
    /**
     * Sets the username.
     * @param name The name {@link java.lang.String}
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the number of followers.
     * @param followers The number of followers
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }
}