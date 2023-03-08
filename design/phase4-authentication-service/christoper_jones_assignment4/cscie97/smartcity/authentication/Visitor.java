package cscie97.smartcity.authentication;

/*
The Visitor class allows actions to be performed on objects that implement the Visitable interface
 */
public interface Visitor {
    public boolean visit(User user);
    public boolean visit(Resource resource);
    public boolean visit(Role role);
    public boolean visit(ResourceRole resourceRole);
    public boolean visit(Permission permission);
}
