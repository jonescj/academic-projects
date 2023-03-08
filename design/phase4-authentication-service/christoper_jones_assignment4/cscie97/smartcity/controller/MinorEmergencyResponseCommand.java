package cscie97.smartcity.controller;

import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.Model;
import cscie97.smartcity.model.Robot;
import cscie97.smartcity.model.SensorType;

import java.util.ArrayList;

/* This class broadcasts a traffic accident warning to nearby robots to get their help */
public class MinorEmergencyResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, emergency, deviceID;
    float latitude, longitude;
    AuthToken token;

    public MinorEmergencyResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String latitude, String longitude, String emergency, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.latitude = Float.parseFloat(latitude);
        this.longitude = Float.parseFloat(longitude);
        this.emergency = emergency;
        this.deviceID = deviceID;
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_manage_city")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID).createSensorOutput(SensorType.speaker, "Stay calm, help is on its way");
                    ArrayList<Double> robotDistance = new ArrayList<Double>();
                    for (String device : ((Model) observable).getCityMap().get(this.cityID).getDevices().keySet()) {
                        if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Robot) {
                            float x0 = ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device).getLocation().getLatitude();
                            float y0 = ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device).getLocation().getLongitude();
                            float x1 = this.latitude;
                            float y1 = this.longitude;
                            robotDistance.add(Math.sqrt(Math.pow(x1 - x0, 2)) + Math.pow(y1 - y0, 2));
                        }
                    }
                    int a = indexOfSmallest(robotDistance, -1);
                    int b = indexOfSmallest(robotDistance, a);
                    int c = 0;
                    for (String device : ((Model) observable).getCityMap().get(this.cityID).getDevices().keySet()) {
                        if (((Model) observable).getCityMap().get(this.cityID).getDevices().get(device) instanceof Robot) {
                            if (c == a || c == b) {
                                ((Robot) ((Model) observable).getCityMap().get(this.cityID).getDevices().get(device)).setActivity("address " + this.emergency + " at lat " + this.latitude + " long " + this.longitude);
                            }
                            c++;
                        }
                    }
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }


    public static int indexOfSmallest(ArrayList<Double> array, int avoid){
        // add this
        if (array.size() == 0)
            return -1;

        int index = 0;
        if(index == avoid){
            index++;
        }
        double min = array.get(index);

        for (int i = 1; i < array.size(); i++){
            if(i == avoid){
                i++;
                if(i >= array.size()){
                    break;
                }
            }
            if (array.get(i) <= min){
                min = array.get(i);
                index = i;
            }
        }
        return index;
    }
}
