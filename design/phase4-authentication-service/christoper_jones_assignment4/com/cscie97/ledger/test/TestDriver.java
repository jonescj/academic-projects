package com.cscie97.ledger.test;

import com.cscie97.ledger.CommandProcessException;
import com.cscie97.ledger.CommandProcessor;
import java.util.Scanner;

// The TestDriver class runs the Ledger Application through the Command Processing functionalities. If provided, it will
// parse a ledger script line by line and process it throughout, otherwise, it will start a scanner to take in commands
// real-time.
public class TestDriver {
    public static void main(String[] args) throws CommandProcessException {
        if(args.length == 0){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the implementation of the Ledger Service Design Document!".toUpperCase());
            while(true){
                System.out.print("> ");
                String command = scanner.nextLine();
                CommandProcessor.processCommand(command);
            }
        }else{
            //For Testing: CommandProcessor.processCommandFile("ledger.script");
            CommandProcessor.processCommandFile(args[0]);
        }
    }
}
