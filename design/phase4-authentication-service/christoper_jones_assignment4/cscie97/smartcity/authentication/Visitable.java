package cscie97.smartcity.authentication;

/*
The Visitable interface allows objects to utilize Visitor objects to perform tasks for them.
 */
public interface Visitable {
    public boolean accept(Visitor visitor);
}
