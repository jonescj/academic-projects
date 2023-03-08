package cscie97.smartcity.authentication;

import java.util.HashMap;

/*
The Role class is a composite collection of roles, resource roles, and permissions. They represent a collection of actions that a user or resource can perform.
 */
public class Role implements Visitable {

    private String id, name, description;
    private HashMap<String,Role> subroles;
    private HashMap<String,Permission> permissions;

    public Role(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subroles = new HashMap<String,Role>();
        this.permissions = new HashMap<String,Permission>();
    }

    // Adds another role within this role
    public void add(Role role) {
        if(!subroles.containsKey(role.getID())){
            subroles.put(role.getID(), role);
        }
    }

    // Adds a permission within this role
    public void add(Permission permission) {
        if(!permissions.containsKey(permission.getID())){
            permissions.put(permission.getID(), permission);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getID() {
        return this.id;
    }

    public HashMap<String, Role> getSubroles(){
        return this.subroles;
    }

    public HashMap<String, Permission> getPermissions(){
        return this.permissions;
    }

    @Override
    public boolean accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
