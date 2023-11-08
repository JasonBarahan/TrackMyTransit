package use_case.search;

import entity.Station;

public class SearchInteractor implements SearchInputBoundary {
    final SearchDataAccessInterface stationDataAccessObject;
    final SearchOutputBoundary stationPresenter;

    //Might add a Factory object later, here is the one used for SignupInteractor for reference:
    // final StationFactory stationFactory; (Not sure if we need to actually use this)

    public SearchInteractor(SearchDataAccessInterface stationDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary) {
        this.stationDataAccessObject = stationDataAccessInterface;
        this.stationPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        if (stationDataAccessObject.stationExist(searchInputData.getStationName()) == false) {
            // When station name doesn't exist, show error message
            stationPresenter.prepareFailView("Station doesn't exist, please enter a correct station name.");
        } else{
            // Creating a Station object using the station factory based on the name that the user input
            Station station = stationDataAccessObject.getStation(searchInputData.getStationName());
            // Do something with Output Data
            SearchOutputData searchOutputData = new SearchOutputData(station.getName(), station.getParentLine(), station.getAmenitiesList(), false); // Question: Do we need the last parameter?
            stationPresenter.prepareSuccessView(searchOutputData);
        }
    }
}
