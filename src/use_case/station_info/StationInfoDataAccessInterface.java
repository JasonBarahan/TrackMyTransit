package use_case.station_info;

import entity.Station;
import entity.Train;

import java.util.*;

//TODO: Review the changes made to getStation, setStation and getIncomingVehicles
// The changes were made in regard to their method parameters
public interface StationInfoDataAccessInterface {
    boolean incomingVehiclesNotEmpty(String stationName);

    Station getStation(String inputStationName);

    void setStation(Station stationObj);

    List<Train> getIncomingVehicles(String inputStationName);
}
