package entity;

import java.util.List;

public class Station implements StationInterface{
    private final String name;
    private final String stationId;
    private final String parentLine;
    private final float latitude;
    private final float longitude;
    private List<String> amenitiesList;
    private List<Vehicle> incomingVehicles;

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

    /**
     * This sets the amenities list through an API call.
     *
     * @param amenitiesList: a list of amenities obtained through an API call
     */
    public void setAmenitiesList(List<String> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    /**
     * This sets the incoming vehicles for a particular station.
     * This is needed as a user may refresh the list of incoming vehicles manually, or through a new query.
     *
     * @param incomingVehicles: list of incoming vehicles as per API call
     */
    public void setIncomingVehicles(List<Vehicle> incomingVehicles) {
        this.incomingVehicles = incomingVehicles;
    }
}