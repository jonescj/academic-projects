package cscie97.smartcity.model;

public class Car extends Vehicle{
    public Car(String deviceID, Location location, boolean enabled, String activity, int capacity, int fee) {
        super(deviceID, location, enabled, activity, capacity, fee);
        if(capacity < 1 || capacity > 20){
            throw new Error("?");
        }
    }
}
