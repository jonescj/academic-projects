package cscie97.smartcity.controller;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.Robot;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;

/*  The MajorEmergencyResponse command broadcasts out a specified emergency response to the entire city. */
public class MajorEmergencyResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, emergency;
    float latitude, longitude;
    AuthToken token;

    public MajorEmergencyResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String latitude, String longitude, String emergency, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.latitude = Float.parseFloat(latitude);
        this.longitude = Float.parseFloat(longitude);
        this.emergency = emergency;
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_city")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    ((Model) observable).getCityMap().get(this.cityID).createSensorOutput(SensorType.speaker, "There is a " + this.emergency + " in " + this.cityID + ", please find shelter immediately");
                    int rcount = 0;
                    int qcount = 0;
                    for (String device : ((Model) observable).getCityMap().get(this.cityID).getDevices().keySet()) {
                        if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Robot) {
                            rcount++;
                        }
                    }
                    for (String device : ((Model) observable).getCityMap().get(this.cityID).getDevices().keySet()) {
                        if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Robot && qcount <= rcount / 2) {
                            ((Robot) ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device)).setActivity("address " + this.emergency + " at lat " + this.latitude + " long " + this.longitude);
                        } else if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Robot && qcount > rcount / 2) {
                            ((Robot) ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device)).setActivity("Help people find shelter");
                        }
                    }
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
