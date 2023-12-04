package interface_adapter.stationInfo.ControllerTest;

public class MockControllerSearchState {
    //Purpose of class: Contains the information we need to have during the search state
    private String stationName = "Union Station";
    private String stationError;

    // Because of the previous copy constructor, the default constructor must be explicit.
    public MockControllerSearchState() {
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
