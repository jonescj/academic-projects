package cscie97.smartcity.model;

/*
The Model Exception is returned from the Ledger condition. The Ledger Exception captures the action that was
attempted and the reason for the failure.
*/
public class ModelException extends Throwable {

    // Action that was performed
    private String action;

    // Reason for the exception
    private String reason;

    // The Model Exception is returned from the Model condition. The Model Exception captures the action that was
    // attempted and the reason for the failure.
    public ModelException(String action,String reason){
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
