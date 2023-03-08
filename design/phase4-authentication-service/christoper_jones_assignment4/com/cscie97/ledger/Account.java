package com.cscie97.ledger;

/*
The Account class represents an individual account within the Ledger Service. An account contains an address
that provides a unique identity for the Account. The Account also contains a balance that represents the value
of the account. The account can only be updated by the Ledger Service.
*/
public class Account{

    // Unique identifier for the account, assigned automatically when an Account instance is created.
    private String address;

    // Balance of the account which reflects the total transfers to and from the Account, including fees for
    // transactions where the account is the payer.
    private int balance;

    // The Account class represents an individual account within the Ledger Service. An account contains an address
    // that provides a unique identity for the Account. The Account also contains a balance that represents the value
    // of the account. The account can only be updated by the Ledger Service.
    public Account(String address, int balance) throws LedgerException {
        this.address = address;
        this.balance = uint(balance);
    }

    // The uint function assures that for any given balance, it falls in between 0 and Integer.MAX_VALUE inclusively
    private int uint(int balance) throws LedgerException {
        if(balance < 0){
            throw new LedgerException("Invalid Account Balance","The balance for the account "+address+" cannot be negative");
        }else{
            return balance;
        }
    }

    // This is a getter for the address field
    public String getAddress(){
        return this.address;
    }

    // This is a getter for the balance field
    public int getBalance(){
        return this.balance;
    }

    // This is a setter for the balance field
    public void setBalance(int balance) {
        this.balance = balance;
    }
}