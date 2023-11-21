package use_case.station_info;

import entity.Station;

import java.util.List;

public interface StationInfoDataAccessInterface {
    Station getStation(String inputStationName); // user input station name for retrieving information

    // TODO: unused in search, not sure if needed right now either
    String getStationParentLine(String inputStationName);
    List<String> getStationAmenities(String inputStationName);
}
