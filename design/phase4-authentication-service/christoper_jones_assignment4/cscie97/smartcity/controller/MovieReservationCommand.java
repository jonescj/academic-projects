package cscie97.smartcity.controller;

import com.cscie97.ledger.Ledger;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/* The MovieReservationCommand allows a controller to trigger a movie reservation event if and only if the person has the funds for it */
public class MovieReservationCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    SensorType sensor;
    HashMap<String, Ledger> accounts;
    AuthToken token;

    public MovieReservationCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String value, String subject, HashMap<String, Ledger> observableAccounts, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
        this.sensor = SensorType.valueOf(sensor);
        this.value = value;
        this.subject = subject;
        this.accounts = observableAccounts;
        this.token = token;
    }

    @Override
    public void execute() {
        if (Authentication.getInstance().checkAccess(token.getId()) && token.getPermissions().contains("scms_control_robot")) {
            for (Observable observable : this.subscriptions) {
                if (observable instanceof Model) {
                    if (((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID) instanceof InformationKiosk) {
                        int fee = 10;
                        int balance = this.accounts.get(this.cityID).getAccount(this.subject).getBalance();
                        if (balance >= fee) {
                            this.accounts.get(this.cityID).getAccount(this.subject).setBalance(balance - fee);
                            System.out.println("\tcontroller - \"" + this.subject + " has been charged " + fee + " units by " + this.deviceID + " for a movie\"");
                            ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID).createSensorOutput(SensorType.speaker, "your seats are reserved; please arrive a few minutes early.");
                        } else {
                            this.accounts.get(this.cityID).getAccount(this.subject).setBalance(0);
                        }
                    }
                    ;
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
