package cscie97.smartcity.model;

public class Location {

    private float latitude;
    private float longitude;
    private float radius;

    public Location(float latitude, float longitude, float radius){
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public boolean overlapsWith(Location L){
        float[] x1Lat = this.getBoundary(this.latitude);
        float[] x1Long = this.getBoundary(this.longitude);

        float[] x2Lat = L.getBoundary(L.latitude);
        float[] x2Long = L.getBoundary(L.longitude);

        if((x1Lat[0] < x2Lat[0] && x2Lat[0] < x1Lat[1]) || (x1Lat[0] < x2Lat[1] && x2Lat[1] < x1Lat[1])){
            if((x1Long[0] < x2Long[0] && x2Long[0] < x1Long[1]) || (x1Long[0] < x2Long[1] && x2Long[1] < x1Long[1])){
                return true;
            }
        }
        return false;
    }

    private float[] getBoundary(float direction){
        float[] boundary = new float[2];
        float R = Math.abs(this.radius);
        boundary[0] = direction - (R/6371);
        boundary[1] = direction + (R/6371);
        return boundary;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
