package use_case.station_general_info;

public class StationGeneralInfoOutputData {
    private final String stationName;
    private final String stationParentLine;
    private final String stationAmenities;

    /**
     * Constructor for the station info use case output data
     * @param stationName
     * @param stationParentLine
     * @param stationAmenities
     */
    public StationGeneralInfoOutputData(String stationName, String stationParentLine, String stationAmenities) {
        this.stationName = stationName;
        this.stationParentLine = stationParentLine;
        this.stationAmenities = stationAmenities;
    }

    public String getStationName() {return stationName;}

    public String getStationParentLine() {return stationParentLine;}

    public String getStationAmenities() {return stationAmenities;}
}
