package use_case.station_general_info;
public class StationGeneralInfoInputData {
    final private String stationName;

    public StationGeneralInfoInputData(String stationName) {
        this.stationName = stationName;
    }

    String getStationName() {
        return stationName;
    }

}
