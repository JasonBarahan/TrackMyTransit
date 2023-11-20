package use_case.search;

import java.util.List;

public class SearchOutputData {
    private final String stationName;
    private final String stationParentLine;
    private final String stationAmenities; //TODO: TEMP CHANGE TO STRING TYPE
    //  After clicking "Find Info" button, the app directs us to Panel #2
    //  We still need to figure out Amenities output

    /**
     * TODO: Requirement: TBD
     * @param stationName
     * @param stationParentLine
     * @param stationAmenities
     */
    public SearchOutputData(String stationName, String stationParentLine, String stationAmenities) {
        this.stationName = stationName;
        this.stationParentLine = stationParentLine;
        this.stationAmenities = stationAmenities;
    }

    public String getStationName() {return stationName;}

    public String getStationParentLine() {return stationParentLine;}

    public String getStationAmenities() {return stationAmenities;}
}
