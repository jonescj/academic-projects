package cscie97.smartcity.model;

/*The Visitor is a subclass of the Person class, and is any Person that isn’t a Resident within that City. They can
interact with the IoT Devices in the same capacity as Residents. However, their fees to use them are waived. */
public class Visitor extends Person{

    public Visitor(String personID, String biometricID, Location location) {
        super(personID,biometricID,location);
    }

    // Update the visitor attributes
    public void update(String biometricID, Location location) {
        if(biometricID.length() != 0){
            this.biometricID = biometricID;
        }
        if(location != null){
            this.location = location;
        }
    }

    @Override
    /* Display the details of the resident/visitor */
    public void showPerson() {
        System.out.println("\tpersonal ID:");
        System.out.println("\t\t"+this.getPersonID());
        System.out.println("\tbio-metric ID:");
        System.out.println("\t\t"+this.getBiometricID());
        System.out.println("\tlocation:");
        System.out.println("\t\tlatitude:  "+this.getLocation().getLatitude());
        System.out.println("\t\tlongitude: "+this.getLocation().getLongitude());
        System.out.println();
    }

}
