package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.station_info.StationInfoState;
import interface_adapter.station_info.StationInfoViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

import java.util.List;

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
        List<String> retrieveStationAmenities = response.getStationAmenities();

        // TODO: Left NOTE FOR TESTING PURPOSES ONLY. Delete in final implementation

        // In the above, changing the arguments to String retrieveStationParentLine = response.getStationParentLine(); would display the Parent line of station

        // Step 1: Resetting the station error value in the searchState to be null (in case any failed search attempts came before this "successful" attempt)
        SearchState searchState = searchViewModel.getState();
        searchState.setStateStationName(retrieveStationName);
      
        //  This line sets the state's station error attribute value to null.
        //  This happens ONLY IF the prepareSuccessView has been triggered
        //  This is because should there be an error message stored in the state, we want to CLEAR THAT to display the success view
        searchState.setStateStationError(null);        

        // Step 2: Setting values in the SearchPanelView
        StationInfoState stationInfoState = stationInfoViewModel.getState();
        stationInfoState.setStateStationName(retrieveStationName);
        stationInfoState.setStateStationAmenities(retrieveStationAmenities.toString());

        stationInfoViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(stationInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String stationRetrievalError){
        SearchState searchState = searchViewModel.getState();
        searchState.setStateStationError(stationRetrievalError);
        searchViewModel.firePropertyChanged();
    }
}
