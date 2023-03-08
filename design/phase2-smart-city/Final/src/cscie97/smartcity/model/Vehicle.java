package cscie97.smartcity.model;

/*Vehicles are mobile IoT Devices that are used for giving rides to Residents and Visitors. Vehicles can be either a
Bus or a Car. Vehicles have a maximum rider capacity. Both Cars and Busses are autonomous. Riding in a Bus or Car is
free for Visitors, but requires a fee for Residents. */
public class Vehicle extends Device{

    private int capacity;
    private int fee;
    private String actvity;

    public Vehicle(String deviceID, Location location, boolean enabled, String activity, int capacity, int fee) {
        super(deviceID, location, enabled);
        this.capacity = capacity;
        this.fee = fee;
        this.actvity = activity;
    }

    // GETTERS/SETTERS
    public int getCapacity(){ return this.capacity; }
    public int getFee(){ return this.fee; };
    public String getActvity(){ return this.actvity; }
    public void setActivity(String activity) {
        this.actvity = activity;
    }
    public void setFee(int fee) {
        this.fee = fee;
    }
}
