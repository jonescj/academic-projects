package cscie97.smartcity.model;

public class ParkingSpace extends Device{

    private int rate;

    public ParkingSpace(String deviceID, Location location, boolean enabled, int rate) {
        super(deviceID, location, enabled);
        this.rate = rate;
    }

    public int getRate(){ return this.rate; }

    public void setRate(int value) {
        this.rate = rate;
    }
}