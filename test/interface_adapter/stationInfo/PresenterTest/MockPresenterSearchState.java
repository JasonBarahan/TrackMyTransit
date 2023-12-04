package interface_adapter.stationInfo.PresenterTest;

import interface_adapter.search.SearchState;

public class MockPresenterSearchState extends SearchState {
    private String stationName = "Union Station";
    private String stationError;

    // Because of the previous copy constructor, the default constructor must be explicit.
    public MockPresenterSearchState() {
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
