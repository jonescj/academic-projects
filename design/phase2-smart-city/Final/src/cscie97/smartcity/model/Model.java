package cscie97.smartcity.model;

import java.util.HashMap;

/*The Model Service is primarily responsible for managing the state of the City domain objects. It manages the cites,
their devices, and their inhabitants within the system. It also provides the API used by the clients of the Model
Service. It contains a name, a description, and a unique seed that helps establish its identity. */
public class Model {

    private String name;
    private String description;
    private String seed;

    // A map of all the cities within the model
    private HashMap<String,City> cityMap;

    // A map of all people within the model
    private HashMap<String,Person> people;

    public Model(String name, String description, String seed) throws ModelException{
        this.name = name;
        this.description = description;
        this.seed = seed;
        this.cityMap = new HashMap<String,City>();
        this.people = new HashMap<String,Person>();
    }

    // Create and add a new city to the model if it doesn’t already exist or overlap with others, otherwise, throw a ModelException.
    public void defineCity(String cityID, String name, String accountAddress, Location location) {
        City city = new City(cityID,name,accountAddress,location);
        for(String cid : cityMap.keySet()){
            if(city.getLocation().overlapsWith(cityMap.get(cid).getLocation()) || cityMap.containsKey(cityID)){
                throw new Error("?"); // CHANGE TO EXCEPTION LATER
            }
        }
        this.cityMap.put(cityID,city);
    }

    // Get a city within the model if it exists, otherwise, throw a ModelException.
    public City getCity(String cityID) {
        return this.cityMap.get(cityID);
    }

    // Create and add a new Resident/Visitor to the model if it doesn’t already exist, otherwise, throw a ModelException.
    public void definePerson(String personID, String name, String biometricID, String phoneNumber, Role role, Location location, String accountAddress) {
        Resident resident = new Resident(personID,name,biometricID,phoneNumber,role,location,accountAddress);
        if(this.people.containsKey(personID)){
            throw new Error("?"); // CHANGE TO EXCEPTION LATER
        }
        for(String cid : this.cityMap.keySet()){
            if(this.cityMap.get(cid).getLocation().overlapsWith(resident.getLocation())){
                this.people.put(personID,resident);
                return;
            }
        }
        throw new Error("?"); // CHANGE TO EXCEPTION LATER
    }

    // Create and add a new Resident/Visitor to the model if it doesn’t already exist, otherwise, throw a ModelException.
    public void definePerson(String personID, String biometricID, Location location) {
        Visitor visitor = new Visitor(personID,biometricID,location);
        if(this.people.containsKey(personID)){
            throw new Error("?"); // CHANGE TO EXCEPTION LATER
        }
        for(String cid : this.cityMap.keySet()){
            if(this.cityMap.get(cid).getLocation().overlapsWith(visitor.getLocation())){
                this.people.put(personID,visitor);
                return;
            }
        }
        throw new Error("?"); // CHANGE TO EXCEPTION LATER
    }

    // Get a resident/visitor within the model if it exists, otherwise, throw a ModelException.
    public Person getPerson(String personID) {
        return this.people.get(personID);
    }

    // Display details about the city and its residents
    public void showCity(String cityID) {
        City city = this.cityMap.get(cityID);
        System.out.println("\tname:");
        System.out.println("\t\t"+city.getName()+":");
        System.out.println("\taccount:");
        System.out.println("\t\t"+city.getAccountAddress());
        System.out.println("\tlocation:");
        System.out.println("\t\tlatitude:  "+city.getLocation().getLatitude());
        System.out.println("\t\tlongitude: "+city.getLocation().getLongitude());
        System.out.println("\t\tradius:    "+city.getLocation().getRadius());
        System.out.println("\tpeople:");
        for(String personID : this.people.keySet()){
            System.out.println("\t\t"+this.people.get(personID).getPersonID());
        }
        System.out.println("\tdevices:");
        HashMap<String,Device> devices = city.getDevices();
        for(String deviceID : devices.keySet()){
            System.out.println("\t\t"+devices.get(deviceID).getDeviceID());
        }
        System.out.println();
    }
}
