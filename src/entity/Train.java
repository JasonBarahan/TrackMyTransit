package entity;
public class Train {
    // Vehicle ID should never change
    private final int vehicleID;
    private double longitude;
    private double latitude;

    public Train(int vehicleID, double latitude, double longitude) {
        this.vehicleID = vehicleID;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getVehicleID() {
        return vehicleID;
    }
}
