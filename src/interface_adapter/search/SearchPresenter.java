package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.station_info.StationInfoState;
import interface_adapter.station_info.StationInfoViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final StationInfoViewModel stationInfoViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel, StationInfoViewModel stationInfoViewModel, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.stationInfoViewModel = stationInfoViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(SearchOutputData response){
        String retrieveStationName = response.getStationName();
        // NOTE: FOR TESTING PURPOSES ONLY:
        // In the above, changing the arguments to String retrieveStationParentLine = response.getStationParentLine(); would display the Parent line of station

        // Step 1: Resetting the station error value in the searchState to be null (in case any failed search attempts came before this "successful" attempt)
        SearchState searchState = searchViewModel.getState();
        searchState.setStateStationName(retrieveStationName);
        searchState.setStateStationError(null);
        // TODO [Polish implementation...]: Explanation of the above line
        //  This line sets the state's station error attribute value to null.
        //  This happens ONLY IF the prepareSuccessView has been triggered
        //  This is because should there be an error message stored in the state, we want to CLEAR THAT to display the success view
        //  Is there a better way of doing this?

        // TODO #2: We don't need to call searchViewModel.firePropertyChanged(); right?

        // Step 2: Setting values in the SearchPanelView
        StationInfoState stationInfoState = stationInfoViewModel.getState();
        stationInfoState.setStateStationName(retrieveStationName);
        //System.out.println("Station info state station name" + stationInfoState.getStateStationName()); //(For debugging purpose)
        stationInfoViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(stationInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        //TODO #3: Do we need to call setState() methods in general?

    }

    public void prepareFailView(String stationRetrievalError){
        SearchState searchState = searchViewModel.getState();
        searchState.setStateStationError(stationRetrievalError);
        searchViewModel.firePropertyChanged();
    }

}
