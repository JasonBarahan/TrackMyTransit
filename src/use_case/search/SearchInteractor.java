package use_case.search;

import entity.Station;
import java.util.List;

public class SearchInteractor implements SearchInputBoundary {
    final SearchDataAccessInterface stationDataAccessObject;
    final SearchOutputBoundary stationPresenter;

    public SearchInteractor(SearchDataAccessInterface stationDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary) {
        this.stationDataAccessObject = stationDataAccessInterface;
        this.stationPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        boolean stationExists = stationDataAccessObject.stationExist(searchInputData.getStationName()); //Returns true if the station has a name in the revisedStopData.txt. Returns False otherwise
        if (stationExists) {
            String amenitiesAPICallMetadataMessage = stationDataAccessObject.amenitiesAPICallMetadataMessage(searchInputData.getStationName()); // Retrieving the API metadata code associated with retrieving station amenities
            String apiCallMetadataSuccessMessage = stationDataAccessObject.getAPIMetadataSuccessMessage(); // Retrieving the success message for this Train API. For the GO Train Api, this is: "OK".
            if (amenitiesAPICallMetadataMessage.equals(apiCallMetadataSuccessMessage)){ // If true, then we know we executed a successful API call. Then we can go ahead and fetch the amenities list.
                // First, fetching the constructed, but incomplete station object. This object is created based on reading from revisedStopData.txt
                Station incompleteStation = stationDataAccessObject.getStation(searchInputData.getStationName());

                stationDataAccessObject.setStation(incompleteStation); // Then, populate the station's currently empty amenitiesList and incomingVehicles attributes based on API calls

                Station station = stationDataAccessObject.getStation(searchInputData.getStationName()); // Now, re-retrieve the fully complete Station object

                // Packaging key details from the above Station object into a SearchOutputData object
                List<String> amenitiesList = station.getAmenitiesList();
                String amenitiesListAsString = String.join(", ", amenitiesList);
                SearchOutputData searchOutputData = new SearchOutputData(station.getName(), station.getParentLine(), amenitiesListAsString);

                // return the output data to the user
                stationPresenter.prepareSuccessView(searchOutputData);
            } else{ // If this branch is reached, then we know that despite the station existing in the text file, we have failed to make an API call. Thus, the presenter will show a pop-up window displaying the invalid api call message
                stationPresenter.prepareFailView("Invalid API Call. Message returned: " + amenitiesAPICallMetadataMessage);
            }
        } else { 
            // If this branch is reached, the station the user entered does not exist in the text file. So, display error message saying that the station does not exist. 
            stationPresenter.prepareFailView("ERROR: Station with the name [" +  searchInputData.getStationName() + "] does not exist");
        }
    }
}
