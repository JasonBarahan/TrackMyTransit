package use_case.StationInfo;

import java.util.List;

public class StationInfoOutputData {
    private final String stationName;
    private final List<List<String>> stationIncomingVehiclesInfo;
    // the nested List<String> is a list including parent line code, parent line name, vehicle type,
    // vehicle display name, scheduled departure time, actual departure time, trip number and delay

    public StationInfoOutputData(String stationName,
                                 List<List<String>> stationIncomingVehiclesInfo) {
        this.stationName = stationName;
        this.stationIncomingVehiclesInfo = stationIncomingVehiclesInfo;
    }

    public String getStationName() {return stationName;}

    public List<List<String>> getStationIncomingVehiclesInfo() {return stationIncomingVehiclesInfo;}



}
