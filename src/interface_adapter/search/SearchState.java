package interface_adapter.search;
public class SearchState {
    //Purpose of class: Contains the information we need to have during the search state
    private String stationName;
    private String stationError;

    //TODO: Do we need to set up our constructors like this (where we have one that "copies" state)
    public SearchState(SearchState copy) {
        stationName = copy.stationName;
        stationError = copy.stationError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SearchState() {
    }

    public String getStateStationName() {
        return stationName;
    }
    public void setStateStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStateStationError() {
        return stationError;
    }
    public void setStateStationError(String stationError) {
        this.stationError = stationError;
    }


}
