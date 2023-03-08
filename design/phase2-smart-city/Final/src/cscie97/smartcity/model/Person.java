package cscie97.smartcity.model;

/*The Person class represents the individual people within the a given City. A Person can be either a Resident or
Visitor. Residents are well known persons, where visitors are anonymous. Both Residents and Visitors are assigned a
unique person id. */
public abstract class Person {

    public String personID; // The unique ID of the person
    public String biometricID; // The unique biometricID of the person
    public Location location; // The location that the person is currently located at

    public Person(String personID, String biometricID, Location location){
        this.personID = personID;
        this.biometricID = biometricID;
        this.location = location;
    }

    // Display the details of the resident/visitor
    public abstract void showPerson();

   // GETTERS/SETTERS
    public Location getLocation(){
        return this.location;
    }
    public String getPersonID(){ return this.personID; }
    public String getBiometricID(){ return this.biometricID; }
}
