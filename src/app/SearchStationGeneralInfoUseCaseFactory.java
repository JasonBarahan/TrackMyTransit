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

    /** Constructor */
    private SearchStationGeneralInfoUseCaseFactory() {}

    /**
     * Returns a SearchPanelView object, which is a View object responsible for displaying info
     * for the search use case (team use case)
     * @param  viewManagerModel  a ViewManagerModel object
     * @param  searchViewModel a SearchViewModel object
     * @param  stationGeneralInfoDAO a StationGeneralInfoDataAccessInterface object
     * @param  stationGeneralInfoViewModel a StationGeneralInfoViewModel object
     * @return a SerachPanelView object
     */
    public static SearchPanelView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            StationGeneralInfoDataAccessInterface stationGeneralInfoDAO,
            StationGeneralInfoViewModel stationGeneralInfoViewModel) {

        try {
            SearchController searchController = createSearchUseCase(viewManagerModel, searchViewModel,
                    stationGeneralInfoDAO, stationGeneralInfoViewModel);
            return new SearchPanelView(searchViewModel, searchController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open station data file.");
        }

        return null;
    }

    /**
     * Returns a SearchPanelView object, which is a View object responsible for displaying info
     * for the search use case (team use case)
     * @param  viewManagerModel  a ViewManagerModel object
     * @param  searchViewModel a SearchViewModel object
     * @param  stationGeneralInfoDAO a StationGeneralInfoDataAccessInterface object
     * @param  stationGeneralInfoViewModel a StationGeneralInfoViewModel object
     * @return a SerachPanelView object
     */

    private static SearchController createSearchUseCase(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            StationGeneralInfoDataAccessInterface stationGeneralInfoDAO,
            StationGeneralInfoViewModel stationGeneralInfoViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        StationGeneralInfoOutputBoundary searchShowAmenitiesOutputBoundary = new SearchPresenter(searchViewModel,
                stationGeneralInfoViewModel, viewManagerModel);

        StationGeneralInfoInputBoundary searchShowAmenitiesInteractor = new StationGeneralInfoInteractor(
                stationGeneralInfoDAO, searchShowAmenitiesOutputBoundary);

        return new SearchController(searchShowAmenitiesInteractor);
    }
}
