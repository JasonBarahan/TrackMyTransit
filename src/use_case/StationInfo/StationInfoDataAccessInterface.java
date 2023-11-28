package use_case.StationInfo;

import entity.Station;
import entity.Train;

import java.util.*;

public interface StationInfoDataAccessInterface {

    Station getStation(String inputStationName);

    List<Train> getIncomingVehicles(String inputStationName);

    String getStationId(String inputStationName);
    // We might not need this since we could get station id from getStation().
}
