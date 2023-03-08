package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;

/*The personSeenResponseCommand class is intended to seen commands directly to the Model service such that the
Controller class doesn't need to preserve its own instance of it. It looks for the person specified and updates their
location. */
public class PersonSeenResponseCommand implements Command{
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    SensorType sensor;
    float latitude, longitude;
    AuthToken token;

    public PersonSeenResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String latitude, String longitude, String value, String subject, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.latitude = Float.parseFloat(latitude);
        this.longitude = Float.parseFloat(longitude);
        this.value = value;
        this.subject = subject;
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_control_robot")) {
        for(Observable observable : this.subscriptions){
            if(observable instanceof Model){
                ((Model)observable).getPerson(this.subject).getLocation().setLatitude(this.latitude);
                ((Model)observable).getPerson(this.subject).getLocation().setLongitude(this.longitude);
                System.out.println("\tcontroller - \""+this.subject+" was last seen at lat "+this.latitude+" long "+this.longitude+"\"");
            }
        }
    } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
