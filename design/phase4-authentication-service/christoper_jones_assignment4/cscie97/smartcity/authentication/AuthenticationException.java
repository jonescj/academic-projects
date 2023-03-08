package cscie97.smartcity.authentication;

/*
The AuthenticationException is thrown whenever a reference error occurs within the Authentication Service. That is,
if attempting to apply an action to a user, resource, role, permission, etc. that does not exist, this exception will
be thrown in response.
 */
public class AuthenticationException extends Throwable{

    // Action that was performed
    private String action;

    // Reason for the exception
    private String reason;

    // The Authentication Exception is returned from the Auth condition. The Model Exception captures the action that was
    // attempted and the reason for the failure.
    public AuthenticationException(String action,String reason){
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
