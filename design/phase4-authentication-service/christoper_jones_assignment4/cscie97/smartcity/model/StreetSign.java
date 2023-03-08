package cscie97.smartcity.model;

/*A street sign is an IoT device that provides information for vehicles. It is able to alter the text displayed on the
sign. For example, it can dynamically adjust the speed limit, or warn about an accident ahead. */
public class StreetSign extends Device{

    private String text;

    public StreetSign(String deviceID, Location location, boolean enabled, String text) {
        super(deviceID, location, enabled);
        this.text = text;
    }

    // GETTERS/SETTERS
    public String getText(){ return this.text; }
    public void setText(String text) {
        this.text = text;
    }
}
