package cscie97.smartcity.authentication;

// The InvalidAuthTokenException is thrown whenever a token has expired or been logged out of, and someone is still trying to use it.
public class InvalidAuthTokenException extends Throwable{

    // Action that was performed
    private String action;

    // Reason for the exception
    private String reason;

    // The Controller Exception is returned from the Controller condition. The Model Exception captures the action that was
    // attempted and the reason for the failure.
    public InvalidAuthTokenException(String action,String reason){
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