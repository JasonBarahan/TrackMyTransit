package interface_adapter.station_info;

public class StationInfoState {
    //Purpose of class: Contains the information we need to have during the station info state (the state after successful user input is received)
    private String stationName;
    private String stationAmenities; //TODO: Tentatively, it is a String type
    private String incomingVehiclesError;

    //TODO: If this "copy" constructor is unused, delete in the final project implementation
    public StationInfoState(StationInfoState copy) {
        stationName = copy.stationName;
        stationAmenities = copy.stationAmenities;
        incomingVehiclesError = copy.incomingVehiclesError;
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
    public void setIncomingVehiclesError(String incomingVehiclesError) {this.incomingVehiclesError = incomingVehiclesError;}
    public String getIncomingVehiclesError() {return incomingVehiclesError;}

    //TODO: This is a MOCK change to the state, where the stationAmenitiesList is converted to a string format
    // Ideally, we want to seperate and print each entry
    public String getStateStationAmenities() {return stationAmenities;}
    public void setStateStationAmenities(String stationAmenities) {
        this.stationAmenities = stationAmenities;
    }

}
