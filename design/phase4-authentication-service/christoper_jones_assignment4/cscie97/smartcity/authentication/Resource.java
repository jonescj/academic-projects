package cscie97.smartcity.authentication;

/*
The Resource class represents cities and devices that a particular user or resource can act upon.
 */
public class Resource implements Visitable {

    private String id, description;

    public Resource(String id, String description) {
        this.id = id;
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
