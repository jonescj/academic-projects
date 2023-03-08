package cscie97.smartcity.controller;

import com.cscie97.ledger.Ledger;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;
import java.util.HashMap;

/* The BusBoardResponseCommand handles transactions that occur from boarding a bus */
public class BusBoardResponseCommand implements Command{
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    SensorType sensor;
    int latitude, longitude;
    AuthToken token;
    public BusBoardResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String value, String subject, HashMap<String, Ledger> observableAccounts, AuthToken token) {
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
        if(Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_ride_bus")){
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID).createSensorOutput(SensorType.speaker, "hello, good to see you " + this.subject);
                }
            }
        }else{
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
