package interface_adapter.search;
public class SearchState {
    //Purpose of class: Contains the information we need to have during the search state
    private String stationName;
    private String stationError;

    //TODO: We will leave this "copy" constructor here for now (where we have one that "copies" state)
    // If this is still unused at the end of the project, delete.
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