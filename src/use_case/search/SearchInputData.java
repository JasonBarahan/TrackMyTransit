package use_case.search;
public class SearchInputData {
    final private String stationName;

    public SearchInputData(String stationName) {
        this.stationName = stationName;
    }

    String getStationName() {
        return stationName;
    }

}
