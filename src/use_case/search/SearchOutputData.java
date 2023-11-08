package use_case.search;

import java.util.List;

public class SearchOutputData {
    private final String stationName;
    private final String stationParentLine;
    private final boolean searchFailed;
    //  Not sure if we want to implement search fail case (i.e.: station name doesn't exist) for search bar yet
    //  We will keep the instance for now and might go back to it if possible

    private final List<String> stationAmenities;
    //  After clicking "Find Info" button, the app directs us to Panel #2
    //  We still need to figure out Amenities output


    /**
     * Requirement: Fail case considers the user misspells(spells the wrong letter) the station name.
     * Incorrect capitalization is not considered in fail cases. In other words, we assume the user will not capitalize
     * letters incorrectly.
     * @param stationName
     * @param stationParentLine
     * @param searchFailed
     * @param stationAmenities
     */
    public SearchOutputData(String stationName, String stationParentLine, List<String> stationAmenities, boolean searchFailed) {
        this.stationName = stationName;
        this.stationParentLine = stationParentLine;
        this.searchFailed = searchFailed;
        this.stationAmenities = stationAmenities;
    }

    public String getStationName() {return stationName;}

    public String getStationParentLine() {return stationParentLine;}

    public List<String> getStationAmenities() {return stationAmenities;}
}
