package cscie97.smartcity.model;

/*Robots act as public servants. Robots are mobile and can respond to commands from Residents and Visitors. For example,
 helping to carry groceries. They can also asset in emergencies, for example putting out a fire. */
public class Robot extends Device{

    // The activity assigned to this robot (i.e. “take out the trash”)
    private String activity;

    public Robot(String deviceID, Location location, boolean enabled, String activity) {
        super(deviceID, location, enabled);
        this.activity = activity;
    }

    // GETTERS/SETTERS
    public String getActivity(){ return this.activity; }
    public void setActivity(String activity) {
        this.activity = activity;
    }
}
