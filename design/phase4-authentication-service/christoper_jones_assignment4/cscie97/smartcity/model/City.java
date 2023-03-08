package cscie97.smartcity.model;

import java.net.URI;
import java.util.HashMap;

/*The City class represents an individual city within the Model Service. It has a predefined location with its own
boundaries, its own subset of residents/visitors, and its own devices set up to assist them. City boundaries cannot
be overlapping. */
public class City {

    private String cityID;
    private String name;
    private String accountAddress;

    // The general location of the city given by latitude, longitude, and the amount of overall space it takes up
    private Location location;

    // A list of all the devices associated with that city
    private HashMap<String,Device> devices;

    public City(String cityID, String name, String accountAddress, Location location) {
        this.cityID = cityID;
        this.name = name;
        this.accountAddress = accountAddress;
        this.location = location;
        this.devices = new HashMap<String,Device>();
    }

    // Update an existing device of deviceType.
    public void updateDevice(DeviceType deviceType,String deviceID, Location location,boolean enabled,String activity,int fee) {
        if(deviceType.equals(DeviceType.VEHICLE)){
            Vehicle vehicle = (Vehicle) this.devices.get(deviceID);
            if(location != null){
                vehicle.setLocation(location);
            }
            if(!vehicle.isEnabled()){
                vehicle.setEnabled(enabled);
            }
            if(activity != null && activity.length() > 0){
                vehicle.setActivity(activity);
            }
            if(fee >= 0){
                vehicle.setFee(fee);
            }
        }else{
            // Throw error
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> Error here...");
        }
    }

    // Create a new device of deviceType if it is valid and does not already exist in the device list.
    public void defineDevice(DeviceType deviceType, String deviceID, Location location, boolean enabled, String text) {
        if(deviceType.equals(DeviceType.STREET_SIGN)){
            StreetSign streetSign = new StreetSign(deviceID, location, enabled, text);
            if(this.devices.containsKey(deviceID)){
                throw new Error("?"); // CHANGE TO EXCEPTION LATER
            }else{
                this.devices.put(deviceID,streetSign);
            }
        }else if(deviceType.equals(DeviceType.ROBOT)){
            Robot robot = new Robot(deviceID, location, enabled, text);
            if(this.devices.containsKey(deviceID)){
                throw new Error("?"); // CHANGE TO EXCEPTION LATER
            }else{
                this.devices.put(deviceID,robot);
            }
        }else{
            // Throw error
        }
    }

    // Update an existing device of deviceType.
    public void updateDevice(DeviceType deviceType, String deviceID, boolean enabled, String text) {
        if(deviceType.equals(DeviceType.STREET_SIGN)){
            StreetSign streetSign = (StreetSign) this.devices.get(deviceID);
            if(text != null && text.length() > 0){
                streetSign.setText(text);
            }
            if(!streetSign.isEnabled()){
                streetSign.setEnabled(enabled);
            }
        }else if(deviceType.equals(DeviceType.ROBOT)){
            Robot robot = (Robot) this.devices.get(deviceID);
            if(text != null && text.length() > 0){
                robot.setActivity(text);
            }
            if(!robot.isEnabled()){
                robot.setEnabled(enabled);
            }
        }else{
            // Throw error
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> Error here...");
        }
    }

    // Create a new device of deviceType if it is valid and does not already exist in the device list.
    public void defineDevice(DeviceType deviceType, String deviceID, Location location, boolean enabled, int value) {
        if(deviceType.equals(DeviceType.STREET_LIGHT)){
            StreetLight streetLight = new StreetLight(deviceID, location, enabled, value);
            if(this.devices.containsKey(deviceID)){
                throw new Error("?"); // CHANGE TO EXCEPTION LATER
            }else{
                this.devices.put(deviceID,streetLight);
            }
        }else if(deviceType.equals(DeviceType.PARKING_SPACE)){
            ParkingSpace parkingSpace = new ParkingSpace(deviceID, location, enabled, value);
            if(this.devices.containsKey(deviceID)){
                throw new Error("?"); // CHANGE TO EXCEPTION LATER
            }else{
                this.devices.put(deviceID,parkingSpace);
            }
        }
    }

    // Update an existing device of deviceType.
    public void updateDevice(DeviceType deviceType, String deviceID, boolean enabled, int value) {
        if(deviceType.equals(DeviceType.STREET_LIGHT)){
            StreetLight streetLight = (StreetLight) this.devices.get(deviceID);
            if(!streetLight.isEnabled()){
                streetLight.setEnabled(enabled);
            }
            if(value >= 0){
                streetLight.setBrightness(value);
            }
        }else if(deviceType.equals(DeviceType.PARKING_SPACE)){
            ParkingSpace parkingSpace = (ParkingSpace) this.devices.get(deviceID);
            if(!parkingSpace.isEnabled()){
                parkingSpace.setEnabled(enabled);
            }
            if(value >= 0){
                parkingSpace.setRate(value);
            }
        }else{
            // Throw error
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> Error here...");
        }
    }

