package interface_adapter.station_general_info;

import use_case.station_general_info.ShowIncomingVehiclesInputBoundary;
import use_case.station_general_info.ShowIncomingVehiclesInputData;

public class StationAmenitiesInfoController {
    final ShowIncomingVehiclesInputBoundary stationInfoUseCaseInteractor;

    public StationAmenitiesInfoController(ShowIncomingVehiclesInputBoundary stationInfoUseCaseInteractor) {
        this.stationInfoUseCaseInteractor = stationInfoUseCaseInteractor;
    }

    public void execute(String stationName) {
        ShowIncomingVehiclesInputData stationInfoInputData = new ShowIncomingVehiclesInputData(stationName);
        stationInfoUseCaseInteractor.execute(stationInfoInputData);
    }
}
