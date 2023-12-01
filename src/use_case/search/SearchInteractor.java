package use_case.search;

import entity.Station;
import java.util.List;

public class SearchInteractor implements SearchInputBoundary {
    final SearchDataAccessInterface stationDataAccessObject;
    final SearchOutputBoundary stationPresenter;

    // TODO (Note for learning purposes, delete later): Unlike week5ca, we likely do not need to insert a Factory here since we are not constructing new data based on user input
    public SearchInteractor(SearchDataAccessInterface stationDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary) {
        this.stationDataAccessObject = stationDataAccessInterface;
        this.stationPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        boolean stationExists = stationDataAccessObject.stationExist(searchInputData.getStationName());
        if (stationExists) {
            boolean validAmenitiesAPICall = stationDataAccessObject.validAmenitiesAPICall(searchInputData.getStationName());
            if (validAmenitiesAPICall){
                // Creating a Station object using the station factory based on the name that the user input
                Station station = stationDataAccessObject.getStation(searchInputData.getStationName());

                // Packaging key details from the above Station object into a SearchOutputData object
                List<String> amenitiesList = station.getAmenitiesList();
                String amenitiesListAsString = String.join(", ", amenitiesList);
                SearchOutputData searchOutputData = new SearchOutputData(station.getName(), station.getParentLine(), amenitiesListAsString);

                // return the output data to the user
                stationPresenter.prepareSuccessView(searchOutputData);
            } else{
                stationPresenter.prepareFailView("Invalid API Call");
            }
        } else { 
            // If this branch is reached, the station the user entered does not exist in the text file. So, display error message.
            stationPresenter.prepareFailView("ERROR: Station with the name [" +  searchInputData.getStationName() + "] does not exist");
        }
    }
}
