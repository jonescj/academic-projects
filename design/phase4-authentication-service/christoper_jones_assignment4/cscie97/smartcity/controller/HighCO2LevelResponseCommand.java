package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.*;

import java.util.ArrayList;

/* The High CO2 Response Command disables the cars if the levels are considered unsafe */
public class HighCO2LevelResponseCommand implements Command{
    ArrayList<Observable> subscriptions;
    String cityID,deviceID;
    SensorType sensor;
    AuthToken token;
    public HighCO2LevelResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_device")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    for (String device : ((Model) observable).getCityMap().get(this.cityID).getDevices().keySet()) {
                        if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Car) {
                            ((Car) ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device)).setEnabled(false);
                        }
                    }
                    System.out.println("\tcontroller - CO2 levels are above the maximum allowed levels");
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
