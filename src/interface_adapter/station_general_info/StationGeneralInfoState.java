package interface_adapter.station_general_info;

public class StationGeneralInfoState {
    //Purpose of class: Contains the information we need to have during the station info state (the state after successful user input is received)
    private String stationName;
    private String stationAmenities;
    private String incomingVehiclesError;

    private String stationParentLine;

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

    //NOTE: This method wraps the stationAmenities attribute around <html> tags to format the text as a "paragraph" on the JPanel
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
