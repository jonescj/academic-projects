package cscie97.smartcity.authentication;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.HashSet;

/*
AuthTokens are provided upon successful logins into the Authentication Service. Tokens store a collection of
permissions that the owner can then use to invoke methods that fit within their scope. Tokens have an expiration time
 that can mark it invalid, or alternatively, a logout will do the same.
 */
public class AuthToken {

    private final long milliseconds = 1000; // 60000

    private String id;
    private long expiration;
    private State state;
    private HashSet<String> permissions;

    public AuthToken(String id) throws InvalidAuthTokenException {
        this.id = id;
        this.expiration = System.currentTimeMillis() + milliseconds;
        this.state = State.ACTIVE;
        this.permissions = new HashSet<String>();
        startTimer(this);
        activate();
    }

    // Initial sign-in provided that the given credentials are correct
    public void activate() throws InvalidAuthTokenException {
        if(this.state.equals(State.ACTIVE)) {
            HashSet<String> roleCheck = new HashSet<String>();
            HashSet<String> permissionCheck = new HashSet<String>();
            for (String permissionID : Authentication.getInstance().getUsers().get(this.getId()).getPermissions().keySet()) {
                this.permissions.add(Authentication.getInstance().getUsers().get(this.getId()).getPermissions().get(permissionID).getID());
                permissionCheck.add(permissionID);
            }
            for (String roleID : Authentication.getInstance().getUsers().get(this.getId()).getRoles().keySet()) {
                Role role = Authentication.getInstance().getUsers().get(this.getId()).getRoles().get(roleID);
                roleCheck.add(roleID);
                addPermissionsFromRole(role, roleCheck, permissionCheck);
            }
        }else{
            throw new InvalidAuthTokenException("InvalidAuthTokenException","Session has expired...cannot login...");
        }
    }

    // This checks whether a user’s username, password, and biometrics match what it has on file. If it does, it grants them an AuthToken.
    public void login(String username, String password) throws InvalidAuthTokenException {
        if(this.state.equals(State.ACTIVE)) {
            HashSet<String> roleCheck = new HashSet<String>();
            HashSet<String> permissionCheck = new HashSet<String>();
            if (Authentication.getInstance().getUsers().get(username).getCredentials().getPassword() == (password.hashCode())) {
                for (String permissionID : Authentication.getInstance().getUsers().get(username).getPermissions().keySet()) {
                    this.permissions.add(Authentication.getInstance().getUsers().get(username).getPermissions().get(permissionID).getID());
                    permissionCheck.add(permissionID);
                }
                for (String roleID : Authentication.getInstance().getUsers().get(username).getRoles().keySet()) {
                    Role role = Authentication.getInstance().getUsers().get(username).getRoles().get(roleID);
                    roleCheck.add(roleID);
                    addPermissionsFromRole(role, roleCheck, permissionCheck);
                }
            }else{
                throw new InvalidAuthTokenException("InvalidAuthTokenException","Username or password is incorrect!");
            }
        }else{
            throw new InvalidAuthTokenException("InvalidAuthTokenException","Session has expired...cannot login...");
        }
    }

    // This checks whether a user’s biometrics match what it has on file. If it does, it grants them an AuthToken.
    public void checkBiometric(String username, String type, String secret) throws InvalidAuthTokenException {
        if(this.state.equals(State.ACTIVE)) {
            HashSet<String> roleCheck = new HashSet<String>();
            HashSet<String> permissionCheck = new HashSet<String>();
            if (Authentication.getInstance().getUsers().get(username).getCredentials().getBiometric(type) == (secret.hashCode())) {
                for (String permissionID : Authentication.getInstance().getUsers().get(username).getPermissions().keySet()) {
                    this.permissions.add(Authentication.getInstance().getUsers().get(username).getPermissions().get(permissionID).getID());
                    permissionCheck.add(permissionID);
                }
                for (String roleID : Authentication.getInstance().getUsers().get(username).getRoles().keySet()) {
                    Role role = Authentication.getInstance().getUsers().get(username).getRoles().get(roleID);
                    roleCheck.add(roleID);
                    addPermissionsFromRole(role, roleCheck, permissionCheck);
                }
            }
        }else{
            throw new InvalidAuthTokenException("InvalidAuthTokenException","Session has expired...cannot login...");
        }
    }

    private void addPermissionsFromRole(Role role, HashSet<String> roleCheck, HashSet<String> permissionCheck) {
        for(String permissionID : role.getPermissions().keySet()){
            if(!permissionCheck.contains(permissionID)) {
                this.permissions.add(role.getPermissions().get(permissionID).getID());
                permissionCheck.add(permissionID);
            }
        }
        for(String subroleID: role.getSubroles().keySet()){
            if(!roleCheck.contains(subroleID)) {
                roleCheck.add(subroleID);
                addPermissionsFromRole(role.getSubroles().get(subroleID), roleCheck, permissionCheck);
            }
        }
    }

    // Invalidates the token
    public void logout(){
        this.state = State.EXPIRED;
        this.permissions = new HashSet<>();
    }

    private void startTimer(AuthToken A) {
        new Thread(new Runnable() {
            public void run() {
                while(System.currentTimeMillis() < A.expiration && A.state != State.EXPIRED){
                   // WAITING...
                }
                A.state = State.EXPIRED;
            }
        }).start();
    }

    public State getState(){
        return this.state;
    }

    public String getId(){
        return this.id;
    }

    public HashSet<String> getPermissions() {
        return this.permissions;
    }
}
