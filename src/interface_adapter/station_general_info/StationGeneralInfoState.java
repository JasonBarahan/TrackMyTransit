package interface_adapter.station_general_info;

public class StationGeneralInfoState {
    //Purpose of class: Contains the information we need to have during the station info state (the state after successful user input is received)
    private String stationName;
    private String stationAmenities; //TODO: Tentatively, it is a String type
    private String incomingVehiclesError;

    private String stationParentLine;

    //TODO: If this "copy" constructor is unused, delete in the final project implementation
    public StationGeneralInfoState(StationGeneralInfoState copy) {
        stationName = copy.stationName;
        stationAmenities = copy.stationAmenities;
        incomingVehiclesError = copy.incomingVehiclesError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public StationGeneralInfoState() {
    }

    public String getStateStationName() {
        return stationName;
    }
    public void setStateStationName(String stationName) {
        this.stationName = stationName;
    }
    public void setIncomingVehiclesError(String incomingVehiclesError) {this.incomingVehiclesError = incomingVehiclesError;}
    public String getIncomingVehiclesError() {return incomingVehiclesError;}

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
