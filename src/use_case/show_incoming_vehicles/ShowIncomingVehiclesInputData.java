package use_case.show_incoming_vehicles;

public class ShowIncomingVehiclesInputData {
    final private String stationName;

    public ShowIncomingVehiclesInputData(String stationName) {this.stationName = stationName;}

    String getStationName() {return stationName;}
}
