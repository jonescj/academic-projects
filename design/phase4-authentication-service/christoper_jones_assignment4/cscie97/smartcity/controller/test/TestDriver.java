package cscie97.smartcity.controller.test;

import cscie97.smartcity.model.CommandProcessor;
import cscie97.smartcity.model.CommandProcessorException;

import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) throws CommandProcessorException {

        if(args.length == 0){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the implementation of the Model Service + Controller Design Document!".toUpperCase());
            while(true){
                System.out.print("> ");
                String command = scanner.nextLine();
                CommandProcessor.processCommand(command);
            }
        }else{
            //For Testing: CommandProcessor.processCommandFile("model.script");
            //CommandProcessor.processCommandFile("test.script");
            CommandProcessor.processCommandFile(args[0]);
        }

        /*Controller controllerService = new Controller();
        Model modelService = new Model("SmartCityModel", "---", "---");

        modelService.attach(controllerService);
        modelService.defineCity("Seattle-WA", "Seattle-WA", "Seattle-WA", new Location(0, 0, 100));

        // Random observables
        modelService.defineVehicle("Seattle-WA", "car-1", new Location(0, 0, 0), true, "car", "parked", 4, 0);
        modelService.defineResident("john", "john", "john", "(555)555-5555", Role.child, new Location(0, 0, 0), "jane");
        modelService.defineResident("jane", "jane", "jane", "(555)555-5555", Role.adult, new Location(0, 0, 0), "jane");
        modelService.defineVisitor("joe", "joe", new Location(0, 0, 0));

        // Major Emergency Event
        modelService.defineRobot("Seattle-WA", "robot-1", new Location(0, 0, 0), true, "waiting");
        modelService.defineRobot("Seattle-WA", "robot-2", new Location(0, 0, 0), true, "waiting");
        modelService.defineRobot("Seattle-WA", "robot-3", new Location(0, 0, 0), true, "waiting");
        modelService.defineRobot("Seattle-WA", "robot-4", new Location(0, 0, 0), true, "waiting");
        modelService.createSensorEvent("Seattle-WA", "robot-1", SensorType.camera, "Emergency fire at lat 0 long 0", null);
        modelService.createSensorEvent("Seattle-WA", "robot-2", SensorType.camera, "Emergency flood at lat 0 long 0", null);
        modelService.createSensorEvent("Seattle-WA", "robot-3", SensorType.camera, "Emergency earthquake at lat 0 long 0", null);
        modelService.createSensorEvent("Seattle-WA", "robot-4", SensorType.camera, "Emergency severe weather at lat 0 long 0", null);

        // Minor Emergency Event
        modelService.defineRobot("Seattle-WA", "robot-5", new Location(0, 0, 0), true, "waiting");
        modelService.createSensorEvent("Seattle-WA", "robot-5", SensorType.camera, "Emergency traffic accident at lat 0 long 0", null);

        // CO2 Over Event
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-1", new Location(0, 0, 0), true, new URI(".png"));
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-2", new Location(0, 0, 0), true, new URI(".png"));
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-3", new Location(0, 0, 0), true, new URI(".png"));
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-1", SensorType.co2meter, "CO2 Over 1000", null);
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-2", SensorType.co2meter, "CO2 Over 1000", null);
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-3", SensorType.co2meter, "CO2 Over 1000", null);

        // Litter Event
        modelService.defineRobot("Seattle-WA", "robot-6", new Location(0, 0, 0), true, "waiting");
        modelService.createSensorEvent("Seattle-WA", "robot-6", SensorType.camera, "Person john throws garbage at lat 0 long 0", "john");

        // Broken Glass Event
        modelService.defineRobot("Seattle-WA", "robot-7", new Location(0, 0, 0), true, "waiting");
        modelService.createSensorEvent("Seattle-WA", "robot-7", SensorType.microphone, "sound of breaking glass at lat 0 long 0", null);

        // Person Seen Event
        modelService.defineRobot("Seattle-WA", "robot-8", new Location(0, 0, 0), true, "waiting");
        modelService.createSensorEvent("Seattle-WA", "robot-8", SensorType.camera, "Person john seen at lat 0 long 0", "john");

        // Missing Child
        modelService.defineRobot("Seattle-WA", "robot-9", new Location(0, 0, 0), true, "waiting");
        modelService.createSensorEvent("Seattle-WA", "robot-9", SensorType.microphone, "Can you help me find my child john", "john");

        // Parking Event
        modelService.defineParkingSpace("Seattle-WA", "parking-space-1", new Location(0, 0, 0), true, 10);
        modelService.createSensorEvent("Seattle-WA", "parking-space-1", SensorType.camera, "Vehicle car-1 parked for 1 hour", "car-1");

        // Bus Route
        modelService.defineVehicle("Seattle-WA", "bus-1", new Location(0, 0, 0), true, "bus", "going to central square", 20, 10);
        modelService.createSensorEvent("Seattle-WA", "bus-1", SensorType.microphone, "Person john says \"Does this bus go to central square?\"", "john");


        // Board Bus
        modelService.defineVehicle("Seattle-WA", "bus-2", new Location(0, 0, 0), true, "bus", "going to central square", 20, 10);
        modelService.createSensorEvent("Seattle-WA", "bus-2", SensorType.microphone, "Person john boards bus", "john");

        // Movie Info Event
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-4", new Location(0, 0, 0), true, new URI(".png"));
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-4", SensorType.microphone, "Person john says \"what movies are showing tonight?\"", "john");

        // Movie Reservation
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-5", new Location(0, 0, 0), true, new URI(".png"));
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-5", SensorType.microphone, "Person john says \"reserve 2 seats for 9 pm showing of Casablanca.\"", "john");

        // CO2 Under 1000
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-6", new Location(0, 0, 0), true, new URI(".png"));
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-7", new Location(0, 0, 0), true, new URI(".png"));
        modelService.defineInformationKiosk("Seattle-WA", "info-kiosk-8", new Location(0, 0, 0), true, new URI(".png"));
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-6", SensorType.co2meter, "CO2 Under 1000", null);
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-7", SensorType.co2meter, "CO2 Under 1000", null);
        modelService.createSensorEvent("Seattle-WA", "info-kiosk-8", SensorType.co2meter, "CO2 Under 1000", null);

        // Notify
        modelService.notify(controllerService);

        for (String cityID : modelService.getCityMap().keySet()) {
            System.out.println(cityID);
            for (String deviceID : modelService.getCityMap().get(cityID).getDevices().keySet()) {
                if (modelService.getCityMap().get(cityID).getDevices().get(deviceID) instanceof Robot) {
                    String activity = ((Robot) (modelService.getCityMap().get(cityID).getDevices().get(deviceID))).getActivity();
                    String event = ((Robot) (modelService.getCityMap().get(cityID).getDevices().get(deviceID))).getLastEvent().toString();
                    System.out.println("-> " + deviceID);
                    System.out.println("---> " + activity);
                }
            }
        }

        modelService.notify(controllerService);*/
    }
}
