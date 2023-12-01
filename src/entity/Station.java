package entity;

import java.util.List;

public class Station implements StationInterface{
    private final String name;
    private final String stationId;
    private final String parentLine;
    private final float latitude;
    private final float longitude;
    private List<String> amenitiesList; //TODO: Due to this variable being assigned a valid value AFTER initial text file is read, we need to make this non-final. But this violates CAE?

    private List<Train> incomingVehicles;
    // used to be:    private final List<Vehicle> incomingVehicles;
    // will change to the original one after implementing Vehicle class.
    //TODO: Due to this variable being assigned a valid value AFTER initial text file is read,
    // we need to make this non-final. But this violates CAE?

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

    Station(String name, String stationId, String parentLine, float latitude, float longitude, List<String> amenitiesList, List<Train> incomingVehicles) {
        //String name, String stationId, String parentLine, float latitude, float longitude, List<String> amenitiesList, List<Vehicle> incomingVehicles
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

    public void setAmenitiesList(List<String> stationAmenitiesList) {
        this.amenitiesList = stationAmenitiesList;
    }

    @Override
    public List<Train> getIncomingVehicles() {
        return incomingVehicles;
    }
//    public List<Vehicle> getIncomingVehicles() {
//        return incomingVehicles;
//    }
    public void setIncomingVehiclesList(List<Train> incomingVehiclesList) {
        this.incomingVehicles = incomingVehiclesList;
    }


}