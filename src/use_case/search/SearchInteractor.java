package use_case.search;

public class SearchInteractor implements SearchInputBoundary {
    final SearchDataAccessInterface stationDataAccessObject;
    final SearchOutputBoundary stationPresenter;

    //Might add a Factory object later, here is the one used for SignupInteractor for reference:

    //final UserFactory userFactory;

    public SearchInteractor(SearchDataAccessInterface stationDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary) {
        this.stationDataAccessObject = stationDataAccessInterface;
        this.stationPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        if (stationDataAccessObject.stationExist(searchInputData.getStationName())) {
            ...
        } else{
            ...
        }
    }
}
