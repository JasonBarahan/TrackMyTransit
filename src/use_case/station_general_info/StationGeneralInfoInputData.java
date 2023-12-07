package use_case.station_general_info;
public class StationGeneralInfoInputData {
    /**
     * Purpose: Contains the "blueprint" for the input data used by the station info use case
     * */
    final private String stationName;

    public StationGeneralInfoInputData(String stationName) {
        this.stationName = stationName;
    }

    String getStationName() {
        return stationName;
    }

}
