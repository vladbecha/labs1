package auth;


public class User {
 
    private String login;
   
    private String salt;
   
    private String hash;

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

    public String getLogin() {
        return login;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}