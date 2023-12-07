package interface_adapter.station_general_info;

import use_case.show_incoming_vehicles.ShowIncomingVehiclesInputBoundary;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesInputData;

import java.text.ParseException;

public class StationGeneralInfoController {
    final ShowIncomingVehiclesInputBoundary stationInfoUseCaseInteractor;

    public StationGeneralInfoController(ShowIncomingVehiclesInputBoundary stationInfoUseCaseInteractor) {
        this.stationInfoUseCaseInteractor = stationInfoUseCaseInteractor;
    }

    /**
     * Purpose: This execute() methods calls the station general info use case interactor to execute the search use case
     * @param stationName
     * No Return
     * */
    public void execute(String stationName) throws ParseException {
        ShowIncomingVehiclesInputData stationInfoInputData = new ShowIncomingVehiclesInputData(stationName);
        stationInfoUseCaseInteractor.execute(stationInfoInputData);
    }
}