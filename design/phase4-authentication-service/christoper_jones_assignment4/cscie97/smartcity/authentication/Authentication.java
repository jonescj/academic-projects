package cscie97.smartcity.authentication;

import java.util.HashMap;

/*
The Authentication class is the primary class used to control access to the Model Service. It creates internal copied
entities and resources to represent the inhabitants within the city it monitors that it uses to simulate interactions
and ultimately determine who has access and who does not.
 */
public class Authentication {
    private static Authentication authInstance = null;
    private HashMap<String,Permission> permissions;
    private HashMap<String,Resource> resources;
    private HashMap<String,Role> roles;
    private HashMap<String,ResourceRole> resourceRoles;
    private HashMap<String,User> users;
    private HashMap<String,AuthToken> tokens;

    private Authentication(){
        permissions = new HashMap<String,Permission>();
        resources = new HashMap<String,Resource>();
        roles = new HashMap<String,Role>();
        resourceRoles = new HashMap<String,ResourceRole>();
        users = new HashMap<String,User>();
        tokens = new HashMap<String,AuthToken>();
    }

    // This allows all dependent services to access the same instance of the Authentication service
    public static Authentication getInstance(){
        if(authInstance == null){
            System.out.println("CJ:\tAUTHENTICATION SERVICE HAS BEEN INITIALIZED");
            authInstance = new Authentication();
        }
        return authInstance;
    }

    // This checks whether a user’s username, password, and biometrics match what it has on file. If it does, it grants them an AuthToken
    public AuthToken login(String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        if(checkInventory(this.getUsers().get(username))){
            if(this.getUsers().get(username).getCredentials().getPassword() == (password.hashCode())){
                AuthToken token = new AuthToken(username);
                this.tokens.put(token.getId(),token);
                return token;
            }else if(this.getUsers().get(username).getCredentials().getBiometric("voiceprint") == (password.hashCode())){
                AuthToken token = new AuthToken(username);
                this.tokens.put(token.getId(),token);
                return token;
            }else if(this.getUsers().get(username).getCredentials().getBiometric("faceprint") == (password.hashCode())){
                AuthToken token = new AuthToken(username);
                this.tokens.put(token.getId(),token);
                return token;
            }else{
                throw new AccessDeniedException("AccessDeniedException", "The username/password did not match...");
            }
        }else{
            throw new AccessDeniedException("AccessDeniedException", "The username/password did not match...");
        }
    }

    // This uses the CheckInventory class to check whether it has record of the given entity
    private boolean checkInventory(User user){
        return user.accept(new CheckInventory());
    }

    // This uses the checkAccess class to check whether the identifiers of the given entities match what it has on file
    public boolean checkAccess(String username) {
        return this.getUsers().get(username).accept(new CheckAccess());
    }

    // Creates a new permission and saves it. If it already exists, throw an AuthenticationException.
    public void createPermission(String id, String name, String description) throws AuthenticationException {
        if(!permissions.containsKey(id)){
            permissions.put(id,new Permission(id,name,description));
        }else{
            throw new AuthenticationException("AuthenticationException", "Permission ID "+id+" already exists...");
        }
    }

    // Creates a new role and saves it. If it already exists, throw an AuthenticationException.
    public void createRole(String id, String name, String description) throws AuthenticationException {
        if(!roles.containsKey(id)){
            roles.put(id,new Role(id,name,description));
        }else{
            throw new AuthenticationException("AuthenticationException", "Role ID "+id+" already exists...");
        }
    }

    // Creates a new user and saves it. If it already exists, throw an AuthenticationException.
    public void createUser(String id, String name) throws AuthenticationException {
        if(!users.containsKey(id)){
            users.put(id,new User(id,name));
        }else{
            throw new AuthenticationException("AuthenticationException", "User ID "+id+" already exists...");
        }
    }

    // Creates a new resource and saves it. If it already exists, throw an AuthenticationException.
    public void createResource(String id, String description) throws AuthenticationException {
        if(!resources.containsKey(id)){
            resources.put(id,new Resource(id,description));
        }else{
            throw new AuthenticationException("AuthenticationException", "Resource ID "+id+" already exists...");
        }
    }

