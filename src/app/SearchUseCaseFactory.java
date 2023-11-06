package app;

import data_access.FileStationDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.SearchPanelView;

public class SearchUseCaseFactory {
    // no instantiation
    public SearchUseCaseFactory() {}

    public static SearchPanelView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            FileStationDataAccessObject fileStationDataAccessObject
    ) {
        SearchController searchController = createSearchUseCase(
                viewManagerModel, searchViewModel, fileStationDataAccessObject);

        return new SearchPanelView(searchViewModel, searchController);
    }


    // create the use case itself
    private static SearchController createSearchUseCase(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            FileStationDataAccessObject fileStationDataAccessObject
    ) {
        SearchOutputBoundary searchOutputBoundary = new SearchPresenter(viewManagerModel, searchViewModel);

        SearchInputBoundary searchInteractor = new SearchInteractor(fileStationDataAccessObject, searchOutputBoundary);

        return new SearchController(searchInteractor);
    }
}