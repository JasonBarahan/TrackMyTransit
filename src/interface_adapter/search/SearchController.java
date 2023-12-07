package interface_adapter.search;
import use_case.station_general_info.StationGeneralInfoInputBoundary;
import use_case.station_general_info.StationGeneralInfoInputData;

import java.text.ParseException;

public class SearchController {
    final StationGeneralInfoInputBoundary userSearchUseCaseInteractor;
    public SearchController(StationGeneralInfoInputBoundary userSearchUseCaseInteractor) {
        this.userSearchUseCaseInteractor = userSearchUseCaseInteractor;
    }

    /**
     * Purpose: This execute() methods calls the search use case interactor to execute the search use case
     * @param stationName
     * No Return
     * */
    public void execute(String stationName) throws ParseException {
        StationGeneralInfoInputData searchInputData = new StationGeneralInfoInputData(stationName);
        userSearchUseCaseInteractor.execute(searchInputData);
    }
}
