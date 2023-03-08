package cscie97.smartcity.authentication;
/*
The CheckInventory class is an implementation of the Visitor class and checks whether or no an entity has been stored within the Authentication Service.
 */
public class CheckInventory implements Visitor {

    @Override
    public boolean visit(User user) {
        return Authentication.getInstance().getUsers().containsKey(user.getID());
    }

    @Override
    public boolean visit(Resource resource) {
        return Authentication.getInstance().getResources().containsKey(resource.getID());
    }

    @Override
    public boolean visit(Role role) {
        return Authentication.getInstance().getRoles().containsKey(role.getID());
    }

    @Override
    public boolean visit(ResourceRole resourceRole) {
        return Authentication.getInstance().getResourceRoles().containsKey(resourceRole.getID());
    }

    @Override
    public boolean visit(Permission permission) {
        return Authentication.getInstance().getPermission().containsKey(permission.getID());
    }
}
