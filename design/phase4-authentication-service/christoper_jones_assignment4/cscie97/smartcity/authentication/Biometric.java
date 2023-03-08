package cscie97.smartcity.authentication;

/*
A biometric object represents a physical credential related to an individual user. These objects can represent voiceprints, faceprints, etc.
 */
public class Biometric {

    private int username, biometricIndex, secret;

    public Biometric(String username, String secret) {
        this.username = username.hashCode();
        this.biometricIndex = secret.split(":")[0].hashCode();
        this.secret = secret.split(":")[1].hashCode();
    }

    // Returns the biometric type
    public int getBiometricIndex() {
        return this.biometricIndex;
    }

    // Returns the print of the biometric
    public int getSecret() {
        return this.secret;
    }
}
