package use_case.show_incoming_vehicles;

import java.util.List;

public class ShowIncomingVehiclesOutputData {
    private final String stationName;
    private final List<List<String>> stationIncomingVehiclesInfo;
    // the nested List<String> is a list including line name, vehicle direction,
    // scheduled departure time, actual departure time and Delay

    public ShowIncomingVehiclesOutputData(String stationName,
                                          List<List<String>> stationIncomingVehiclesInfo) {
        this.stationName = stationName;
        this.stationIncomingVehiclesInfo = stationIncomingVehiclesInfo;
    }

    public String getStationName() {return stationName;}

    public List<List<String>> getStationIncomingVehiclesInfo() {return stationIncomingVehiclesInfo;}



}
