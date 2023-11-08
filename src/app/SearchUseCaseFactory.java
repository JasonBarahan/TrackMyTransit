package app;
import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import use_case.search.*;
import view.SearchPanelView;

import javax.swing.*;
import java.io.IOException;

public class SearchUseCaseFactory {
    private SearchUseCaseFactory() {}

    public static SearchPanelView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchDataAccessInterface stationDataAccessObject) {
        SearchController searchController = creatSearchUseCase(
                viewManagerModel, searchViewModel, stationDataAccessObject);
        return new SearchPanelView(searchViewModel, searchController);

    }
    private static SearchController creatSearchUseCase(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchDataAccessInterface stationDataAccessObject) {

        SearchOutputBoundary searchOutputBoundary = new SearchPresenter(viewManagerModel, searchViewModel);

            SearchInputBoundary stationSearchInteractor = new SearchInteractor(
                    stationDataAccessObject, searchOutputBoundary);

        return new SearchController(stationSearchInteractor);

    }

}
