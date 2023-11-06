package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {
    // view models
    private final SearchViewModel searchViewModel;

    private ViewManagerModel viewManagerModel;


    public SearchPresenter(ViewManagerModel viewManagerModel,
                           SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }

    public void prepareSuccessView(SearchOutputData response) {
        SearchState searchState = searchViewModel.getState();
        searchState.setSearch(response.getStationName());       // this queries the station
        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChanged();

        // this was replaced by a pop up for testing purposes
        // TODO: Check if modifications are needed to turn popup into completely new view.
        this.viewManagerModel.setActiveView(searchViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String error) {
        SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(error);
        searchViewModel.firePropertyChanged();
    }
}
