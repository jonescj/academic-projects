package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.Robot;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;

public class LitterCleanUpResponseCommand implements Command{
    ArrayList<Observable> subscriptions;
    String cityID, deviceID,value;
    SensorType sensor;
    float latitude, longitude;
    AuthToken token;
    public LitterCleanUpResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String latitude, String longitude, String value, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.latitude = Float.parseFloat(latitude);
        this.longitude = Float.parseFloat(longitude);
        this.value = value;
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_control_robot")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    if ((((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID)) instanceof Robot) {
                        ((Robot) (((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID))).setActivity("clean up broken glass at lat " + this.latitude + " long " + this.longitude);
                    }
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
