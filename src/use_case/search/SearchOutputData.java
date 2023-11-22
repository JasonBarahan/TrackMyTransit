package use_case.search;

import java.util.List;

public class SearchOutputData {
    private final String stationName;
    private final String stationParentLine;
    private final List<String> stationAmenities;
    //  After clicking "Find Info" button, the app directs us to Panel #2
    //  We still need to figure out Amenities output

    /**
     * Requirement: Fail case considers the user misspells(spells the wrong letter) the station name.
     * Incorrect capitalization is not considered in fail cases. In other words, we assume the user will not capitalize
     * letters incorrectly.
     * @param stationName
     * @param stationParentLine
     * @param stationAmenities
     */
    public SearchOutputData(String stationName, String stationParentLine, List<String> stationAmenities) {
        this.stationName = stationName;
        this.stationParentLine = stationParentLine;
        this.stationAmenities = stationAmenities;
    }

    public String getStationName() {return stationName;}

    public String getStationParentLine() {return stationParentLine;}

    public List<String> getStationAmenities() {return stationAmenities;}
}
