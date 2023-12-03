package use_case.search_show_amenities;
public class SearchShowAmenitiesInputData {
    final private String stationName;

    public SearchShowAmenitiesInputData(String stationName) {
        this.stationName = stationName;
    }

    String getStationName() {
        return stationName;
    }

}
