package cscie97.smartcity.controller;

public class ObservorException extends Throwable{

    // Action that was performed
    private String action;

    // Reason for the exception
    private String reason;

    // The Controller Exception is returned from the Controller condition. The Model Exception captures the action that was
    // attempted and the reason for the failure.
    public ObservorException(String action,String reason){
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
