package use_case.station_info;

import entity.Station;
import entity.Train;

import java.text.ParseException;
import java.util.*;

//TODO: Review the changes made to getStation, setStation and getIncomingVehicles
// The changes were made in regard to their method parameters
public interface StationInfoDataAccessInterface {
    String NONE = "[No Delay] ";
    String MINIMAL = "[Minimal Delay] ";
    String DELAY = "[Delay] ";
    String MAJOR = "[Major Delay] ";

    String SECONDS = " second(s)";
    String MINUTES = " minute(s)";
    String HOURS = " hour(s)";

    String delayTime(String scheduled, String computed) throws ParseException;
    double calculated(double time);
    boolean incomingVehiclesNotEmpty(String stationName);

    Station getStation(String inputStationName);

    void setStation(Station stationObj) throws ParseException;

    List<Train> getIncomingVehicles(String inputStationName) throws ParseException;
}
