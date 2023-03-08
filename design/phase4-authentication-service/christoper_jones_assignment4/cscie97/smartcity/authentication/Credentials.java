package cscie97.smartcity.authentication;

import java.util.HashMap;

/*
The Credentials class stores the password and biometric information of a given User.
 */
public class Credentials {

    private Password password;
    private HashMap<Integer,Integer> biometrics;

    public Credentials(){
        this.password = null;
        this.biometrics = new HashMap<Integer,Integer>();
    }

    // Adds a password pair to the Credential
    public void add(Password password) {
        if(this.password == null){
            this.password = password;
        }else{
            throw new Error("Password is already set...");
        }
    }

    // Adds a biometric pair to the Credential
    public void add(Biometric biometric) {
        if(!biometrics.containsKey(biometric.getBiometricIndex())){
            biometrics.put(biometric.getBiometricIndex(), biometric.getSecret());
        }else{
            throw new Error("BiometricIndex is already set...");
        }
    }

    public int getPassword() {
        return this.password.getSecret();
    }

    public int getBiometric(String type) {
        return this.biometrics.get(type.hashCode());
    }
}
