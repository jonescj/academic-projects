package com.cscie97.ledger;

/*
The Transaction class represents a transaction in the Ledger System. A transaction contains a transaction id,
an amount, a fee, a note, and references a payer account and a receiver account. The transaction amount is
transferred from the payer’s account balance to the receiver’s account balance. The transaction fee is
transferred from the payer’s account to the master account. Transactions are aggregated within blocks.
*/
public class Transaction{

    // Unique identifier for the transaction assigned to the transaction by the Ledger System.
    private String transactionID;

    // The transaction amount to be deducted from the payer account and added to the receiver’s account. The amount
    // must be greater or equal to 0 and less than or equal to max.integer.
    private int amount;

    // The fee is taken from the payer account and added to the master account. The fee must be greater than or equal
    // to 10.
    private int fee;

    // An arbitrary string that may be up to 1024 characters in length.
    private String note;

    // The Account issuing the transaction. The amount of the transaction and the transaction fee will be deducted
    // from the payer’s account balance.
    public Account payer;

    // The amount of the transaction will be added to the balance of the receiver account.
    public Account receiver;

    // The Transaction class represents a transaction in the Ledger System. A transaction contains a transaction id,
    // an amount, a fee, a note, and references a payer account and a receiver account. The transaction amount is
    // transferred from the payer’s account balance to the receiver’s account balance. The transaction fee is
    // transferred from the payer’s account to the master account. Transactions are aggregated within blocks.
    public Transaction(String transactionID, int amount, int fee, String note, Account payer, Account receiver) {
        this.transactionID = transactionID;
        this.amount = amount;
        this.fee = fee;
        this.note = note;
        this.payer = payer;
        this.receiver = receiver;
    }

    // Getter for Transaction ID
    public String getTransactionID() {
        return this.transactionID;
    }

    // Getter for Amount
    public int getAmount(){
        return this.amount;
    }

    // Getter for Fee
    public int getFee(){
        return this.fee;
    }

    // Getter for Note
    public String getNote(){
        return this.note;
    }
}