package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

public class SearchController {
    final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    public void execute(String query) {
        SearchInputData searchInputData = new SearchInputData(query);

        searchInteractor.execute(searchInputData);
    }
}
