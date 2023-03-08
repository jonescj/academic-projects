package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Car;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;

/* The Low CO2 Response Command re-enables the cars if the levels are considered safe */
public class LowCO2LevelResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, deviceID;
    SensorType sensor;
    AuthToken token;

    public LowCO2LevelResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_control_robot")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    for (String device : ((Model) observable).getCityMap().get(this.cityID).getDevices().keySet()) {
                        if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Car) {
                            ((Car) ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device)).setEnabled(true);
                        }
                    }
                    System.out.println("\tcontroller - CO2 levels are below the maximum allowed levels");
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}

