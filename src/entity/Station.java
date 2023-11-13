package entity;

import java.util.List;

public class Station implements StationInterface{
    private final String name;
    private final String stationId;
    private final String parentLine;
    private final float latitude;
    private final float longitude;
    private final List<String> amenitiesList;
    private final List<Vehicle> incomingVehicles;

    /**
     * Requirement: stationId and name exist.
     * @param name
     * @param stationId
     * @param parentLine
     * @param latitude
     * @param longitude
     * @param amenitiesList
     * @param incomingVehicles
     */

    Station(String name, String stationId, String parentLine, float latitude, float longitude, List<String> amenitiesList, List<Vehicle> incomingVehicles) {
        this.stationId = stationId;
        this.name = name;
        this.parentLine = parentLine;
        this.longitude = longitude;
        this.latitude = latitude;
        this.amenitiesList = amenitiesList;
        this.incomingVehicles = incomingVehicles;

    }
    @Override
    public String getId() {
        return stationId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParentLine() {
        return parentLine;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public List<String> getAmenitiesList() {
        return amenitiesList;
    }

    @Override
    public List<Vehicle> getIncomingVehicles() {
        return incomingVehicles;
    }
}