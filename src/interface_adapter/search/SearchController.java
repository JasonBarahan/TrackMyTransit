package interface_adapter.search;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

import java.text.ParseException;

public class SearchController {
    final SearchInputBoundary userSearchUseCaseInteractor;
    public SearchController(SearchInputBoundary userSearchUseCaseInteractor) {
        this.userSearchUseCaseInteractor = userSearchUseCaseInteractor;
    }

    public void execute(String stationName) throws ParseException {
        SearchInputData searchInputData = new SearchInputData(stationName);
        userSearchUseCaseInteractor.execute(searchInputData);
    }
}
