package com.cscie97.ledger;

import java.util.HashMap;
import java.util.Map;

/*
The Ledger manages the Blocks of the blockchain. It also provides the API used by clients of the Ledger.
The Ledger processes transaction processing requests, and also queries about the state of the Ledger, including
Account balances, Transaction details, and Block details.
*/
public class Ledger {

    // Name of the ledger.
    private String name;

    // Ledger description.
    private String description;

    // The Seed that is used as input to the hashing algorithm.
    private String seed;

    // The initial Block of the blockchain.
    public Block genesisBlock;

    // A map of block numbers and the associated
    public HashMap<Integer,Block> blockMap = new HashMap<Integer,Block>();

    // The Ledger manages the Blocks of the blockchain. It also provides the API used by clients of the Ledger.
    // The Ledger processes transaction processing requests, and also queries about the state of the Ledger, including
    // Account balances, Transaction details, and Block details.
    public Ledger(String name, String description, String seed) throws LedgerException {
        this.name = name;
        this.description = description;
        this.seed = seed;
        this.init();
    }

    // The initialize method sets up the genesisBlock and the master account
    private void init() throws LedgerException {
        this.genesisBlock = new Block(1, "", this.seed);
        Account master = this.createAccount("master");
        master.setBalance(Integer.MAX_VALUE);
        this.genesisBlock.accountBalanceMap.put("master",master);
    }

    // Create a new account, assign a unique identifier, and set the balance to 0. Return the account identifier.
    public Account createAccount(String address) throws LedgerException {
        Account newAccount = new Account(address,0);
        this.genesisBlock.accountBalanceMap.put(newAccount.getAddress(),newAccount);
        return newAccount;
    }

    // Return the account balance for the Account with a given address based on the most recent completed block. If
    // the Account does not exist, throw a LedgerException.
    public int getAccountBalance(String address) throws LedgerException {
        int balance = -1;
        for(int i : this.blockMap.keySet()){
            for(String accountID : this.blockMap.get(i).accountBalanceMap.keySet()){
                if(accountID.equals(address)){
                    balance = this.blockMap.get(i).accountBalanceMap.get(accountID).getBalance();
                }
            }
        }
        if(balance < 0){
            throw new LedgerException("Account Not Committed","The account for \""+address+"\" has not been commited yet");
        }else{
            return balance;
        }
    }

    // Process a transaction. Validate the Transaction and if valid, add the Transaction to the current Block and
    // update the associated Account balances for the current Block. Return the assigned transaction id. If the
    // transaction is not valid, throw a LedgerException.
    public String processTransaction(Transaction transaction) throws LedgerException {
        if(transaction.payer.getBalance() >= (transaction.getAmount() + transaction.getFee()) && (transaction.getFee() >= 10)){
            this.updateTransactionList(transaction);
            return transaction.getTransactionID();
        }else{
            throw new LedgerException("Invalid Transaction","The payer "+transaction.payer.getAddress()+" has insufficient funds");
        }
    }

    // Update the accounts involved in a transaction and add them to their respective block
    private void updateTransactionList(Transaction transaction) throws LedgerException {
        try {
            this.genesisBlock.accountBalanceMap.get(transaction.payer.getAddress()).setBalance(this.getAccount(transaction.payer.getAddress()).getBalance() - (transaction.getAmount() + transaction.getFee()));
            this.genesisBlock.accountBalanceMap.get(transaction.receiver.getAddress()).setBalance(this.getAccount(transaction.receiver.getAddress()).getBalance() + transaction.getAmount());
            this.genesisBlock.accountBalanceMap.get("master").setBalance(this.getAccount("master").getBalance() + transaction.getFee());
            this.genesisBlock.transactionList.add(transaction);
            this.checkBlockCompletion();
        }catch(Exception err) {
            throw new LedgerException("Missing Parameter Error",err.getMessage());
        }
    }

    // Check whether or not the Block has hit its Transaction limit. If so, commit it, and set up the next Block
    private void checkBlockCompletion() throws LedgerException {
        if(this.genesisBlock.transactionList.size() >= 10){
            Block completed = this.genesisBlock;
            this.blockMap.put(this.genesisBlock.getBlockNumber(), completed);
            int blockNumber = completed.getBlockNumber() + 1;
            String hash = completed.getHash();
            Block newBlock = new Block(blockNumber, hash, hash);
            newBlock.accountBalanceMap = new HashMap<>();
            newBlock.previousBlock = completed;
            for(String accountID : completed.accountBalanceMap.keySet()){
                newBlock.accountBalanceMap.put(accountID, new Account(accountID,completed.accountBalanceMap.get(accountID).getBalance()));
            }
            this.genesisBlock = newBlock;
        }
    }

    // Return the account balance map for the most recent completed block.
    public Map<String,Integer> getAccountBalances() throws LedgerException {
        Map<String,Account> balanceMapping = null;
        for(int i : this.blockMap.keySet()){
            balanceMapping = this.blockMap.get(i).accountBalanceMap;
        }
        if(balanceMapping == null){
            throw new LedgerException("No Block Committed","There are no existing committed blocks");
        }else{
            Map<String,Integer> balances = new HashMap<String,Integer>();
            for(String accountID : balanceMapping.keySet()){
                balances.put(accountID, balanceMapping.get(accountID).getBalance());
            }
            return balances;
        }
    }

    // Return the Block for the given block number.
    public Block getBlock(int blockNumber){
        return this.blockMap.get(blockNumber);
    }

    // Return the Transaction for the given transaction id.
    public Transaction getTransaction(String transactionID){
        for(int i : this.blockMap.keySet()){
            for(int j = 0; j < this.blockMap.get(i).transactionList.size(); j++){
                if(this.blockMap.get(i).transactionList.get(j).getTransactionID().equals(transactionID)){
                    return this.blockMap.get(i).transactionList.get(j);
                }
            }
        }
        return null;
    }

    // Validate the current state of the blockchain. For each block, check the hash of the previous hash, make sure
    // that the account balances total to the max value. Verify that each completed block has exactly 10 transactions.
    public void validate(){
        for(int i : this.blockMap.keySet()){
            int total = 0;
            for(String accountID : this.blockMap.get(i).accountBalanceMap.keySet()){
                total = total + this.blockMap.get(i).accountBalanceMap.get(accountID).getBalance();
            }
            if(total == Integer.MAX_VALUE && this.blockMap.get(i).transactionList.size() == 10){
                System.out.println("Block #"+i+": Valid");
            }else{
                System.out.println("Block #"+i+": Not Valid");
            }
        }
    }

    // Get the number of committed blocks
    public int getBlockCounts(){
        return this.blockMap.size();
    }

    public Account getAccount(String accountID) {
        return this.genesisBlock.accountBalanceMap.get(accountID);
    }
}