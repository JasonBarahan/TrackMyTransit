package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;


import interface_adapter.station_amenites_info.StationAmenitiesInfoViewModel;
import use_case.search_show_amenities.SearchShowAmenitiesDataAccessInterface;
import use_case.search_show_amenities.SearchShowAmenitiesInputBoundary;
import use_case.search_show_amenities.SearchShowAmenitiesInteractor;
import use_case.search_show_amenities.SearchShowAmenitiesOutputBoundary;
import view.SearchPanelView;

import javax.swing.*;
import java.io.IOException;

public class SearchShowAmenitiesUseCaseFactory {

    /** Prevent instantiation. */
    private SearchShowAmenitiesUseCaseFactory() {}

    public static SearchPanelView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchShowAmenitiesDataAccessInterface searchShowAmenitiesDataAccessInterface,
            StationAmenitiesInfoViewModel stationAmenitiesInfoViewModel) {

        try {
            SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel,
                    searchShowAmenitiesDataAccessInterface, stationAmenitiesInfoViewModel);
            return new SearchPanelView(searchViewModel, searchController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open station data file.");
        }

        return null;
    }

    private static SearchController createSearchUseCase(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchShowAmenitiesDataAccessInterface searchShowAmenitiesDataAccessInterface,
            StationAmenitiesInfoViewModel stationInfoViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        SearchShowAmenitiesOutputBoundary searchShowAmenitiesOutputBoundary = new SearchPresenter(searchViewModel,
                stationInfoViewModel, viewManagerModel);

        SearchShowAmenitiesInputBoundary searchShowAmenitiesInteractor = new SearchShowAmenitiesInteractor(
                searchShowAmenitiesDataAccessInterface, searchShowAmenitiesOutputBoundary);

        return new SearchController(searchShowAmenitiesInteractor);
    }
}
