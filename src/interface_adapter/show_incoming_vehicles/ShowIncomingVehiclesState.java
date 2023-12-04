package interface_adapter.show_incoming_vehicles;

import java.util.List;

public class ShowIncomingVehiclesState {
    private String stationName;
    private List<List<String>> incomingVehiclesInfo;

    //TODO: If this "copy" constructor is unused, delete in the final project implementation
    public ShowIncomingVehiclesState(ShowIncomingVehiclesState copy) {
        stationName = copy.stationName;
        incomingVehiclesInfo = copy.incomingVehiclesInfo;
    }

    public ShowIncomingVehiclesState() {}

    public String getStateStationName(){return stationName;}

    public void setStateStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<List<String>> getStateIncomingVehiclesList() {return incomingVehiclesInfo;}

    public void setStateIncomingVehiclesList(List<List<String>> incomingVehiclesInfoList){
        this.incomingVehiclesInfo = incomingVehiclesInfoList;
    }
}
