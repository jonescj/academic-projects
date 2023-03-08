package cscie97.smartcity.controller;

import com.cscie97.ledger.Ledger;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.Robot;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;
import java.util.HashMap;

/* The LitterEventResponse command will notify any individuals who are littering to stop */
public class LitterEventResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    SensorType sensor;
    float latitude, longitude;
    AuthToken token;

    public LitterEventResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String latitude, String longitude, String value, String subject, HashMap<String, Ledger> observableAccounts, AuthToken token) {
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
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    if (((Model) (observable)).getCityMap().get(cityID).getDevice(deviceID) instanceof Robot) {
                        ((Robot) ((Model) (observable)).getCityMap().get(cityID).getDevice(deviceID)).createSensorOutput(SensorType.speaker, "Please do not litter");
                        ((Robot) ((Model) (observable)).getCityMap().get(cityID).getDevice(deviceID)).setActivity("clean garbage at lat " + this.latitude + " long " + this.longitude);
                        System.out.println("\tcontroller - \"" + this.subject + " is littering\"");
                    }
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
