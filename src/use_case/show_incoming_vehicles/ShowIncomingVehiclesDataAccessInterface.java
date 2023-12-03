package use_case.show_incoming_vehicles;

import entity.StationInterface;
import entity.Train;

import java.util.*;

//TODO: Review the changes made to getStation, setStation and getIncomingVehicles
// The changes were made in regard to their method parameters
public interface ShowIncomingVehiclesDataAccessInterface {

    StationInterface getStation(String inputStationName);

    void setStation(String stationName);

    List<Train> getIncomingVehicles(String inputStationName);

    void setIncomingVehiclesList(String stationName);

    boolean incomingVehiclesIsEmpty(String stationName);

    String getVehicleInfoRetrievalErrorMsg(String stationName);
}
