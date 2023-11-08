package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import org.jetbrains.annotations.NotNull;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;


    public SearchPresenter(ViewManagerModel viewManagerModel,
                           SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }
    @Override
    public void prepareSuccessView(@NotNull SearchOutputData searchOutputData) {
        SearchState searchState = searchViewModel.getState();
        searchState.setStationName(searchOutputData.getStationName());
        this.searchViewModel.setState(searchState);
        searchViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String error) {
        SearchState searchState = searchViewModel.getState();
        searchState.setStationError(error);
        searchViewModel.firePropertyChanged();
    }
}
