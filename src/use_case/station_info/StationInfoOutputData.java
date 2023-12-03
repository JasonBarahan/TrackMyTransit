package use_case.station_info;

import java.util.List;

public class StationInfoOutputData {
    private final String stationName;
    private final List<List<String>> stationIncomingVehiclesInfo;
    // the nested List<String> is a list including line name, vehicle direction,
    // scheduled departure time, actual departure time and Delay

    public StationInfoOutputData(String stationName,
                                 List<List<String>> stationIncomingVehiclesInfo) {
        this.stationName = stationName;
        this.stationIncomingVehiclesInfo = stationIncomingVehiclesInfo;
    }

    public String getStationName() {return stationName;}

    public List<List<String>> getStationIncomingVehiclesInfo() {return stationIncomingVehiclesInfo;}



}
