package cscie97.smartcity.authentication;

/*
The CheckAccess class is an implementation of the Visitor class and checks whether or no an entity has been stored within the Authentication Service.
 */
public class CheckAccess implements Visitor {
    @Override
    public boolean visit(User user) {
        if(Authentication.getInstance().getTokens().containsKey(user.getID())) {
            if (Authentication.getInstance().getTokens().get(user.getID()).getState().equals(State.valueOf("ACTIVE"))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean visit(Resource resource) {
        return false;
    }

    @Override
    public boolean visit(Role role) {
        return false;
    }

    @Override
    public boolean visit(ResourceRole resourceRole) {
        return false;
    }

    @Override
    public boolean visit(Permission permission) {
        return false;
    }
}
