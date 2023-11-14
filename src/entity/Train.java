package entity;
public class Train {
    // Vehicle ID should never change
    private final int vehicleID;
    private double longitude;
    private double latitude;

    private String routeDestination;
    private String routeName;

    public Train(int vehicleID, double latitude, double longitude, String routeName, String routeDestination) {
        this.vehicleID = vehicleID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.routeDestination = routeDestination;
        this.routeName = routeName;
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

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteDestination() {
        return routeDestination;
    }

    public void setRouteDestination(String routeDestination) {
        this.routeDestination = routeDestination;
    }
}
