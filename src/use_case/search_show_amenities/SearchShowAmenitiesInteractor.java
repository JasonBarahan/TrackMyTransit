package use_case.search_show_amenities;

import java.util.List;

public class SearchShowAmenitiesInteractor implements SearchShowAmenitiesInputBoundary {
    final SearchShowAmenitiesDataAccessInterface stationDataAccessObject;
    final SearchShowAmenitiesOutputBoundary stationPresenter;

    public SearchShowAmenitiesInteractor(SearchShowAmenitiesDataAccessInterface stationDataAccessInterface,
                                         SearchShowAmenitiesOutputBoundary searchOutputBoundary) {
        this.stationDataAccessObject = stationDataAccessInterface;
        this.stationPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchShowAmenitiesInputData searchInputData) {
        String stationName = searchInputData.getStationName();
        boolean stationExists = stationDataAccessObject.stationExist(stationName); //Returns true if the station has a name in the revisedStopData.txt. Returns False otherwise
        if (stationExists) {
            String amenitiesAPICallMetadataMessage = stationDataAccessObject.amenitiesAPICallMetadataMessage(stationName); // Retrieving the API metadata code associated with retrieving station amenities
            String apiCallMetadataSuccessMessage = stationDataAccessObject.getAPIMetadataSuccessMessage(); // Retrieving the success message for this Train API. For the GO Train Api, this is: "OK".

            if (amenitiesAPICallMetadataMessage.equals(apiCallMetadataSuccessMessage)){ // If true, then we know we executed a successful API call. Then we can go ahead and fetch the amenities list.
                // First, fetching the constructed, but incomplete station object. This object is created based on reading from revisedStopData.txt
                stationDataAccessObject.setStation(stationName); // Then, populate the station's currently empty amenitiesList and incomingVehicles attributes based on API calls
                String stationParentLine = stationDataAccessObject.getStationParentLine(stationName);

                // Packaging key details from the above Station object into a SearchShowAmenitiesOutputData object
                List<String> amenitiesList = stationDataAccessObject.getStationAmenities(stationName);
                String amenitiesListAsString = String.join(", ", amenitiesList);
                SearchShowAmenitiesOutputData searchOutputData = new SearchShowAmenitiesOutputData(stationName, stationParentLine, amenitiesListAsString);

                // return the output data to the user
                stationPresenter.prepareSuccessView(searchOutputData);

            } else{ // If this branch is reached, then we know that despite the station existing in the text file, we have failed to make an API call. Thus, the presenter will show a pop-up window displaying the invalid api call message
                stationPresenter.prepareFailView("Invalid API Call. Message returned: " + amenitiesAPICallMetadataMessage);
            }
        } else { 
            // If this branch is reached, the station the user entered does not exist in the text file. So, display error message saying that the station does not exist. 
            stationPresenter.prepareFailView("ERROR: Station with the name [" +  stationName + "] does not exist");
        }
    }
}
