package cscie97.smartcity.authentication;

/*
The Password class represents a credential that holds a given user’s hashed ID and password.
 */
public class Password {

    private int username, secret;

    public Password(String username, String secret) {
        this.username = username.hashCode();
        this.secret = secret.hashCode();
    }

    public int getSecret() {
        return this.secret;
    }
}
