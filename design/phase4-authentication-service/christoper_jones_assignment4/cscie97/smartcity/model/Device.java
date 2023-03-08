package cscie97.smartcity.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Device {

    private String deviceID;
    public Location location;
    private boolean enabled;
    private Status status;
    private Event lastEvent;

    private HashMap<SensorType, ArrayList<Event>> sensors;

    public Device(String deviceID, Location location, boolean enabled){

        this.deviceID = deviceID;
        this.location = location;
        this.enabled = enabled;
        this.status = Status.OFFLINE;
        this.sensors = new HashMap<SensorType, ArrayList<Event>>();

        this.sensors.put(SensorType.camera, new ArrayList<Event>());
        this.sensors.put(SensorType.co2meter, new ArrayList<Event>());
        this.sensors.put(SensorType.microphone, new ArrayList<Event>());
        this.sensors.put(SensorType.speaker, new ArrayList<Event>());
        this.sensors.put(SensorType.thermometer, new ArrayList<Event>());

        this.lastEvent = null;
    }

    public void createSensorEvent(SensorType sensor, String value, String personID) {
        Event event = new Event(sensor,value,personID);
        this.sensors.get(sensor).add(0,event);
        this.lastEvent = event;
    }

    public void createSensorOutput(SensorType sensor, String value) {
        Event event = new Event(sensor,value,null);
        this.sensors.get(sensor).add(0,event);
        this.lastEvent = event;
    }

    public Event getLastEvent(){ return this.lastEvent; }

    public Status getStatus(){ return this.status; }

    public boolean isEnabled(){ return this.enabled; }

    public Location getLocation(){
        return this.location;
    }

    public String getDeviceID(){return this.deviceID;};

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
