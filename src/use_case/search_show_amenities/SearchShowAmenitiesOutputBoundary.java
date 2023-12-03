package use_case.search_show_amenities;

public interface SearchShowAmenitiesOutputBoundary {
    void prepareSuccessView(SearchShowAmenitiesOutputData searchOutputData);
    void prepareFailView(String error);
}
