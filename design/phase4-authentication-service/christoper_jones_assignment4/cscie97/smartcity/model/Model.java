package cscie97.smartcity.model;

import com.cscie97.ledger.LedgerException;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.authentication.AuthenticationException;
import cscie97.smartcity.authentication.Permission;
import cscie97.smartcity.controller.Controller;
import cscie97.smartcity.controller.ControllerException;
import cscie97.smartcity.controller.Observable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/*The Model Service is primarily responsible for managing the state of the City domain objects. It manages the cites,
their devices, and their inhabitants within the system. It also provides the API used by the clients of the Model
Service. It contains a name, a description, and a unique seed that helps establish its identity. */
public class Model implements Observable {

    // # TESTING AUTH SERVICE
    private Authentication authService;

    private String name;
    private String description;
    private String seed;

    // A map of all the cities within the model
    private HashMap<String, City> cityMap;

    // A map of all people within the model
    private HashMap<String, Person> people;

    public Model(String name, String description, String seed) throws ModelException {
        this.name = name;
        this.description = description;
        this.seed = seed;
        this.cityMap = new HashMap<String, City>();
        this.people = new HashMap<String, Person>();

        // # AUTHENTICATION
        authService = Authentication.getInstance();
    }

    // Create and add a new city to the model if it doesn’t already exist or overlap with others, otherwise, throw a ModelException.
    public void defineCity(String cityID, String name, String accountAddress, Location location, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_define_city")) {
            City city = new City(cityID, name, accountAddress, location);
            for (String cid : cityMap.keySet()) {
                if (city.getLocation().overlapsWith(cityMap.get(cid).getLocation()) || cityMap.containsKey(cityID)) {
                    throw new ModelException("ExistingCityError", "The city [" + cityID + "] already exists");
                }
            }
            this.cityMap.put(cityID, city);

            // ADD RESOURCE TO AUTH SERVICE
            authService.createResource(cityID, name);
        } else {
            System.out.println("Check access failed...");
        }
    }

    // Get a resident/visitor within the model if it exists, otherwise, throw a ModelException.
    public Person getPerson(String personID) {
        return this.people.get(personID);
    }

    // Display details about the city and its residents
    public void showCity(String cityID, AuthToken token) {
        City city = this.cityMap.get(cityID);
        System.out.println("\tname:");
        System.out.println("\t\t" + city.getName() + ":");
        System.out.println("\taccount:");
        System.out.println("\t\t" + city.getAccountAddress());
        System.out.println("\tlocation:");
        System.out.println("\t\tlatitude:  " + city.getLocation().getLatitude());
        System.out.println("\t\tlongitude: " + city.getLocation().getLongitude());
        System.out.println("\t\tradius:    " + city.getLocation().getRadius());
        System.out.println("\tpeople:");
        for (String personID : this.people.keySet()) {
            System.out.println("\t\t" + this.people.get(personID).getPersonID());
        }
        System.out.println("\tdevices:");
        HashMap<String, Device> devices = city.getDevices();
        for (String deviceID : devices.keySet()) {
            System.out.println("\t\t" + devices.get(deviceID).getDeviceID());
        }
        System.out.println();
    }

    public void defineStreetSign(String cityID, String deviceID, Location location, boolean enabled, String text, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            StreetSign streetSign = new StreetSign(deviceID, location, enabled, text);
            if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
            } else {
                this.cityMap.get(cityID).getDevices().put(deviceID, streetSign);

                // ADD RESOURCE TO AUTH SERVICE
                authService.createResource(deviceID, deviceID);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateStreetSign(String cityID, String deviceID, boolean enabled, String text, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            StreetSign streetSign = (StreetSign) this.cityMap.get(cityID).getDevices().get(deviceID);
            if (text != null && text.length() > 0) {
                streetSign.setText(text);
            }
            if (!streetSign.isEnabled()) {
                streetSign.setEnabled(enabled);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineInformationKiosk(String cityID, String deviceID, Location location, boolean enabled, URI image, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            InformationKiosk informationKiosk = new InformationKiosk(deviceID, location, enabled, image);
            if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
            } else {
                this.cityMap.get(cityID).getDevices().put(deviceID, informationKiosk);

                // ADD RESOURCE TO AUTH SERVICE
                authService.createResource(deviceID, deviceID);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateInformationKiosk(String cityID, String deviceID, boolean enabled, URI uri, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            InformationKiosk informationKiosk = (InformationKiosk) this.cityMap.get(cityID).getDevices().get(deviceID);
            if (!informationKiosk.isEnabled()) {
                informationKiosk.setEnabled(enabled);
            }
            if (uri != null) {
                informationKiosk.setImage(uri);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineStreetLight(String cityID, String deviceID, Location location, boolean enabled, int value, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            StreetLight streetLight = new StreetLight(deviceID, location, enabled, value);
            if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
            } else {
                this.cityMap.get(cityID).getDevices().put(deviceID, streetLight);

                // ADD RESOURCE TO AUTH SERVICE
                authService.createResource(deviceID, deviceID);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateStreetLight(String cityID, String deviceID, boolean enabled, int value, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            StreetLight streetLight = (StreetLight) this.cityMap.get(cityID).getDevices().get(deviceID);
            if (!streetLight.isEnabled()) {
                streetLight.setEnabled(enabled);
            }
            if (value >= 0) {
                streetLight.setBrightness(value);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineParkingSpace(String cityID, String deviceID, Location location, boolean enabled, int value, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            ParkingSpace parkingSpace = new ParkingSpace(deviceID, location, enabled, value);
            if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
            } else {
                this.cityMap.get(cityID).getDevices().put(deviceID, parkingSpace);

                // ADD RESOURCE TO AUTH SERVICE
                authService.createResource(deviceID, deviceID);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateParkingSpace(String cityID, String deviceID, boolean enabled, int value, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            ParkingSpace parkingSpace = (ParkingSpace) this.cityMap.get(cityID).getDevices().get(deviceID);
            if (!parkingSpace.isEnabled()) {
                parkingSpace.setEnabled(enabled);
            }
            if (value >= 0) {
                parkingSpace.setRate(value);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineRobot(String cityID, String deviceID, Location location, boolean enabled, String activity, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            Robot robot = new Robot(deviceID, location, enabled, activity);
            if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
            } else {
                this.cityMap.get(cityID).getDevices().put(deviceID, robot);

                // ADD RESOURCE TO AUTH SERVICE
                authService.createResource(deviceID, deviceID);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateRobot(String cityID, String deviceID, boolean enabled, String activity, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            Robot robot = (Robot) this.cityMap.get(cityID).getDevices().get(deviceID);
            if (activity != null && activity.length() > 0) {
                robot.setActivity(activity);
            }
            if (!robot.isEnabled()) {
                robot.setEnabled(enabled);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineVehicle(String cityID, String deviceID, Location location, boolean enabled, String vehicleType, String activity, int capacity, int fee, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            if (vehicleType.equals("bus")) {
                Bus bus = new Bus(deviceID, location, enabled, activity, capacity, fee);
                if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                    throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
                } else {
                    this.cityMap.get(cityID).getDevices().put(deviceID, bus);
                }
            } else if (vehicleType.equals("car")) {
                Car car = new Car(deviceID, location, enabled, activity, capacity, fee);
                if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
                    throw new ModelException("ExistingDeviceError", "The device [" + deviceID + "] already exists");
                } else {
                    this.cityMap.get(cityID).getDevices().put(deviceID, car);
                }
            } else {
                throw new ModelException("IncorrectTypeError", "The type [" + vehicleType + "] is not a subtype of Vehicle");
            }

            // ADD RESOURCE TO AUTH SERVICE
            authService.createResource(deviceID, deviceID);
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateVehicle(String cityID, String deviceID, Location location, boolean enabled, String activity, int fee, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            Vehicle vehicle = (Vehicle) this.cityMap.get(cityID).getDevices().get(deviceID);
            if (location != null) {
                vehicle.setLocation(location);
            }
            if (!vehicle.isEnabled()) {
                vehicle.setEnabled(enabled);
            }
            if (activity != null && activity.length() > 0) {
                vehicle.setActivity(activity);
            }
            if (fee >= 0) {
                vehicle.setFee(fee);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void showDevice(String cityID, String deviceID, AuthToken token) {
        if (this.cityMap.get(cityID).getDevices().containsKey(deviceID)) {
            Device device = this.cityMap.get(cityID).getDevices().get(deviceID);
            System.out.println("\tdevice id:");
            System.out.println("\t\t" + device.getDeviceID());
            System.out.println("\tlocation:");
            System.out.println("\t\tlatitude:  " + device.getLocation().getLatitude());
            System.out.println("\t\tlongitude: " + device.getLocation().getLongitude());
            System.out.println("\tenabled:");
            System.out.println("\t\t" + device.isEnabled());
            System.out.println("\tstatus:");
            System.out.println("\t\t" + device.getStatus());
            System.out.println("\tlast event:");
            System.out.println("\t\t" + device.getLastEvent());
            System.out.println();
        } else {
            for (String idOFDevice : this.cityMap.get(cityID).getDevices().keySet()) {
                Device device = this.cityMap.get(cityID).getDevices().get(idOFDevice);
                System.out.println("\tdevice id:");
                System.out.println("\t\t" + device.getDeviceID());
                System.out.println("\tlocation:");
                System.out.println("\t\tlatitude:  " + device.getLocation().getLatitude());
                System.out.println("\t\tlongitude: " + device.getLocation().getLongitude());
                System.out.println("\tenabled:");
                System.out.println("\t\t" + device.isEnabled());
                System.out.println("\tstatus:");
                System.out.println("\t\t" + device.getStatus());
                System.out.println("\tlast event:");
                System.out.println("\t\t" + device.getLastEvent());
                System.out.println();
            }
        }
    }

    public void createSensorEvent(String cityID, String deviceID, SensorType sensor, String value, String personID, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_simulate_event")) {
            this.cityMap.get(cityID).getDevices().get(deviceID).createSensorEvent(sensor, value, personID);
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void createSensorOutput(String cityID, String deviceID, SensorType sensor, String value, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("scms_simulate_event")) {
            if (deviceID == null) {
                for (String device : this.cityMap.get(cityID).getDevices().keySet()) {
                    this.cityMap.get(cityID).getDevices().get(device).createSensorOutput(sensor, value);
                }
            } else {
                this.cityMap.get(cityID).getDevices().get(deviceID).createSensorOutput(sensor, value);
            }
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineResident(String personID, String name, String biometricID, String phoneNumber, Role role, Location location, String accountAddress, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("auth_user_admin")) {
            Resident resident = new Resident(personID, name, biometricID, phoneNumber, role, location, accountAddress);
            if (this.people.containsKey(personID)) {
                throw new ModelException("ExistingPersonError", "The person [" + personID + "] already exists");
            }
            for (String cid : this.cityMap.keySet()) {
                if (this.cityMap.get(cid).getLocation().overlapsWith(resident.getLocation())) {
                    this.people.put(personID, resident);

                    authService.createUser(personID,name);
                    authService.addCredential(personID,"biometric", "voiceprint:"+personID+"-voiceprint");
                    authService.addCredential(personID,"biometric", "faceprint:"+personID+"-faceprint");
                    if(role.equals(Role.valueOf("child"))){
                        authService.addRole(personID,"child_role");
                    }else{
                        authService.addRole(personID,"adult_role");
                    }

                    return;
                }
            }
            throw new ModelException("InvalidCityBoundaryError", "The person [" + personID + "] is outside of any city boundaries");
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateResident(String personID, String name, String biometricID, String phoneNumber, Role role, Location location, String accountAddress, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("auth_user_admin")) {
            ((Resident) this.getPerson(personID)).update(name, biometricID, phoneNumber, role, location, accountAddress);
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void defineVisitor(String personID, String biometricID, Location location, AuthToken token) throws ModelException, AuthenticationException {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("auth_user_admin")) {
            Visitor visitor = new Visitor(personID, biometricID, location);
            if (this.people.containsKey(personID)) {
                throw new ModelException("ExistingPersonError", "The person [" + personID + "] already exists");
            }
            for (String cid : this.cityMap.keySet()) {
                if (this.cityMap.get(cid).getLocation().overlapsWith(visitor.getLocation())) {
                    this.people.put(personID, visitor);

                    authService.createUser(personID,"some visitor");
                    authService.addCredential(personID,"biometric", "voiceprint:"+personID+"-voiceprint");
                    authService.addCredential(personID,"biometric", "faceprint:"+personID+"-faceprint");

                    return;
                }
            }
            throw new ModelException("InvalidCityBoundaryError", "The person [" + personID + "] is outside of any city boundaries");
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void updateVisitor(String personID, String biometricID, Location location, AuthToken token) {
        if (authService.checkAccess(token.getId()) && token.getPermissions().contains("auth_user_admin")) {
            ((Visitor) this.getPerson(personID)).update(biometricID, location);
        } else {
            System.out.println("Check access failed...");
        }
    }

    public void showPerson(String personID, AuthToken token) {
        this.getPerson(personID).showPerson();
    }

    public HashMap<String,City> getCityMap(){
        return this.cityMap;
    }

    @Override
    public void attach(Controller controller) throws AuthenticationException {
        controller.addSubscription(this);
        for(String cityID : this.cityMap.keySet()){
            controller.requestAuthToken(cityID);
        }
    }

    @Override
    public void detach(Controller controller) {
        controller.removeSubscription(this);
        for(String cityID : this.cityMap.keySet()){
            controller.revokeAuthToken(cityID);
        }
    }

    @Override
    public void notify(Controller controller) throws LedgerException, URISyntaxException, ControllerException {
        for(String cityID : this.cityMap.keySet()){
            City city = this.cityMap.get(cityID);
            controller.addToEventQueue("<cityID> "+city.getCityID());
            for(String personID : this.people.keySet()){
                Person person = this.people.get(personID);
                if(city.getLocation().overlapsWith(person.getLocation())) {
                    if (person instanceof Resident) {
                        controller.addToEventQueue("<cityID> " + city.getCityID() + " <personID> " + person.getPersonID() + " <account> " + ((Resident) person).getAccountAddress());
                    } else {
                        controller.addToEventQueue("<cityID> " + city.getCityID() + " <personID> " + person.getPersonID());
                    }
                }
            }
        }
        for(String cityID : this.cityMap.keySet()){
            City city = this.cityMap.get(cityID);
            for(String deviceID : city.getDevices().keySet()){
                Device device = city.getDevice(deviceID);
                if(device.getLastEvent() != null){
                    controller.addToEventQueue("<cityID> "+city.getCityID()+" <deviceID> "+device.getDeviceID());
                    controller.addToEventQueue("<cityID> "+city.getCityID()+" <deviceID> "+device.getDeviceID()+" "+device.getLastEvent().toString());
                }else{
                    controller.addToEventQueue("<cityID> "+city.getCityID()+" <deviceID> "+device.getDeviceID());
                }
            }
        }
        controller.update();
    }
}
