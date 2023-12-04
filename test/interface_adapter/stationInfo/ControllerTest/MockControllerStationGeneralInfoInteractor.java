package interface_adapter.stationInfo.ControllerTest;
import use_case.station_general_info.StationGeneralInfoDataAccessInterface;
import use_case.station_general_info.StationGeneralInfoInputBoundary;
import use_case.station_general_info.StationGeneralInfoInputData;
import use_case.station_general_info.StationGeneralInfoOutputBoundary;


public class MockControllerStationGeneralInfoInteractor implements StationGeneralInfoInputBoundary {
    final StationGeneralInfoDataAccessInterface stationDataAccessObject;
    final StationGeneralInfoOutputBoundary stationPresenter;

    public MockControllerStationGeneralInfoInteractor(StationGeneralInfoDataAccessInterface stationDataAccessInterface,
                                                      StationGeneralInfoOutputBoundary searchOutputBoundary) {
        this.stationDataAccessObject = stationDataAccessInterface;
        this.stationPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(StationGeneralInfoInputData searchInputData) {
        System.out.println("If reached here, then execute successfully called");
    }

}
