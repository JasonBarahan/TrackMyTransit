package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;


import interface_adapter.station_general_info.StationGeneralInfoViewModel;
import use_case.station_general_info.StationGeneralInfoDataAccessInterface;
import use_case.station_general_info.StationGeneralInfoInputBoundary;
import use_case.station_general_info.StationGeneralInfoInteractor;
import use_case.station_general_info.StationGeneralInfoOutputBoundary;
import view.SearchPanelView;

import javax.swing.*;
import java.io.IOException;

public class SearchStationGeneralInfoUseCaseFactory {

    /** Prevent instantiation. */
    private SearchStationGeneralInfoUseCaseFactory() {}

    public static SearchPanelView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            StationGeneralInfoDataAccessInterface searchShowAmenitiesDataAccessInterface,
            StationGeneralInfoViewModel stationAmenitiesInfoViewModel) {

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
            StationGeneralInfoDataAccessInterface searchShowAmenitiesDataAccessInterface,
            StationGeneralInfoViewModel stationInfoViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        StationGeneralInfoOutputBoundary searchShowAmenitiesOutputBoundary = new SearchPresenter(searchViewModel,
                stationInfoViewModel, viewManagerModel);

        StationGeneralInfoInputBoundary searchShowAmenitiesInteractor = new StationGeneralInfoInteractor(
                searchShowAmenitiesDataAccessInterface, searchShowAmenitiesOutputBoundary);

        return new SearchController(searchShowAmenitiesInteractor);
    }
}
