package interface_adapter.station_info;

import interface_adapter.search.SearchState;

public class StationInfoState {
    //Purpose of class: Contains the information we need to have during the station info state (the state after successful user input is received)
    private String stationName;
    private String stationAmenities; //TODO: Tentatively, it is a String type

    private String stationParentLine;

    //TODO: If this "copy" constructor is unused, delete in the final project implementation
    public StationInfoState(StationInfoState copy) {
        stationName = copy.stationName;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public StationInfoState() {
    }

    public String getStateStationName() {
        return stationName;
    }
    public void setStateStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStateStationAmenities() {return stationAmenities;}

    //TODO: This is a MOCK change to the state, where the stationAmenitiesList is first converted to a string format
    // ... then, wrapped around HTML tags to format the content as a "paragraph"
    public void setStateStationAmenities(String stationAmenities) {
        this.stationAmenities = "<html>" + stationAmenities + "<html>";
    }

    public String getStateStationParentLine() {
        return stationParentLine;
    }
    public void setStateStationParentLine(String stationParentLine) {
        this.stationParentLine = stationParentLine;
    }

}
