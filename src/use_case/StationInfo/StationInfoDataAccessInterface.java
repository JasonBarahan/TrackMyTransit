package use_case.StationInfo;

import entity.Station;

public interface StationInfoDataAccessInterface {

    Station getStation(String inputStationName);

    String getStationId(String inputStationName);
    // We might not need this since we could get station id from getStation().
}
