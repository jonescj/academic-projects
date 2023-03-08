package com.cscie97.ledger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

/*
The CommandProcessor is a utility class for feeding the Ledger a set of operations, using command syntax.
*/
public class CommandProcessor {

    // The ledger instance
    private static Ledger ledger;

    // Process a single command. The output of the command is formatted and displayed to stdout. Throw a
    // CommandProcessorException on error.
    public static void processCommand(String command) throws CommandProcessException {
        try {
            HashMap<String, String[]> acceptableCommands = initializeAcceptableCommands();
            if (command == null) {
                throw new CommandProcessException(command, "the command could not be found", 0);
            } else if (command.length() == 0 || command.charAt(0) == '#') {
                // DO NOTHING
            } else {
                String[] parameters = command.split(" ");
                String[] subcommands = acceptableCommands.get(parameters[0]);
                String[] inputs = new String[subcommands.length];
                Arrays.fill(inputs, "");

                int current = 1;
                if (current <= inputs.length && current != parameters.length) {
                    for (int s = 0; s < subcommands.length; s++) {
                        inputs[s] = parameters[current];
                        current++;
                        if (s + 1 < subcommands.length) {
                            try {
                                while (!parameters[current].equals(subcommands[s + 1])) {
                                    inputs[s] = inputs[s] + " " + parameters[current];
                                    current++;
                                }
                            } catch (Exception err) {
                                throw new CommandProcessException(command, "Could not find parameter: " + subcommands[s + 1], -1);
                            }
                        } else {
                            while (current < parameters.length) {
                                inputs[s] = inputs[s] + " " + parameters[current];
                                current++;
                            }
                        }
                        current++;
                    }
                }
                switch (parameters[0]) {
                    case "create-ledger":
                        createLedger(inputs[0], inputs[1], inputs[2]);
                        break;
                    case "create-account":
                        createAccount(inputs[0]);
                        break;
                    case "get-account-balance":
                        getAccountBalance(inputs[0]);
                        break;
                    case "process-transaction":
                        processTransaction(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
                        break;
                    case "get-account-balances":
                        getAccountBalances();
                        break;
                    case "get-block":
                        getBlock(inputs[0]);
                        break;
                    case "get-transaction":
                        getTransaction(inputs[0]);
                        break;
                    case "validate":
                        validate();
                        break;
                    default:
                        throw new CommandProcessException("Cannot Find Command", "Cannot find the command <"+parameters[0]+">", 0);
                }
            }
        }catch(LedgerException err){
            System.out.println("!"+err.getAction().toUpperCase()+": ["+err.getReason()+"]");
            //throw new CommandProcessException("Command Processing Issue", err.getMessage(), 0);
        }
    }

    // Process a set of commands provided within the given commandFile. Throw a CommandProcessorException
    // on error.
    public static void processCommandFile(String file) throws CommandProcessException {
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
            throw new CommandProcessException("Command Processing Issue", err.getMessage(), lineNumber);
        }
    }

    // Create a new ledger with the given name, description, and seed value.
    // COMMAND: create-ledger <name> description <description> seed <seed>
    private static void createLedger(String name, String description, String seed) throws CommandProcessException {
        System.out.println("\n>>> Creating a Ledger w/ parameters ("+name+", "+description+", "+seed+")");
        try{
            ledger = new Ledger(name, description, seed);
        }catch(LedgerException e){
            throw new CommandProcessException("Ledger Creation Failed","There was an issue creating this Ledger",0);
        }
    }

    // Create a new account with the given account id.
    // COMMAND: create-account <account-id>
    private static void createAccount(String accountID) throws LedgerException {
        System.out.println("\n>>> Creating an Account w/ parameters ("+accountID+")");
        ledger.createAccount(accountID);
    }

    // Output the account balance for the given account
    // COMMAND: get-account-balance <account-id>
    private static void getAccountBalance(String accountID) throws LedgerException {
        System.out.println("\n>>> Get Account Balances w/ parameters ("+accountID+")");
        System.out.println(ledger.getAccountBalance(accountID));
    }

