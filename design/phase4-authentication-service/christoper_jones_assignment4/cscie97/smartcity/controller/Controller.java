package cscie97.smartcity.controller;

import com.cscie97.ledger.Ledger;
import com.cscie97.ledger.LedgerException;
import com.cscie97.ledger.Transaction;
import cscie97.smartcity.authentication.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/* The Smart City Controller Service is responsible for monitoring the people and devices within a given city and
regulating their activity within their respective city. It responds to status updates and actions that occur within
the city through the lens of the device sensors. Additionally, it assures that the people within these cities are
living as they should by assuring that they are financially stable, acknowledged for hindrances within the city,
notified for emergencies within the city, and have their general questions and concerns responded to. */
public class Controller implements Observer {

    // # TESTING AUTH SERVICE
    private Authentication authService;

    private ArrayList<Observable> subscriptions; // A list of items the controller has subscribed to
    private ArrayList<String> eventQueue; // A list of events collected for the items subscribed to
    private ArrayList<HashMap<String,String>> observedEvents; // A list of processed events from the eventQueue
    private HashMap<String, Ledger> observableAccounts; // A list of managed accounts associated with the observables
    public Controller(){
        subscriptions = new ArrayList<Observable>();
        observableAccounts = new HashMap<String, Ledger>();
        eventQueue = new ArrayList<String>();
        observedEvents = new ArrayList<HashMap<String,String>>();

        // # AUTHENTICATION
        authService = Authentication.getInstance();
    }

    /*
    * The addSubscription function adds an observable object to the control to monitor
    * @param subscription
    */
    public void addSubscription(Observable subscription){
        subscriptions.add(subscription);
    }

    /*
     * The removeSubscription function removes an observable object from the control
     * @param subscription
     */
    public void removeSubscription(Observable subscription){
        subscriptions.remove(subscription);
    }

    /*
     * The addToEventQueue function collects events from the observable object
     * @param event
     */
    public void addToEventQueue(String event){
        eventQueue.add(event.trim());
    }

