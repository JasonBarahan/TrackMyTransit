package interface_adapter.station_info;

import use_case.StationInfo.StationInfoInputBoundary;
import use_case.StationInfo.StationInfoInputData;

public class StationInfoController {
    final StationInfoInputBoundary stationInfoUseCaseInteractor;

    public StationInfoController(StationInfoInputBoundary stationInfoUseCaseInteractor) {
        this.stationInfoUseCaseInteractor = stationInfoUseCaseInteractor;
    }

    public void execute(String stationName) {
        StationInfoInputData stationInfoInputData = new StationInfoInputData(stationName);
        stationInfoUseCaseInteractor.execute(stationInfoInputData);
    }
}
