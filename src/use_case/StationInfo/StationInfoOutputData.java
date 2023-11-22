package use_case.StationInfo;

import java.util.List;

public class StationInfoOutputData {
    private final String stationName;
    private final List<List<String>> stationIncomingVehiclesInfo;
    // the nested List<String> is a list including parent line code, parent line name, vehicle type,
    // vehicle display name, scheduled departure time, actual departure time and vehicle number
    private final String vehicleDelay;
    // We can calculate the delay from scheduled time and actual time based on a certain vehicle number

    public StationInfoOutputData(String stationName,
                                 List<List<String>> stationIncomingVehiclesInfo,
                                 String vehicleDelay) {
        this.stationName = stationName;
        this.stationIncomingVehiclesInfo = stationIncomingVehiclesInfo;
        this.vehicleDelay = vehicleDelay;
    }

    public String getStationName() {return stationName;}

    public List<List<String>> getStationIncomingVehiclesInfo() {return stationIncomingVehiclesInfo;}

    public String getVehicleDelay(String vehicleNumber) {return vehicleDelay;}
    //

}