    /*
     * The processEventQueue function analyzes the events from the observable object
     * @param event
     */
    public void processEventQueue() throws LedgerException, URISyntaxException, ControllerException {
        for(int i = 0; i < eventQueue.size(); i++){
            System.out.println(eventQueue.get(i));
            HashMap<String,String> packet = new HashMap<String,String>();
            if(eventQueue.get(i).contains("<subject>")){
                packet.put("<subject>",getAllAfter(eventQueue.get(i),"<subject>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<subject>")));
            }
            if(eventQueue.get(i).contains(" long ")){
                packet.put("<long>",cutOffAfter(cutOffAfter(getAllAfter(eventQueue.get(i),"long"),','),'"'));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("long")));
            }
            if(eventQueue.get(i).contains(" lat ")){
                packet.put("<lat>",cutOffAfter(cutOffAfter(getAllAfter(eventQueue.get(i),"lat"),','),'"'));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("lat")));
            }
            if(eventQueue.get(i).contains("<value>")){
                packet.put("<value>",getAllAfter(eventQueue.get(i),"<value>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<value>")));
            }
            if(eventQueue.get(i).contains("<sensor>")){
                packet.put("<sensor>",getAllAfter(eventQueue.get(i),"<sensor>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<sensor>")));
            }
            if(eventQueue.get(i).contains("<account>")){
                packet.put("<account>",getAllAfter(eventQueue.get(i),"<account>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<account>")));
            }
            if(eventQueue.get(i).contains("<personID>")){
                packet.put("<personID>",getAllAfter(eventQueue.get(i),"<personID>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<personID>")));
            }
            if(eventQueue.get(i).contains("<deviceID>")){
                packet.put("<deviceID>",getAllAfter(eventQueue.get(i),"<deviceID>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<deviceID>")));
            }
            if(eventQueue.get(i).contains("<cityID>")){
                packet.put("<cityID>",getAllAfter(eventQueue.get(i),"<cityID>"));
                eventQueue.set(i,eventQueue.get(i).substring(0,eventQueue.get(i).indexOf("<cityID>")));
            }
            if(eventQueue.get(i).length() > 0){
                throw new ControllerException("NullEventError","The current event has a length of 0");
            }
            observedEvents.add(packet);
        }
        eventQueue = new ArrayList<String>();
        processObversedEvents(); // authService.login()
    }

    /*
     * The processObversedEvents function analyzes the events from the event queue
     * @param event
     */
    public void processObversedEvents() throws LedgerException, URISyntaxException {
        HashMap<String,Integer> counterMap = new HashMap<String,Integer>();
        while(!observedEvents.isEmpty()){
            // AUTHENTICATION SERVICE CODE
            AuthToken token;
            AuthToken usertoken;
            try {
                token = authService.login("controller", "controller-voiceprint");
            }catch(Exception | AuthenticationException | AccessDeniedException | InvalidAuthTokenException e1){
                try{
                    token = authService.login("controller", "controller-faceprint");
                }catch(Exception | AuthenticationException | AccessDeniedException | InvalidAuthTokenException e2){
                    throw new Error("Failure estabishing token in controller class...");
                }
            }
            // Set up accounts for new cities
            if(observedEvents.get(0).keySet().size() == 1 && observedEvents.get(0).containsKey("<cityID>")){
                String accountName = observedEvents.get(0).get("<cityID>");
                String description = "The ledger account for "+accountName;
                String seed = accountName;
                if(!observableAccounts.containsKey(accountName)) {
                    observableAccounts.put(accountName, new Ledger(accountName, description, seed));
                    System.out.println("\tcontroller - \"set up new account for "+accountName+"\"");
                }
            }
            // Welcome visitors
            else if(observedEvents.get(0).keySet().size() == 2 && observedEvents.get(0).containsKey("<cityID>") && observedEvents.get(0).containsKey("<personID>")){
                String visitorName = observedEvents.get(0).get("<personID>");
                String cityName = observedEvents.get(0).get("<cityID>");
            }
            // Setup accounts for new devices
            else if(observedEvents.get(0).keySet().size() == 2 && observedEvents.get(0).containsKey("<cityID>") && observedEvents.get(0).containsKey("<deviceID>")){
                String parentName = observedEvents.get(0).get("<cityID>");
                String accountName = observedEvents.get(0).get("<deviceID>");
                if(observableAccounts.get(parentName).getAccount(accountName) == null) {
                    observableAccounts.get(parentName).createAccount(accountName);
                    System.out.println("\tcontroller - \"set up new account for "+accountName+"\"");
                    observableAccounts.get(parentName).processTransaction(new Transaction("transaction-0-"+accountName, 1000, 10, "init-"+accountName, observableAccounts.get(parentName).getAccount("master"), observableAccounts.get(parentName).getAccount(accountName)));
                }
            }
            // Setup accounts for new residents
            else if(observedEvents.get(0).keySet().size() == 3 && observedEvents.get(0).containsKey("<cityID>") && observedEvents.get(0).containsKey("<personID>") && observedEvents.get(0).containsKey("<account>")){
                String parentName = observedEvents.get(0).get("<cityID>");
                String accountName = observedEvents.get(0).get("<personID>");
                if(observableAccounts.get(parentName).getAccount(accountName) == null) {
                    observableAccounts.get(parentName).createAccount(accountName);
                    System.out.println("\tcontroller - \"set up new account for "+accountName+"\"");
                    observableAccounts.get(parentName).processTransaction(new Transaction("transaction-0-"+accountName, 1000, 10, "init-"+accountName, observableAccounts.get(parentName).getAccount("master"), observableAccounts.get(parentName).getAccount(accountName)));
                }
            }
            // Notify city of emergencies & response to clean up events
            else if(observedEvents.get(0).keySet().size() == 6 && observedEvents.get(0).containsKey("<cityID>") && observedEvents.get(0).containsKey("<deviceID>") && observedEvents.get(0).containsKey("<sensor>") && observedEvents.get(0).containsKey("<value>")){
                if(observedEvents.get(0).get("<sensor>").equals("camera") && ((observedEvents.get(0).get("<value>").contains("flood")) || (observedEvents.get(0).get("<value>").contains("fire")) || (observedEvents.get(0).get("<value>").contains("severe weather")) || (observedEvents.get(0).get("<value>").contains("earthquake"))) && isFloat(observedEvents.get(0).get("<lat>").trim()) && isFloat(observedEvents.get(0).get("<long>").trim())){
                    if(observedEvents.get(0).get("<value>").contains("flood")){
                        new MajorEmergencyResponseCommand(subscriptions, observedEvents.get(0).get("<cityID>"), observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"), observedEvents.get(0).get("<lat>"), observedEvents.get(0).get("<long>"), "flood",token).execute();
                        System.out.println("\tcontroller - \"a flood has been reported by "+observedEvents.get(0).get("<deviceID>")+"\"");
                    }else if(observedEvents.get(0).get("<value>").contains("fire")){
                        new MajorEmergencyResponseCommand(subscriptions, observedEvents.get(0).get("<cityID>"), observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"), observedEvents.get(0).get("<lat>"), observedEvents.get(0).get("<long>"), "fire",token).execute();
                        System.out.println("\tcontroller - \"a fire has been reported by "+observedEvents.get(0).get("<deviceID>")+"\"");
                    }else if(observedEvents.get(0).get("<value>").contains("severe weather")){
                        new MajorEmergencyResponseCommand(subscriptions, observedEvents.get(0).get("<cityID>"), observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"), observedEvents.get(0).get("<lat>"), observedEvents.get(0).get("<long>"), "severe weather",token).execute();
                        System.out.println("\tcontroller - \"severe weather has been reported by "+observedEvents.get(0).get("<deviceID>")+"\"");
                    }else if(observedEvents.get(0).get("<value>").contains("earthquake")){
                        new MajorEmergencyResponseCommand(subscriptions, observedEvents.get(0).get("<cityID>"), observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"), observedEvents.get(0).get("<lat>"), observedEvents.get(0).get("<long>"), "earthquake",token).execute();
                        System.out.println("\tcontroller - \"an earthquake has been reported by "+observedEvents.get(0).get("<deviceID>")+"\"");
                    }
                }else if(observedEvents.get(0).get("<sensor>").equals("camera") && ((observedEvents.get(0).get("<value>").contains("traffic accident"))) && isFloat(observedEvents.get(0).get("<lat>")) && isFloat(observedEvents.get(0).get("<long>"))){
                    new MinorEmergencyResponseCommand(subscriptions, observedEvents.get(0).get("<cityID>"), observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"), observedEvents.get(0).get("<lat>"), observedEvents.get(0).get("<long>"), "flood",token).execute();
                    System.out.println("\tcontroller - \"a traffic accident has been reported by "+observedEvents.get(0).get("<deviceID>")+"\"");
                }else if(observedEvents.get(0).get("<sensor>").equals("microphone") && ((observedEvents.get(0).get("<value>").contains("breaking glass"))) && isFloat(observedEvents.get(0).get("<lat>")) && isFloat(observedEvents.get(0).get("<long>"))){
                    new LitterCleanUpResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<lat>"),observedEvents.get(0).get("<long>"),observedEvents.get(0).get("<value>"),token).execute();
                }else{
                    // Non-Actionable
                }
            }
            // ???
            else if(observedEvents.get(0).keySet().size() == 7 && observedEvents.get(0).containsKey("<cityID>") && observedEvents.get(0).containsKey("<deviceID>") && observedEvents.get(0).containsKey("<sensor>") && observedEvents.get(0).containsKey("<value>") &&  observedEvents.get(0).containsKey("<subject>")){
                if(observedEvents.get(0).get("<value>").contains("garbage") && observedEvents.get(0).get("<sensor>").equals("camera") && isFloat(observedEvents.get(0).get("<lat>")) && isFloat(observedEvents.get(0).get("<long>"))){
                    new LitterEventResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<lat>"),observedEvents.get(0).get("<long>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),observableAccounts,token).execute();
                }else if(observedEvents.get(0).get("<value>").contains("seen") && observedEvents.get(0).get("<sensor>").equals("camera") && isFloat(observedEvents.get(0).get("<lat>")) && isFloat(observedEvents.get(0).get("<long>"))){
                    new PersonSeenResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<lat>"),observedEvents.get(0).get("<long>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),token).execute();
                }else{
                    // Non-Actionable
                }
            }
            // Respond to CO2 Events
            else{
                if(observedEvents.get(0).get("<sensor>").contains("co2meter")){
                    String key = "";
                    if(observedEvents.get(0).get("<value>").equals("CO2 Over 1000")){
                        key = observedEvents.get(0).get("<cityID>")+" "+"CO2 Over 1000";
                    }else if(observedEvents.get(0).get("<value>").equals("CO2 Under 1000")){
                        key = observedEvents.get(0).get("<cityID>")+" "+"CO2 Under 1000";
                    }else{
                        Float value = Float.parseFloat(observedEvents.get(0).get("<value>"));
                        if(value >= 1000){
                            key = observedEvents.get(0).get("<cityID>")+" "+"CO2 Over 1000";
                        }else{
                            key = observedEvents.get(0).get("<cityID>")+" "+"CO2 Under 1000";
                        }
                    }
                    if(counterMap.containsKey(key)){
                        counterMap.put(key,counterMap.get(key)+1);
                        if(counterMap.get(key) >= 3 && key.contains("CO2 Over 1000")){
                            new HighCO2LevelResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"),token).execute();
                        }else if(counterMap.get(key) >= 3 && key.contains("CO2 Under 1000")){
                            new LowCO2LevelResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"), observedEvents.get(0).get("<sensor>"),token).execute();
                        }
                    }else{
                        counterMap.put(key,1);
                    }
                }
                else if(observedEvents.get(0).get("<value>").contains("Can you help me find my child")){
                    new MissingChildResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),token).execute();
                }else if(observedEvents.get(0).get("<value>").contains("parked")){
                    new ParkingMeterResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),observableAccounts,token).execute();
                }else if(observedEvents.get(0).get("<value>").contains("Does this bus go")){
                    new BusRouteRequestResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),token).execute();
                }else if(observedEvents.get(0).get("<value>").contains("boards bus")){
                    new BusBoardResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),observableAccounts,token).execute();
                }else if(observedEvents.get(0).get("<value>").contains("what movies are showing")){
                    new MovieInfoResponseCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),token).execute();
                }else if(observedEvents.get(0).get("<value>").contains("reserve") && observedEvents.get(0).get("<value>").contains("seats") && observedEvents.get(0).get("<value>").contains("showing")){
                    new MovieReservationCommand(subscriptions,observedEvents.get(0).get("<cityID>"),observedEvents.get(0).get("<deviceID>"),observedEvents.get(0).get("<sensor>"),observedEvents.get(0).get("<value>"),observedEvents.get(0).get("<subject>"),observableAccounts,token).execute();
                }else{
                    // Non-actionable
                }
            }

            observedEvents.remove(0);
        }
    }

    private String cutOffAfter(String string, char separator) {
        return string.split(separator+"")[0];
    }

    private boolean isFloat(String number) {
        if (number == null) {
            return false;
        }
        try {
            float i = Float.parseFloat(number.replace("\"",""));
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private String getAllAfter(String given, String after){
        return given.substring(given.indexOf(after) + after.length()+1, given.length()).trim();
    }

    @Override
    public void update() throws URISyntaxException, ControllerException, LedgerException {
        this.processEventQueue();
    }

    public void requestAuthToken(String cityID) throws AuthenticationException {
        if(!authService.getUsers().containsKey("controller")) {
            authService.createUser("controller", "Controller Service");
            authService.addCredential("controller", "password", "secret");
            authService.addCredential("controller", "biometric", "voiceprint:controller-voiceprint");
            authService.addCredential("controller", "biometric", "faceprint:controller-faceprint");
        }
        authService.createResourceRole(cityID + "_public_administrator_resource_role", "public_admin_role", cityID);
    }
    public void revokeAuthToken(String cityID){
        // IMPLEMENT WHEN NEEDED
    }
}
