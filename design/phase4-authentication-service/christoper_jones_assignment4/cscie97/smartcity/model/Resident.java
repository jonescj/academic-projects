package cscie97.smartcity.model;

public class Resident extends Person{

    private String name;
    private String phoneNumber;
    private String accoutAddress;
    private Role role;
    private String personID;
    private String biometricID;
    private Location location;

    public Resident(String personID, String name, String biometricID, String phoneNumber, Role role, Location location, String accountAddress) {
        super(personID,biometricID,location);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.accoutAddress = accountAddress;
    }

    // Update the resident attributes
    public void update(String name, String biometricID, String phoneNumber, Role role, Location location, String accountAddress) {
        if(name.length() > 0){
            this.name = name;
        }if(biometricID.length() > 0){
            this.biometricID = biometricID;
        }if(phoneNumber.length() > 0){
            this.phoneNumber = phoneNumber;
        }if(role != null){
            this.role = role;
        }if(location != null){
            this.location = location;
        }if(accountAddress.length() > 0){
            this.accoutAddress = accountAddress;
        }
    }

    @Override
    /* Display the details of the resident/visitor */
    public void showPerson() {
        System.out.println("\tpersonal ID:");
        System.out.println("\t\t"+this.getPersonID());
        System.out.println("\tbio-metric ID:");
        System.out.println("\t\t"+this.getBiometricID());
        System.out.println("\tname:");
        System.out.println("\t\t"+this.getName());
        System.out.println("\tphone number:");
        System.out.println("\t\t"+this.getPhoneNumber());
        System.out.println("\trole:");
        System.out.println("\t\t"+this.getRole());
        System.out.println("\tlocation:");
        System.out.println("\t\tlatitude:  "+this.getLocation().getLatitude());
        System.out.println("\t\tlongitude: "+this.getLocation().getLongitude());
        System.out.println("\taccount address:");
        System.out.println("\t\t"+this.getAccountAddress());
        System.out.println();
    }

    // GETTERS/SETTERS
    public String getName(){
        return this.name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getAccountAddress(){
        return this.accoutAddress;
    }
    public Role getRole(){
        return this.role;
    }
}