    // Process a new transaction. Return an error message if the transaction is invalid, otherwise output the
    // transaction id.
    // COMMAND: process-transaction <transaction-id> amount <amount> fee <fee> note <note> payer <account-address>
    // receiver <account-address>
    private static void processTransaction(String transactionID, String amount, String fee, String note, String payer,
                                           String receiver) throws LedgerException {
        System.out.println("\n>>> Processing Transaction w/ parameters ("+transactionID+", "+amount+", "+fee+", "+note+", "+payer+", "+receiver+")");
        ledger.processTransaction(new Transaction(transactionID, Integer.parseInt(amount), Integer.parseInt(fee), note,
                ledger.getAccount(payer), ledger.getAccount(receiver))); // use variables here....
    }

    // Output the account balances for the most recent completed block.
    // COMMAND: get-account-balances
    private static void getAccountBalances() throws LedgerException {
        System.out.println("\n>>> Get all Account Balances w/ parameters ()");
        System.out.println(ledger.getAccountBalances());
    }

    // Output the details for the given block number.
    // COMMAND: get-block <block-number>
    private static void getBlock(String blockNumber) throws LedgerException {
        try {
            System.out.println("\n>>> Get Block details w/ parameters (" + blockNumber + ")");
            Block result = ledger.getBlock(Integer.parseInt(blockNumber));
            System.out.println("Hash:" + result.getHash());
            for (int i = 0; i < result.transactionList.size(); i++) {
                System.out.println("Transaction:" + result.transactionList.get(i).getTransactionID() + " > " + result.transactionList.get(i).payer.getAddress() + ":" + result.transactionList.get(i).receiver.getAddress() + " ($" + result.transactionList.get(i).getAmount() + ")");
            }
        }catch(Exception err){
            throw new LedgerException("Nonexistent Hash","Unable to find the Hash of the Block");
        }
    }

    // Output the details of the given transaction id. Return an error if the transaction was not found in the Ledger.
    // get-transaction <transaction-id>
    private static void getTransaction(String transactionID) throws LedgerException {
        try {
            System.out.println("\n>>> Get Transaction details w/ parameters (" + transactionID + ")");
            Transaction result = ledger.getTransaction(transactionID);
            System.out.println("ID:      \t" + result.getTransactionID());
            System.out.println("Payer:   \t" + result.payer.getAddress());
            System.out.println("Receiver:\t" + result.receiver.getAddress());
            System.out.println("Amount:  \t" + result.getAmount());
            System.out.println("Fee:     \t" + result.getFee());
            System.out.println("Note:    \t" + result.getNote());
        }catch(Exception err){
            throw new LedgerException("Nonexistent Transaction","Cannot Find TransactionID <"+transactionID+">");
        }
    }

    // Validate the current state of the blockchain.
    // COMMAND: validate
    private static void validate() {
        System.out.println("\n>>> Validating details w/ parameters ()");
        ledger.validate();
    }

    // Add an acceptable command input to the command processor
    private static void addAcceptableCommand(HashMap<String, String[]> acceptableCommands, String... commandList){
        acceptableCommands.put(commandList[0],commandList);
    }

    // Add acceptable commands into the command processor
    private static HashMap<String, String[]> initializeAcceptableCommands(){
        HashMap<String, String[]> acceptableCommands = new HashMap<String, String[]>();
        addAcceptableCommand(acceptableCommands,"create-ledger","description","seed");
        addAcceptableCommand(acceptableCommands,"create-account");
        addAcceptableCommand(acceptableCommands,"get-account-balance");
        addAcceptableCommand(acceptableCommands,"process-transaction","amount","fee","note","payer","receiver");
        addAcceptableCommand(acceptableCommands,"get-account-balances");
        addAcceptableCommand(acceptableCommands,"get-block");
        addAcceptableCommand(acceptableCommands,"get-transaction");
        addAcceptableCommand(acceptableCommands,"validate");
        return acceptableCommands;
    }
}
