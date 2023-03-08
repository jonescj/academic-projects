package cscie97.smartcity.model;

import com.cscie97.ledger.LedgerException;
import cscie97.smartcity.authentication.*;
import cscie97.smartcity.controller.Controller;
import cscie97.smartcity.controller.ControllerException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;

// The CommandProcessor is a utility class for feeding the Model a set of operations, using command syntax.
public class CommandProcessor {

    // A test controller instance
    private static Controller controller;

    // The model instance
    private static Model model;

    // Process a single command. The output of the command is formatted and displayed to stdout. Throw a
    // CommandProcessorException on error.
    public static void processCommand(String command) throws CommandProcessorException {
        HashMap<String, String[]> acceptableCommands = initializeAcceptableCommands();
        try {
            if (command == null) {
                throw new CommandProcessorException(command, "the command could not be found", 0);
            } else if (command.length() == 0 || command.charAt(0) == '#') {
                // DO NOTHING
            } else {
                System.out.println("\n> " + command);
                String[] parameters = command.split(" ");
                String[] subcommands = acceptableCommands.get(parameters[0] + " " + parameters[1]);
                String[] inputs = new String[subcommands.length - 1];
                Arrays.fill(inputs, "");

                int current = 1;
                for (int i = 0; i < parameters.length; i++) {
                    if (subcommands[current].equals(parameters[i]) && i + 1 < parameters.length) {
                        inputs[current - 1] = parameters[i + 1];
                        i++;
                        if (current + 1 < subcommands.length) {
                            if (cantFindOption(parameters, subcommands[current + 1])) {
                                if(inputs[current - 1].contains("\"")) {
                                    char last = ((inputs[current - 1]).charAt((inputs[current - 1]).length()-1));
                                    while (i + 1 < parameters.length && !parameters[i + 1].equals(subcommands[current + 1]) && (last != '\"')) {
                                        i++;
                                        inputs[current - 1] = inputs[current - 1] + " " + parameters[i];
                                        last = ((inputs[current - 1]).charAt((inputs[current - 1]).length()-1));
                                    }
                                }

                                current++;
                                if (current + 1 > subcommands.length) {
                                    break;
                                }
                            } else {
                                while (i + 1 < parameters.length && !parameters[i + 1].equals(subcommands[current + 1])) {
                                    i++;
                                    inputs[current - 1] = inputs[current - 1] + " " + parameters[i];
                                }
                            }
                        } else {

                            while (i + 1 < parameters.length) {
                                i++;
                                inputs[current - 1] = inputs[current - 1] + " " + parameters[i];
                            }
                        }
                        current++;
                        if (current + 1 > subcommands.length) {
                            break;
                        }
                    } else if (cantFindOption(parameters, subcommands[current])) {
                        current++;
                        if (current < subcommands.length) {
                            while (cantFindOption(parameters, subcommands[current])) {
                                current++;
                                i--;
                            }
                        }
                        if(parameters[i].equals(subcommands[current])){
                            i--;
                        }
                    }
                }

                String[] ids = inputs[0].split(":");
                if (ids.length < 2) {
                    String zero = ids[0];
                    ids = new String[2];
                    ids[0] = zero;
                }
                String[] input2 = inputs[0].split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                switch (parameters[0] + " " + parameters[1]) {
                    case "initialize model":
                        initializeModel(inputs[0], inputs[1], inputs[2]);
                        break;
                    case "define city":
                        defineCity(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
                        break;
                    case "show city":
                        showCity(inputs[0], inputs[1], inputs[2]);
                        break;
                    case "define street-sign":
                        defineStreetSign(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
                        break;
                    case "update street-sign":
                        updateStreetSign(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4]);
                        break;
                    case "define info-kiosk":
                        defineInformationKiosk(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
                        break;
                    case "update info-kiosk":
                        updateInformationKiosk(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4]);
                        break;
                    case "define street-light":
                        defineStreetLight(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
                        break;
                    case "update street-light":
                        updateStreetLight(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4]);
                        break;
                    case "define parking-space":
                        defineParkingSpace(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
                        break;
                    case "update parking-space":
                        updateParkingSpace(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4]);
                        break;
                    case "define robot":
                        defineRobot(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
                        break;
                    case "update robot":
                        updateRobot(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
                        break;
                    case "define vehicle":
                        defineVehicle(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7], inputs[8], inputs[9]);
                        break;
                    case "update vehicle":
                        updateVehicle(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
                        break;
                    case "show device":
                        showDevice(ids[0], ids[1], inputs[1], inputs[2]);
                        break;
                    case "create sensor-event":
                        createSensorEvent(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
                        break;
                    case "create sensor-output":
                        createSensorOutput(ids[0], ids[1], inputs[1], inputs[2], inputs[3], inputs[4]);
                        break;
                    case "define resident":
                        defineResident(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7], inputs[8], inputs[9]);
                        break;
                    case "update resident":
                        updateResident(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7], inputs[8], inputs[9]);
                        break;
                    case "define visitor":
                        defineVisitor(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
                        break;
                    case "update visitor":
                        updateVisitor(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
                        break;
                    case "show person":
                        showPerson(inputs[0],inputs[1], inputs[2]);
                        break;
                    case "initialize controller":
                        initializeController();
                        break;
                    case "attach controller":
                        attachController();
                        break;
                    case "notify controller":
                        notifyController();
                        break;
                    case "detach controller":
                        detachController();
                        break;
                    case "define permission":
                        createPermission(input2[0],input2[1],input2[2]);
                        break;
                    case "define role":
                        createRole(input2[0],input2[1],input2[2]);
                        break;
                    case "add permission_to_role":
                        addPermission(input2[0],input2[1]);
                        break;
                    case "add role_to_user":
                        addRole(input2[0],input2[1]);
                        break;
                    case "create user":
                        createUser(input2[0],input2[1]);
                        break;
                    case "add user_credential":
                        addCredential(input2[0],input2[1],input2[2]);
                        break;
                    default:
                        throw new CommandProcessorException("Cannot Find Command", "Cannot find the command <" + parameters[0] + ">", 0);
                }
            }
        }catch(ModelException | AuthenticationException | AccessDeniedException | InvalidAuthTokenException err){
            throw new CommandProcessorException("InputValidationError","The command <"+command+"> has produced an error!",0);
        }
    }

    private static void addRole(String id, String role) throws AuthenticationException {
        Authentication.getInstance().addRole(id,role);
    }

    private static void addCredential(String id, String type, String secret) throws AuthenticationException {
        Authentication.getInstance().addCredential(id,type, secret);
    }

    private static void createUser(String id, String name) throws AuthenticationException {
        Authentication.getInstance().createUser(id,name);
    }

    private static void addPermission(String role, String id) throws AuthenticationException {
        Authentication.getInstance().addPermission(role, id);
    }

    private static void createRole(String id, String name, String description) throws AuthenticationException {
        Authentication.getInstance().createRole(id,name,description);
    }

    private static void createPermission(String id, String name, String description) throws cscie97.smartcity.authentication.AuthenticationException {
        Authentication.getInstance().createPermission(id,name,description);
    }

    // Process a set of commands provided within the given commandFile. Throw a CommandProcessorException
    // on error.
    public static void processCommandFile(String file) throws CommandProcessorException {
        int lineNumber = 0;
        try{
            File script = new File(file);
            FileReader scriptReader = new FileReader(script);
            BufferedReader bufferedReader = new BufferedReader(scriptReader);
            String line;

            while((line = bufferedReader.readLine()) != null){
                lineNumber++;
                processCommand(line);
            }
            bufferedReader.close();
            scriptReader.close();

        }catch(Exception err){
            throw new CommandProcessorException("Command Processing Issue", err.getMessage(), lineNumber);
        }
    }

    /* ---------------------------------------------- MODEL COMMANDS ---------------------------------------------- */

    // Initialize Model
    // initialize model name <name> description <description> seed <seed>
    private static void initializeModel(String name, String description, String seed) throws CommandProcessorException {
        try{
            model = new Model(name,description,seed);
        }catch(ModelException e){
            throw new CommandProcessorException("Model Creation Failed","There was an issue creating this Model",0);
        }
    }

    /* ---------------------------------------------- CONTROLLER COMMANDS ----------------------------------------------  */

    // Initialize Test Controller
    // initialize controller
    private static void initializeController() throws CommandProcessorException {
        try{
            controller = new Controller();
        }catch(Exception e){
            throw new CommandProcessorException("Controller Creation Failed","There was an issue creating this Controller",0);
        }
    }

    // Attach Test Controller
    // attach controller
    private static void attachController() throws CommandProcessorException {
        try{
            model.attach(controller);
        }catch(Exception | AuthenticationException e){
            throw new CommandProcessorException("Controller Creation Failed","There was an issue creating this Controller",0);
        }
    }

    // Notify Test Controller
    // notify controller
    private static void notifyController() throws CommandProcessorException {
        try{
            model.notify(controller);
        }catch(Exception | ControllerException | LedgerException e){
            throw new CommandProcessorException("Controller Creation Failed","There was an issue creating this Controller",0);
        }
    }

    // Detach Test Controller
    // Detach controller
    private static void detachController() throws CommandProcessorException {
        try{
            model.detach(controller);
        }catch(Exception e){
            throw new CommandProcessorException("Controller Creation Failed","There was an issue creating this Controller",0);
        }
    }
    /* ---------------------------------------------- CITY COMMANDS ---------------------------------------------- */

    // Define a city
    // COMMAND: define city <city_id> name <name> account <address> lat <float> long <float> radius <float>
    private static void defineCity(String cityID, String name, String accountAddress, String latitude, String longitude, String radius, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        model.defineCity(cityID,name,accountAddress,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),Float.parseFloat(radius)), Authentication.getInstance().login(username, password));
    }

    // Show the details of a city. Print out the details of the city including the id, name, account, location, people,
    // and IoT devices.
    // COMMAND: show city <city_id>
    private static void showCity(String cityID, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        model.showCity(cityID,Authentication.getInstance().login(username, password));
    }

    /* ---------------------------------------------- DEVICE COMMANDS ---------------------------------------------- */

    // Define a street sign
    // define street-sign <city_id>:<device_id> lat <float> long <float> enabled (true|false) text <text>
    private static void defineStreetSign(String cityID, String deviceID, String latitude, String longitude, String enabled, String text, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).defineDevice(DeviceType.valueOf("STREET_SIGN"), deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),text);
        model.defineStreetSign(cityID,deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),text,Authentication.getInstance().login(username, password));
    }

    // Update a street sign
    // update street-sign <city_id>:<device_id> [enabled (true|false)] [text <text>]
    private static void updateStreetSign(String cityID, String deviceID, String enabled, String text, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).updateDevice(DeviceType.valueOf("STREET_SIGN"),deviceID,Boolean.parseBoolean(enabled),text);
        model.updateStreetSign(cityID,deviceID,Boolean.parseBoolean(enabled),text,Authentication.getInstance().login(username, password));
    }

    // Define an information kiosk
    // define info-kiosk <city_id>:<device_id> lat <float> long <float> enabled (true|false) image <uri>
    private static void defineInformationKiosk(String cityID, String deviceID, String latitude, String longitude, String enabled, String uri, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).defineDevice(DeviceType.valueOf("INFORMATION_KIOSK"), deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),URI.create(uri));
        model.defineInformationKiosk(cityID,deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),URI.create(uri), Authentication.getInstance().login(username, password));
    }

    // Update an information kiosk
    // update info-kiosk <city_id>:<device_id> [enabled (true|false)] [image <uri>]
    private static void updateInformationKiosk(String cityID, String deviceID, String enabled, String uri, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).updateDevice(DeviceType.valueOf("INFORMATION_KIOSK"),deviceID,Boolean.parseBoolean(enabled),URI.create(uri));
        model.updateInformationKiosk(cityID,deviceID,Boolean.parseBoolean(enabled),URI.create(uri), Authentication.getInstance().login(username, password));
    }

    // Define an street light
    // define street-light <city_id>:<device_id> lat <float> long <float> enabled (true|false) brightness <int>
    private static void defineStreetLight(String cityID, String deviceID, String latitude, String longitude, String enabled, String brightness, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).defineDevice(DeviceType.valueOf("STREET_LIGHT"), deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),Integer.parseInt(brightness));
        model.defineStreetLight(cityID,deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),Integer.parseInt(brightness),Authentication.getInstance().login(username, password));
    }

    // Update an street light
    // update street-light <city_id>:<device_id> [enabled (true|false)] [brightness <int>]
    private static void updateStreetLight(String cityID, String deviceID, String enabled, String brightness, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).updateDevice(DeviceType.valueOf("STREET_LIGHT"),deviceID,Boolean.parseBoolean(enabled),Integer.parseInt(brightness));
        model.updateStreetLight(cityID,deviceID,Boolean.parseBoolean(enabled),Integer.parseInt(brightness), Authentication.getInstance().login(username, password));
    }

    // Define a parking space
    // define parking-space <city_id>:<device_id> lat <float> long <float> enabled (true|false) rate <int>
    private static void defineParkingSpace(String cityID, String deviceID, String latitude, String longitude, String enabled, String rate, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).defineDevice(DeviceType.valueOf("PARKING_SPACE"), deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),Integer.parseInt(rate));
        model.defineParkingSpace(cityID,deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),Integer.parseInt(rate), Authentication.getInstance().login(username, password));
    }

    // Update a parking space
    // update parking-space <city_id>:<device_id> [enabled (true|false)] [rate <int>]
    private static void updateParkingSpace(String cityID, String deviceID, String enabled, String rate, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).updateDevice(DeviceType.valueOf("PARKING_SPACE"),deviceID,Boolean.parseBoolean(enabled),Integer.parseInt(rate));
        model.updateParkingSpace(cityID,deviceID,Boolean.parseBoolean(enabled),Integer.parseInt(rate), Authentication.getInstance().login(username, password));
    }

    // Define a robot
    // define robot <city_id>:<device_id> lat <float> long <float> enabled (true|false) activity <string>
    private static void defineRobot(String cityID, String deviceID, String latitude, String longitude, String enabled, String activity, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).defineDevice(DeviceType.valueOf("ROBOT"), deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),activity);
        model.defineRobot(cityID,deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled),activity, Authentication.getInstance().login(username, password));
    }

    // Update a robot
    // update robot <city_id>:<device_id> [lat <float> long <float>] [enabled (true|false)] [activity <string>]
    private static void updateRobot(String cityID, String deviceID, String latitude, String longitude, String enabled, String activity, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).updateDevice(DeviceType.valueOf("ROBOT"),deviceID,Boolean.parseBoolean(enabled),activity);
        model.updateRobot(cityID,deviceID,Boolean.parseBoolean(enabled),activity, Authentication.getInstance().login(username, password));
    }

    // Define a vehicle
    // define vehicle <city_id>:<device_id> lat <float> long <float> enabled (true|false) type (bus|car) activity <string> capacity <int> fee <int>
    private static void defineVehicle(String cityID, String deviceID, String latitude, String longitude, String enabled, String vehicleType, String activity, String capacity, String fee, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).defineDevice(DeviceType.valueOf("VEHICLE"), deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled), vehicleType, activity, Integer.parseInt(capacity),Integer.parseInt(fee));
        model.defineVehicle(cityID,deviceID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),Boolean.parseBoolean(enabled), vehicleType, activity, Integer.parseInt(capacity),Integer.parseInt(fee), Authentication.getInstance().login(username, password));
    }

    // Update a vehicle
    // update vehicle <city_id>:<device_id> [lat <float> long <float>] [enabled (true|false)] [activity <string>] [fee <int>]
    private static void updateVehicle(String cityID, String deviceID, String latitude, String longitude, String enabled, String activity, String fee, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        if(latitude.length() == 0 || latitude.length() == 0){
            //model.getCity(cityID).updateDevice(DeviceType.valueOf("VEHICLE"), deviceID, null, Boolean.parseBoolean(enabled), activity, Integer.parseInt(fee));
            model.updateVehicle(cityID,deviceID,null,Boolean.parseBoolean(enabled), activity, Integer.parseInt(fee), Authentication.getInstance().login(username, password));
        }else if(fee.length() == 0){
            //model.getCity(cityID).updateDevice(DeviceType.valueOf("VEHICLE"), deviceID, new Location(Float.parseFloat(latitude), Float.parseFloat(longitude), 0), Boolean.parseBoolean(enabled), activity, -1);
            model.updateVehicle(cityID,deviceID,new Location(Float.parseFloat(latitude), Float.parseFloat(longitude), 0), Boolean.parseBoolean(enabled), activity, -1, Authentication.getInstance().login(username, password));
        }else {
            //model.getCity(cityID).updateDevice(DeviceType.valueOf("VEHICLE"), deviceID, new Location(Float.parseFloat(latitude), Float.parseFloat(longitude), 0), Boolean.parseBoolean(enabled), activity, Integer.parseInt(fee));
            model.updateVehicle(cityID,deviceID,new Location(Float.parseFloat(latitude), Float.parseFloat(longitude), 0), Boolean.parseBoolean(enabled), activity, Integer.parseInt(fee), Authentication.getInstance().login(username, password));
        }
    }

    // Show the details of a device, if device id is omitted, show details for all devices within the city
    // COMMAND: show device <city_id>[:<device_id>]
    private static void showDevice(String cityID,String deviceID, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).showDevice(deviceID);
        model.showDevice(cityID,deviceID, Authentication.getInstance().login(username, password));
    }

    // Simulate a device sensor event
    // COMMAND: create sensor-event <city_id>[:<device_id>] type (microphone|camera|thermometer|co2meter) value <string> [subject <person_id>]
    private static void createSensorEvent(String cityID, String deviceID, String type, String value, String personID, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getCity(cityID).getDevice(deviceID).createSensorEvent(SensorType.valueOf(type),value,personID);
        model.createSensorEvent(cityID,deviceID,SensorType.valueOf(type),value,personID, Authentication.getInstance().login(username, password));
    }

    // Simulate a device output
    // COMMAND: create sensor-output <city_id>[:<device_id>] type (speaker) value <string>
    private static void createSensorOutput(String cityID, String deviceID, String type, String value, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        if(deviceID == null){
            //model.getCity(cityID).createSensorOutput(SensorType.valueOf(type),value);
            model.createSensorOutput(cityID,deviceID,SensorType.valueOf(type),value, Authentication.getInstance().login(username, password));
        }else {
            //model.getCity(cityID).getDevice(deviceID).createSensorOutput(SensorType.valueOf(type), value);
            model.createSensorOutput(cityID,deviceID,SensorType.valueOf(type), value, Authentication.getInstance().login(username, password));
        }
    }

    /* ---------------------------------------------- PERSON COMMANDS ---------------------------------------------- */

    // Define a new Resident
    // COMMAND: define resident <person_id> name <name> bio-metric <string> phone <phone_number> role (adult|child|administrator) lat <lat> long <long> account <account_address>
    private static void defineResident(String personID, String name, String biometricID, String phoneNumber, String role, String latitude, String longitude, String accountAddress, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.definePerson(personID,name,biometricID,phoneNumber,Role.getRole(role),new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),accountAddress);
        model.defineResident(personID,name,biometricID,phoneNumber,Role.getRole(role),new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0),accountAddress, Authentication.getInstance().login(username, password));
    }

    // Update a Resident
    // COMMAND: update resident <person_id> [name <name>] [bio-metric <string>] [phone <phone_number>] [role (adult|child|administrator)] [lat <lat> long <long>] [account <account_address>]
    private static void updateResident(String personID, String name, String biometricID, String phoneNumber, String role, String latitude, String longitude, String accountAddress, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        if(latitude.length() == 0 || longitude.length() == 0){
            //((Resident) model.getPerson(personID)).update(name, biometricID, phoneNumber, Role.getRole(role), null, accountAddress);
            model.updateResident(personID,name, biometricID, phoneNumber, Role.getRole(role), null, accountAddress, Authentication.getInstance().login(username, password));
        }else {
            //((Resident) model.getPerson(personID)).update(name, biometricID, phoneNumber, Role.getRole(role), new Location(Float.parseFloat(latitude), Float.parseFloat(longitude), 0), accountAddress);
            model.updateResident(personID,name, biometricID, phoneNumber, Role.getRole(role), new Location(Float.parseFloat(latitude), Float.parseFloat(longitude), 0), accountAddress, Authentication.getInstance().login(username, password));
        }
    }

    // Define a new Visitor
    // COMMAND: define visitor <person_id> bio-metric <string> lat <lat> long <long>
    private static void defineVisitor(String personID, String biometricID, String latitude, String longitude, String username, String password) throws ModelException, AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.definePerson(personID,biometricID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0));
        model.defineVisitor(personID,biometricID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0), Authentication.getInstance().login(username, password));
    }

    // Update a Visitor
    // COMMAND: update visitor <person_id> [bio-metric <string>] [lat <lat> long <long>]
    private static void updateVisitor(String personID, String biometricID, String latitude, String longitude, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        if(latitude.length() == 0 || longitude.length() == 0){
            //((Visitor)model.getPerson(personID)).update(biometricID,null);
            model.updateVisitor(personID,biometricID,null, Authentication.getInstance().login(username, password));
        }else {
            //((Visitor)model.getPerson(personID)).update(biometricID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0));
            model.updateVisitor(personID,biometricID,new Location(Float.parseFloat(latitude),Float.parseFloat(longitude),0), Authentication.getInstance().login(username, password));
        }
    }

    // Show the details of the person
    // COMMAND: show person <person_id>
    private static void showPerson(String personID, String username, String password) throws AuthenticationException, AccessDeniedException, InvalidAuthTokenException {
        //model.getPerson(personID).showPerson();
        model.showPerson(personID, Authentication.getInstance().login(username, password));
    }

    // Add an acceptable command input to the command processor
    private static void addAcceptableCommand(HashMap<String, String[]> acceptableCommands, String... commandList){
        acceptableCommands.put(commandList[0],commandList);
    }

    // Construct an acceptable format for incoming commands
    private static HashMap<String, String[]> initializeAcceptableCommands() {
        HashMap<String, String[]> acceptableCommands = new HashMap<String, String[]>();
        addAcceptableCommand(acceptableCommands,"initialize model","model","description","seed");
        addAcceptableCommand(acceptableCommands,"define city","city","name","account","lat","long","radius","username","password");
        addAcceptableCommand(acceptableCommands,"show city","city","username","password");
        addAcceptableCommand(acceptableCommands,"define street-sign","street-sign","lat","long","enabled","text","username","password");
        addAcceptableCommand(acceptableCommands,"update street-sign","street-sign","enabled","text","username","password");
        addAcceptableCommand(acceptableCommands,"define info-kiosk","info-kiosk","lat","long","enabled","image","username","password");
        addAcceptableCommand(acceptableCommands,"update info-kiosk","info-kiosk","enabled","image","username","password");
        addAcceptableCommand(acceptableCommands,"define street-light","street-light","lat","long","enabled","brightness","username","password");
        addAcceptableCommand(acceptableCommands,"update street-light","street-light","enabled","brightness","username","password");
        addAcceptableCommand(acceptableCommands,"define parking-space","parking-space","lat","long","enabled","rate","username","password");
        addAcceptableCommand(acceptableCommands,"update parking-space","parking-space","enabled","rate","username","password");
        addAcceptableCommand(acceptableCommands,"define robot","robot","lat","long","enabled","activity","username","password");
        addAcceptableCommand(acceptableCommands,"update robot","robot","lat","long","enabled","activity","username","password");
        addAcceptableCommand(acceptableCommands,"define vehicle","vehicle","lat","long","enabled","type","activity","capacity","fee","username","password");
        addAcceptableCommand(acceptableCommands,"update vehicle","vehicle","lat","long","enabled","activity","fee","username","password");
        addAcceptableCommand(acceptableCommands,"show device","device","username","password");
        addAcceptableCommand(acceptableCommands,"create sensor-event","sensor-event","type","value","subject","username","password");
        addAcceptableCommand(acceptableCommands,"create sensor-output","sensor-output","type","value","username","password");
        addAcceptableCommand(acceptableCommands,"define resident","resident","name","bio-metric","phone","role","lat","long","account","username","password");
        addAcceptableCommand(acceptableCommands,"update resident","resident","name","bio-metric","phone","role","lat","long","account","username","password");
        addAcceptableCommand(acceptableCommands,"define visitor","visitor","bio-metric","lat","long","username","password");
        addAcceptableCommand(acceptableCommands,"update visitor","visitor","bio-metric","lat","long","username","password");
        addAcceptableCommand(acceptableCommands,"show person","person","username","password");
        // TEST COMMANDS
        addAcceptableCommand(acceptableCommands,"initialize controller","controller");
        addAcceptableCommand(acceptableCommands,"attach controller","controller");
        addAcceptableCommand(acceptableCommands,"notify controller","controller");
        addAcceptableCommand(acceptableCommands,"detach controller","controller");
        // MORE TEST COMMANDS
        addAcceptableCommand(acceptableCommands,"define permission","permission");
        addAcceptableCommand(acceptableCommands,"define role","role");
        addAcceptableCommand(acceptableCommands,"add permission_to_role","permission_to_role");
        addAcceptableCommand(acceptableCommands,"create user","user");
        addAcceptableCommand(acceptableCommands,"add user_credential","user_credential");
        addAcceptableCommand(acceptableCommands,"add role_to_user","role_to_user");
        return acceptableCommands;
    }

    // Search if an optional parameter has been removed
    private static boolean cantFindOption(String[] list, String e) {
        for(int i = 0; i < list.length; i++){
            if(list[i].equals(e)){
                return false;
            }
        }
        return true;
    }
}
