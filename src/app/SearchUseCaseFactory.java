package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;


import interface_adapter.station_info.StationInfoViewModel;
import use_case.search.SearchDataAccessInterface;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.SearchPanelView;

import javax.swing.*;
import java.io.IOException;

public class SearchUseCaseFactory {

    /** Prevent instantiation. */
    private SearchUseCaseFactory() {}

    public static SearchPanelView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchDataAccessInterface searchDataAccessObject,
            StationInfoViewModel stationInfoViewModel) {

        try {
            SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel, searchDataAccessObject, stationInfoViewModel);
            return new SearchPanelView(searchViewModel, searchController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open station data file.");
        }

        return null;
    }

    private static SearchController createSearchUseCase(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchDataAccessInterface searchDataAccessObject,
            StationInfoViewModel stationInfoViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel, stationInfoViewModel, viewManagerModel);
        
        SearchInputBoundary searchInteractor = new SearchInteractor(
                searchDataAccessObject, searchOutputBoundary);

        return new SearchController(searchInteractor);
    }
}
