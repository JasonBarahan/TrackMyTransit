package use_case.search;

import entity.Station;

import java.util.List;

public interface SearchDataAccessInterface {
    boolean stationExist(String identifier);

    String getAPIMetadataSuccessMessage();
    String amenitiesAPICallMetadataMessage(String stationName);

    String getStationID (String inputStationName);
    Station getStation(String inputStationName);

    String getStationName (Station stationObj);
    String getStationParentLine(String inputStationName);

    List<String> getStationAmenities(String inputStationName);

    void setStation(String stationName);
    void setStationAmenities(Station stationObj);

}
