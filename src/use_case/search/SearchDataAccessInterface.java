package use_case.search;

import entity.Station;

import java.util.List;

public interface SearchDataAccessInterface {

    // new method added to check whether the station whose name the user inputs ACTUALLY exists in the data object
    boolean stationExist(String identifier);

    String getStationID (String inputStationName);
    Station getStation(String inputStationName); // attempting to only get proper station name for base implementation of team use case
    // used to be: Station getStation(); above
    String getStationParentLine(String inputStationName);

    List<String> getStationAmenities(String inputStationName);

    void setStationAmenities(Station stationObj, List<String> stationObjAmenities);

}
