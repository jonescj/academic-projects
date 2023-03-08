package cscie97.smartcity.model;

/*The role of a resident identifies who they are in reference to the others. This can potentially be used to construct
 relationships in future iterations of this project.*/
public enum Role {
    adult, // An identifier of an adult
    child, // An identifier of a child
    administrator; // An identifier of an administrator

    // GETTER
    public static Role getRole(String value){
        try {
            return Role.valueOf(value);
        }catch(Exception err){
            return null;
        }
    }
}
