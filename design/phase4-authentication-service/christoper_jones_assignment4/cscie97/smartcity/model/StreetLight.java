package cscie97.smartcity.model;

/* The Street Light is an IoT device for illuminating the city. The Street Light is able to adjust its brightness. */
public class StreetLight extends Device{

    private int brightness;

    public StreetLight(String deviceID, Location location, boolean enabled, int brightness) {
        super(deviceID, location, enabled);
        this.brightness = brightness;
    }

    // GETTERS/SETTERS
    public int getBrightness(){ return this.brightness; }
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
