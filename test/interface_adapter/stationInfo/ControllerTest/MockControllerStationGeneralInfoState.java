package interface_adapter.stationInfo.ControllerTest;

public class MockControllerStationGeneralInfoState {
    private String stationName = "Union Station";
    private String stationAmenities = "Public Washrooms, Wifi";
    private String incomingVehiclesError;

    private String stationParentLine = "Union Line";


    // Because of the previous copy constructor, the default constructor must be explicit.
    public MockControllerStationGeneralInfoState() {
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
