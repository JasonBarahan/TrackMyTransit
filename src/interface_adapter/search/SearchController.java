package interface_adapter.search;
import use_case.station_general_info.StationGeneralInfoInputBoundary;
import use_case.station_general_info.StationGeneralInfoInputData;

public class SearchController {
    final StationGeneralInfoInputBoundary userSearchUseCaseInteractor;
    public SearchController(StationGeneralInfoInputBoundary userSearchUseCaseInteractor) {
        this.userSearchUseCaseInteractor = userSearchUseCaseInteractor;
    }

    public void execute(String stationName) {
        StationGeneralInfoInputData searchInputData = new StationGeneralInfoInputData(stationName);
        userSearchUseCaseInteractor.execute(searchInputData);
    }
}
