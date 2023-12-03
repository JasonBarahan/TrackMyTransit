package interface_adapter.station_info;

import use_case.station_info.StationInfoInputBoundary;
import use_case.station_info.StationInfoInputData;

import java.text.ParseException;

public class StationInfoController {
    final StationInfoInputBoundary stationInfoUseCaseInteractor;

    public StationInfoController(StationInfoInputBoundary stationInfoUseCaseInteractor) {
        this.stationInfoUseCaseInteractor = stationInfoUseCaseInteractor;
    }

    public void execute(String stationName) throws ParseException {
        StationInfoInputData stationInfoInputData = new StationInfoInputData(stationName);
        stationInfoUseCaseInteractor.execute(stationInfoInputData);
    }
}
