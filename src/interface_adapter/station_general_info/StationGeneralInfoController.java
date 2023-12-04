package interface_adapter.station_general_info;

import use_case.show_incoming_vehicles.ShowIncomingVehiclesInputBoundary;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesInputData;

public class StationGeneralInfoController {
    final ShowIncomingVehiclesInputBoundary stationInfoUseCaseInteractor;

    public StationGeneralInfoController(ShowIncomingVehiclesInputBoundary stationInfoUseCaseInteractor) {
        this.stationInfoUseCaseInteractor = stationInfoUseCaseInteractor;
    }

    public void execute(String stationName) {
        ShowIncomingVehiclesInputData stationInfoInputData = new ShowIncomingVehiclesInputData(stationName);
        stationInfoUseCaseInteractor.execute(stationInfoInputData);
    }
}
