package cscie97.smartcity.authentication;

/*
The Permission class represents singular actions that a particular user or resource can perform.
 */
public class Permission implements Visitable {

    private String id,name,description;

    public Permission(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public String getID() {
        return this.id;
    }
}
