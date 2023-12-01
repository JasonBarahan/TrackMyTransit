package use_case.station_info;

import entity.Station;
import entity.Train;

import java.util.*;

public interface StationInfoDataAccessInterface {
    boolean incomingVehiclesNotEmpty(String stationName);

    Station getStation(String inputStationName);

    List<Train> getIncomingVehicles(String inputStationName);
}
