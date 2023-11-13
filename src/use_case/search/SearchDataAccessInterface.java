package use_case.search;

import entity.Station;

import java.util.List;

public interface SearchDataAccessInterface {

    // Method stationExist() added to check whether the station whose name the user inputs ACTUALLY exists in the data object
    boolean stationExist(String identifier);

    // Getting Station object based on input station name for team use case
    Station getStation(String inputStationName);
    // used to be: Station getStation(); above
    String getStationParentLine(String inputStationName);

    List<String> getStationAmenities(String inputStationName);

}
