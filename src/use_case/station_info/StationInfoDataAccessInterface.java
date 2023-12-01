package use_case.station_info;

import entity.Station;
import entity.Train;

import java.util.*;

public interface StationInfoDataAccessInterface {
    boolean incomingVehiclesNotEmpty(String stationName);

    Station getStation(String inputStationName);

    List<Train> getIncomingVehicles(String inputStationName);

    String getStationId(String inputStationName);
    // We might not need this since we could get station id from getStation().
}
