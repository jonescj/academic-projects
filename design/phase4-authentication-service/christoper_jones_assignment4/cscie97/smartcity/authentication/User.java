package cscie97.smartcity.authentication;

import java.util.HashMap;

/*
The User class represents an inhabitant within the Model Service. The hold their access information such as their usernames, passwords, and biometric prints. Additionally, users can be given access through roles and permissions to perform tasks.
 */
public class User implements Visitable{

    private String id, name;
    private Credentials credentials;
    private HashMap<String,Role> roles;
    private HashMap<String,Permission> permissions;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.credentials = new Credentials();
        this.roles = new HashMap<String,Role>();
        this.permissions = new HashMap<String,Permission>();
    }

    // Sets up a username/password combination for the user
    public void addCredential(Password password) {
        this.credentials.add(password);
    }

    // Adds a username/biometric combination for the user
    public void addCredential(Biometric biometric) {
        this.credentials.add(biometric);
    }

    // Adds a role to the user
    public void add(Role role) {
        this.roles.put(role.getID(), role);
    }

    @Override
    public boolean accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public String getID() {
        return this.id;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public HashMap<String, Permission> getPermissions(){
        return this.permissions;
    }

    public HashMap<String, Role> getRoles(){
        return this.roles;
    }
}
