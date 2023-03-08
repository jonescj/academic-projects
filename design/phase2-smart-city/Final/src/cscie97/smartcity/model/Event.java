package cscie97.smartcity.model;

/*The Event class represents the individual events to be processed by a Sensor. Events have a type, action, and an
optional subject. */
public class Event {
    private long timestamp;
    private String value;
    private String subject;

    public Event(String value, String subject){
        this.timestamp = System.currentTimeMillis();
        this.value = value;
        this.subject = subject;
    }
}
