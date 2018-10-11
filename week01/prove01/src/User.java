public class User {
    private String mPassword;
    private String mSalt;
    private String mHash;

    public User(String password) {
        mPassword = password;
        mSalt = "";
        mHash = "";
    }

    public String getPassword() {
        return mPassword;
    }

    public String getSalt() {
        return mSalt;
    }

    public String getHashedPassword() {
        return mHash;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setSalt(String salt) {
        mSalt = salt;
    }

    public void setHashedPassword(String hash) {
        mHash = hash;
    }
}