    // Create a new device of deviceType if it is valid and does not already exist in the device list.
    public void defineDevice(DeviceType deviceType, String deviceID, Location location, boolean enabled, URI image) {
        if(deviceType.equals(DeviceType.INFORMATION_KIOSK)){
            InformationKiosk informationKiosk = new InformationKiosk(deviceID, location, enabled, image);
            if(this.devices.containsKey(deviceID)){
                throw new Error("?"); // CHANGE TO EXCEPTION LATER
            }else{
                this.devices.put(deviceID,informationKiosk);
            }
        }
    }

    // Update an existing device of deviceType.
    public void updateDevice(DeviceType deviceType, String deviceID, boolean enabled, URI uri) {
        if(deviceType.equals(DeviceType.INFORMATION_KIOSK)){
            InformationKiosk informationKiosk = (InformationKiosk) this.devices.get(deviceID);
            if(!informationKiosk.isEnabled()){
                informationKiosk.setEnabled(enabled);
            }
            if(uri != null){
                informationKiosk.setImage(uri);
            }
        }else{
            // Throw error
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> Error here...");
        }
    }

    // Create a new device of deviceType if it is valid and does not already exist in the device list.
    public void defineDevice(DeviceType deviceType, String deviceID, Location location, boolean enabled, String vehicleType, String activity, int capacity, int fee) throws ModelException {
        if(deviceType.equals(DeviceType.VEHICLE)){
            if(vehicleType.equals("bus")){
                Bus bus = new Bus(deviceID, location, enabled, activity, capacity, fee);
                if(this.devices.containsKey(deviceID)){
                    throw new Error("?"); // CHANGE TO EXCEPTION LATER
                }else{
                    this.devices.put(deviceID,bus);
                }
            }else if(vehicleType.equals("car")){
                Car car = new Car(deviceID, location, enabled, activity, capacity, fee);
                if(this.devices.containsKey(deviceID)){
                    throw new Error("?"); // CHANGE TO EXCEPTION LATER
                }else{
                    this.devices.put(deviceID,car);
                }
            }else{
                // Throw error
            }
        }
    }

    // Get a specific device from the device list if it exists.
    public Device getDevice(String deviceID) {
        return this.devices.get(deviceID);
    }

    // Display details about a device if it exists.
    public void showDevice(String deviceID) {
        if(this.devices.containsKey(deviceID)){
            Device device = this.devices.get(deviceID);
            System.out.println("\tdevice id:");
            System.out.println("\t\t"+device.getDeviceID());
            System.out.println("\tlocation:");
            System.out.println("\t\tlatitude:  "+device.getLocation().getLatitude());
            System.out.println("\t\tlongitude: "+device.getLocation().getLongitude());
            System.out.println("\tenabled:");
            System.out.println("\t\t"+device.isEnabled());
            System.out.println("\tstatus:");
            System.out.println("\t\t"+device.getStatus());
            System.out.println("\tlast event:");
            System.out.println("\t\t"+device.getLastEvent());
            System.out.println();
        }else{
            for(String idOFDevice: this.devices.keySet()){
                Device device = this.devices.get(idOFDevice);
                System.out.println("\tdevice id:");
                System.out.println("\t\t"+device.getDeviceID());
                System.out.println("\tlocation:");
                System.out.println("\t\tlatitude:  "+device.getLocation().getLatitude());
                System.out.println("\t\tlongitude: "+device.getLocation().getLongitude());
                System.out.println("\tenabled:");
                System.out.println("\t\t"+device.isEnabled());
                System.out.println("\tstatus:");
                System.out.println("\t\t"+device.getStatus());
                System.out.println("\tlast event:");
                System.out.println("\t\t"+device.getLastEvent());
                System.out.println();
            }
        }
    }

    // Broadcast a message to all devices within a city.
    public void createSensorOutput(SensorType sensor, String value) {
        for(String deviceID:this.devices.keySet()){
            this.devices.get(deviceID).createSensorOutput(sensor, value);
        }
    }

    // GETTERS/SETTERS
    public String getCityID() { return this.cityID; }
    public HashMap<String,Device> getDevices(){ return this.devices; };
    public String getName(){ return this.name; };
    public String getAccountAddress(){ return this.accountAddress;}
    public Location getLocation() {
        return this.location;
    }
}
