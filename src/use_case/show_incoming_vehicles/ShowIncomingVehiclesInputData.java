package use_case.station_general_info;

public class ShowIncomingVehiclesInputData {
    final private String stationName;

    public ShowIncomingVehiclesInputData(String stationName) {this.stationName = stationName;}

    String getStationName() {return stationName;}
}
