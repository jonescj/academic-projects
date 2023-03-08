package cscie97.smartcity.model;

public class Bus extends Vehicle{
    public Bus(String deviceID, Location location, boolean enabled, String activity, int capacity, int fee) {
        super(deviceID, location, enabled, activity, capacity, fee);
        if(capacity < 10 || capacity > 200){
            throw new Error("?");
        }
    }
}
