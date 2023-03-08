package cscie97.smartcity.model;

import java.net.URI;

public class InformationKiosk extends Device{

    private URI image;

    public InformationKiosk(String deviceID, Location location, boolean enabled, URI image) {
        super(deviceID, location, enabled);
        this.image = image;
    }

    public URI getImage(){ return this.image; }

    public void setImage(URI image) {
        this.image= image;
    }
}
