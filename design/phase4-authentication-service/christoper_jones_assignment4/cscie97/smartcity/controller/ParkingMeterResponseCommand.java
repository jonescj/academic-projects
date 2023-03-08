package cscie97.smartcity.controller;

import com.cscie97.ledger.Ledger;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/* The ParkingMeterResponseCommand charges a Vehicle that has been parking within it for at least an 1 hour. */
public class ParkingMeterResponseCommand implements Command {
    ArrayList<Observable> subscriptions;
    String cityID, deviceID, value, subject;
    HashMap<String, Ledger> accounts;
    AuthToken token;

    public ParkingMeterResponseCommand(ArrayList<Observable> subscriptions, String cityID, String deviceID, String sensor, String value, String subject, HashMap<String, Ledger> observableAccounts, AuthToken token) {
        this.subscriptions = subscriptions;
        this.cityID = cityID;
        this.deviceID = deviceID;
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
                    if (((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID) instanceof ParkingSpace &&
                            ((Model) observable).getCityMap().get(this.cityID).getDevice(this.subject) instanceof Car) {
                        int fee = ((ParkingSpace) ((Model) observable).getCityMap().get(this.cityID).getDevice(this.deviceID)).getRate();
                        int balance = this.accounts.get(this.cityID).getAccount(this.subject).getBalance();
                        if (balance >= fee) {
                            this.accounts.get(this.cityID).getAccount(this.subject).setBalance(balance - fee);
                        } else {
                            this.accounts.get(this.cityID).getAccount(this.subject).setBalance(0);
                        }
                        System.out.println("\tcontroller - \"" + this.subject + " has been charged " + fee + " units by " + this.deviceID + " for parking\"");
                    }
                    ;
                }
            }
        } else {
            System.out.println("\t{{{CHANGE PERMISSIONS}}}".toUpperCase());
        }
    }
}
