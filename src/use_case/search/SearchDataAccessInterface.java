package use_case.search;

import entity.Station;
import entity.StationInterface;

import java.util.List;

public interface SearchDataAccessInterface {
    boolean stationExist(String identifier);

    String getAPIMetadataSuccessMessage();
    String amenitiesAPICallMetadataMessage(String stationName);

    String getStationID (String inputStationName);
    StationInterface getStation(String inputStationName);

    String getStationName (Station stationObj);
    String getStationParentLine(String inputStationName);

    List<String> getStationAmenities(String inputStationName);

    void setStation(String stationName);
    void setStationAmenities(String stationName);

}
