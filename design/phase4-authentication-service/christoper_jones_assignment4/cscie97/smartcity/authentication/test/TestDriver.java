package cscie97.smartcity.authentication.test;

import com.cscie97.ledger.LedgerException;
import cscie97.smartcity.authentication.AuthToken;
import cscie97.smartcity.authentication.Authentication;
import cscie97.smartcity.authentication.CheckAccess;
import cscie97.smartcity.authentication.CheckInventory;
import cscie97.smartcity.controller.Controller;
import cscie97.smartcity.controller.ControllerException;
import cscie97.smartcity.model.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class TestDriver {
    public static void main(String[] args) throws CommandProcessorException {
        // Initialization of the Authentication Service
        Authentication authService = Authentication.getInstance();

        // Establishment of the Model Service
        if(args.length == 0){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the implementation of the Authentication Service Design Document!".toUpperCase());
            while(true){
                System.out.print("> ");
                String command = scanner.nextLine();
                CommandProcessor.processCommand(command);
            }
        }else{
            //For Testing: CommandProcessor.processCommandFile("sample.script");
            //CommandProcessor.processCommandFile("test.script");
            CommandProcessor.processCommandFile(args[0]);
        }
    }
}