    // Creates a new resource role and saves it. If it already exists, throw an AuthenticationException.
    public void createResourceRole(String id, String role, String resource) throws AuthenticationException {
        if(!resourceRoles.containsKey(id)){
            if(roles.containsKey(role)){
                if(resources.containsKey(resource)){
                    String name = roles.get(role).getName();
                    String description = roles.get(role).getDescription() + "for "+resource;
                    resourceRoles.put(id, new ResourceRole(id, name, description));
                    resourceRoles.get(id).add(roles.get(role));
                }else{
                    throw new AuthenticationException("AuthenticationException", "Resource ID "+resource+" does not exist...");
                }
            }else{
                throw new AuthenticationException("AuthenticationException", "Role ID "+role+" does not exist...");
            }
        }else{
            throw new AuthenticationException("AuthenticationException", "Resource Role ID "+id+" already exists...");
        }
    }

    // Adds a credential to a user. If this cannot be done, throw an AuthenticationException.
    public void addCredential(String userID, String type, String secret) throws AuthenticationException {
        if(users.containsKey(userID)){
            if((type).equals("password")){
                users.get(userID).addCredential(new Password(userID, secret));
            }else if((type).equals("biometric")){
                users.get(userID).addCredential(new Biometric(userID, secret));
            }else{
                throw new AuthenticationException("AuthenticationException", "Credential type "+type+" not handled...");
            }
        }else{
            throw new AuthenticationException("AuthenticationException", "User ID "+userID+" does not exist...");
        }
    }

    // Adds a role to a user. If this cannot be done, throw an AuthenticationException.
    public void addRole(String userID, String roleID) throws AuthenticationException {
        if(users.containsKey(userID)){
            if(roles.containsKey(roleID)){
                users.get(userID).add(roles.get(roleID));
            }else if(resourceRoles.containsKey(roleID)){
                users.get(userID).add(resourceRoles.get(roleID));
            }else{
                throw new AuthenticationException("AuthenticationException", "Role ID "+roleID+" does not exist...");
            }
        }else{
            throw new AuthenticationException("AuthenticationException", "User ID "+userID+" does not exist...");
        }
    }

    // Adds a resource role to a user. If this cannot be done, throw an AuthenticationException.
    public void addResourceRole(String userID, String roleID) throws AuthenticationException {
        if(users.containsKey(userID)){
            if(resourceRoles.containsKey(roleID)){
                users.get(userID).add(resourceRoles.get(roleID));
            }else{
                throw new AuthenticationException("AuthenticationException", "Role ID "+roleID+" does not exist...");
            }
        }else{
            throw new AuthenticationException("AuthenticationException", "User ID "+roleID+" does not exist...");
        }
    }

    // Adds a permission to a user. If this cannot be done, throw an AuthenticationException.
    public void addPermission(String roleID, String permissionID) throws AuthenticationException {
        if(roles.containsKey(roleID)){
            if(permissions.containsKey(permissionID)){
                roles.get(roleID).add(permissions.get(permissionID));
            }else{
                throw new AuthenticationException("AuthenticationException", "Permission ID "+permissionID+" does not exist...");
            }
        }else if(resourceRoles.containsKey(roleID)){
            if(permissions.containsKey(permissionID)){
                resourceRoles.get(roleID).add(permissions.get(permissionID));
            }else{
                throw new AuthenticationException("AuthenticationException", "Permission ID "+permissionID+" does not exist...");
            }
        }else{
            throw new AuthenticationException("AuthenticationException", "Role ID "+roleID+" does not exist...");
        }
    }

    public HashMap<String,User> getUsers(){
        return this.users;
    }

    public HashMap<String,Resource> getResources() {
        return this.resources;
    }

    public HashMap<String, Role> getRoles() {
        return this.roles;
    }

    public HashMap<String, ResourceRole> getResourceRoles() {
        return this.resourceRoles;
    }

    public HashMap<String, Permission> getPermission() {
        return this.permissions;
    }

    public HashMap<String,AuthToken> getTokens() {
        return this.tokens;
    }
}
