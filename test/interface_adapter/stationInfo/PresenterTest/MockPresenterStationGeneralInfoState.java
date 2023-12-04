package interface_adapter.stationInfo.PresenterTest;
import interface_adapter.station_general_info.StationGeneralInfoState;

public class MockPresenterStationGeneralInfoState extends StationGeneralInfoState {
    private String stationName;
    private String stationAmenities;
    private String incomingVehiclesError;

    private String stationParentLine;

    // Because of the previous copy constructor, the default constructor must be explicit.
    public MockPresenterStationGeneralInfoState() {
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
