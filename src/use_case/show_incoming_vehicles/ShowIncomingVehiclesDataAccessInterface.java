package use_case.show_incoming_vehicles;

import entity.StationInterface;
import entity.Train;

import java.util.*;

public interface ShowIncomingVehiclesDataAccessInterface {
    StationInterface getStation(String inputStationName);

    void setStation(String stationName);

    List<Train> getIncomingVehicles(String inputStationName);

    void setIncomingVehiclesList(String stationName);

    boolean incomingVehiclesIsEmpty(String stationName);

    String getVehicleInfoRetrievalErrorMsg(String stationName);
}
