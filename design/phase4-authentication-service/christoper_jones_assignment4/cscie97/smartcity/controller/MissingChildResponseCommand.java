package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.*;

import java.util.ArrayList;

/* The MissingChildResponseCommand allows the controller to help find people within the Smart Model's city  */
public class MissingChildResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    SensorType sensor;
    AuthToken token;

    public MissingChildResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String value, String subject, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.value = value;
        this.subject = subject;
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_control_robot")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    Location personLocation = ((Model) observable).getPerson(this.subject).getLocation();
                    Location deviceLocation = ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID).getLocation();
                    ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID).createSensorOutput(SensorType.speaker, "person " + this.subject + " is at lat " + personLocation.getLatitude() + " long " + personLocation.getLongitude() + ", a robot is retrieving now, stay where you are.");
                    ((Robot) ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID)).setActivity("retrieve person " + this.subject + " and bring to lat " + deviceLocation.getLatitude() + " long " + deviceLocation.getLatitude() + " of microphone.");
                    System.out.println("\tcontroller - \"" + this.deviceID + " has found " + this.subject + "\"");
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
