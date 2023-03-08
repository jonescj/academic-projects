package cscie97.smartcity.model;

/*The Event class represents the individual events to be processed by a Sensor. Events have a type, action, and an
optional subject. */
public class Event {
    private long timestamp;
    private String value;
    private String subject;
    private SensorType sensor;

    public Event(SensorType sensor, String value, String subject){
        this.timestamp = System.currentTimeMillis();
        this.sensor = sensor;
        this.value = value;
        this.subject = subject;
    }

    @Override
    public String toString(){
        if(this.subject == null){
            return "<sensor> "+this.sensor + " <value> "+this.value;
        }else{
            return "<sensor> "+this.sensor + " <value> "+this.value + " <subject> "+this.subject;
        }
    }
}
