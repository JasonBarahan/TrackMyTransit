package use_case.show_incoming_vehicles;

import entity.StationInterface;
import entity.Train;

import java.text.ParseException;
import java.util.*;

public interface ShowIncomingVehiclesDataAccessInterface {
    String NONE = "[No Delay] ";
    String MINIMAL = "[Minimal Delay] ";
    String DELAY = "[Delay] ";
    String MAJOR = "[Major Delay] ";

    String SECONDS = " second(s)";
    String MINUTES = " minute(s)";
    String HOURS = " hour(s)";

    String delayTime(String scheduled, String computed) throws ParseException;
    double calculated(double time);
    StationInterface getStation(String inputStationName);

    void setStation(String stationName) throws ParseException;

    List<Train> getIncomingVehicles(String inputStationName);

    void setIncomingVehiclesList(String stationName) throws ParseException;

    boolean incomingVehiclesIsEmpty(String stationName);

    String getVehicleInfoRetrievalErrorMsg(String stationName);
}
