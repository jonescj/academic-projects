package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.InformationKiosk;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.SensorType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/* The MovieInfoResponse class allows the controller to trigger a movie info event such that a person can get info
* about an upcoming movie */
public class MovieInfoResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    SensorType sensor;
    AuthToken token;

    public MovieInfoResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String value, String subject, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.value = value;
        this.subject = subject;
        this.token = token;
    }

    @Override
    public void execute() throws URISyntaxException {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_control_robot")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID).createSensorOutput(SensorType.speaker, "Casablanca is showing at 9 pm");
                    ((InformationKiosk) ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID)).setImage(new URI("https://en.wikipedia.org/wiki/Casablanca_(film)#/media/File:CasablancaPoster-Gold.jpg"));
                    System.out.println("\tcontroller - \"the image for " + this.deviceID + " has been changed to: " + ((InformationKiosk) ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID)).getImage() + "\"");
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
