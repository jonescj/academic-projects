package cscie97.smartcity.test;

import cscie97.smartcity.model.CommandProcessor;
import cscie97.smartcity.model.CommandProcessorException;

import java.util.Scanner;

// The TestDriver class runs the Ledger Application through the Command Processing functionalities. If provided, it will
// parse a model script line by line and process it throughout, otherwise, it will start a scanner to take in commands
// real-time.
public class TestDriver {
    public static void main(String[] args) throws CommandProcessorException {
        if(args.length == 0){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the implementation of the Model Service Design Document!".toUpperCase());
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
    }
}
