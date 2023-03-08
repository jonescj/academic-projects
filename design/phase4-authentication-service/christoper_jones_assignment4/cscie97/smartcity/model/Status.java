package cscie97.smartcity.model;

/* The Status class is an enum class storing the values READY and OFFLINE  to indicate the status of each of the
individual devices. By default, they are all offline, but can be switched to ready on update. */
public enum Status {
    READY, // An indicator that a device is ready to use
    OFFLINE; // An indicator that a device is offline

    // GETTER
    public static Status getStatus(String value){
        try {
            return Status.valueOf(value);
        }catch(Exception err){
            return null;
        }
    }
}
