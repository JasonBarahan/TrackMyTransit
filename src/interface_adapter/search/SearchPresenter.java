package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.station_general_info.StationGeneralInfoState;
import interface_adapter.station_general_info.StationGeneralInfoViewModel;
import use_case.station_general_info.StationGeneralInfoOutputBoundary;
import use_case.station_general_info.StationGeneralInfoOutputData;

public class SearchPresenter implements StationGeneralInfoOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final StationGeneralInfoViewModel stationInfoViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel, StationGeneralInfoViewModel stationInfoViewModel, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.stationInfoViewModel = stationInfoViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Purpose: prepareSuccessView() prepares a transition to the next panel, which occurs ONLY if the user types in the correct station names and presses the submit button
     * It does this by setting the view state appropriately.
     * @param response Consisting of the output data we wish to display
     */
    public void prepareSuccessView(StationGeneralInfoOutputData response){
        String retrieveStationName = response.getStationName();
        String retrieveStationAmenities = response.getStationAmenities();
        String retrieveParentLine = response.getStationParentLine();

        // Step 1: Resetting the station error value in the searchState to be null (in case any failed search attempts came before this "successful" attempt)
        SearchState searchState = searchViewModel.getState();
        searchState.setStateStationName(retrieveStationName);
      
        //  This line sets the state's station error attribute value to null.
        //  This happens ONLY IF the prepareSuccessView has been triggered
        //  This is because should there be an error message stored in the state, we want to CLEAR THAT to display the success view
        searchState.setStateStationError(null);        

        // Step 2: Setting values in the SearchPanelView

        // Setting the station name value and updating state with change
        StationGeneralInfoState stationAmenitiesInfoState = stationInfoViewModel.getState();
        stationAmenitiesInfoState.setStateStationName(retrieveStationName);

        // Setting the station amenities list and updating state with change
        stationAmenitiesInfoState.setStateStationAmenities(retrieveStationAmenities);

        // Setting the station amenities list and updating state with change
        stationAmenitiesInfoState.setStateStationParentLine(retrieveParentLine);

        stationInfoViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(stationInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Purpose: prepareFailView() prepares the information required to display a "failure pop up window", occuring when the user either has incorrect API key or incorrect input
     * It does this by setting the view state appropriately.
     * @param stationRetrievalError Consisting of the error message to display
     */
    public void prepareFailView(String stationRetrievalError){
        SearchState searchState = searchViewModel.getState();
        searchState.setStateStationError(stationRetrievalError);
        searchViewModel.firePropertyChanged();
    }
}
