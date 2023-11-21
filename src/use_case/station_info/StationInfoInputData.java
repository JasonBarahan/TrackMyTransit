package use_case.station_info;

public class StationInfoInputData {
    final private String stationName;
    final private String stationLine;
    final private String stationLat;
    final private String stationLon;


    public StationInfoInputData(String stationName, String stationLine, String stationLat, String stationLon) {
        this.stationName = stationName;
        this.stationLine = stationLine;
        this.stationLat = stationLat;
        this.stationLon = stationLon;
    }

    String getStationName() {
        return stationName;
    }

    // seems better to put the Line, ID may be useless to user?
    String getStationLine() {
        return stationLine;
    }

    // unknown if we want to implement this pinpoint location could be unused until the map feature ?
    String getStationLat() {
        return stationLine;
    }

    String getStationLon() {
        return stationLine;
    }
}
