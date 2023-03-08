package com.cscie97.ledger;

/*
The Ledger Exception is returned from the Ledger condition. The Ledger Exception captures the action that was
attempted and the reason for the failure.
*/
public class LedgerException extends Throwable {

    // Action that was performed
    private String action;

    // Reason for the exception
    private String reason;

    // The Ledger Exception is returned from the Ledger condition. The Ledger Exception captures the action that was
    // attempted and the reason for the failure.
    public LedgerException(String action,String reason){
        this.action = action;
        this.reason = reason;
    }

    // Get Action causing the Exception
    public String getAction() {
        return this.action;
    }

    // Get the reason for Exception
    public String getReason() {
        return this.reason;
    }
}
