package use_case.search;

import entity.Station;

public interface SearchDataAccessInterface {

    // new method added to check whether the station whose name the user inputs ACTUALLY exists in the data object
    boolean stationExist(String identifier);
    String getStationName(String inputStationName); // attempting to only get proper station name for base immplementation of team use case
    // used to be: Station getStation(); above

}
