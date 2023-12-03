package interface_adapter.search;
import use_case.search_show_amenities.SearchShowAmenitiesInputBoundary;
import use_case.search_show_amenities.SearchShowAmenitiesInputData;

public class SearchController {
    final SearchShowAmenitiesInputBoundary userSearchUseCaseInteractor;
    public SearchController(SearchShowAmenitiesInputBoundary userSearchUseCaseInteractor) {
        this.userSearchUseCaseInteractor = userSearchUseCaseInteractor;
    }

    public void execute(String stationName) {
        SearchShowAmenitiesInputData searchInputData = new SearchShowAmenitiesInputData(stationName);
        userSearchUseCaseInteractor.execute(searchInputData);
    }
}
