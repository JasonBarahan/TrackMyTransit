package interface_adapter.search;

public class SearchState {
    private String search = null;
    private String searchError = null;

    public SearchState(SearchState copy) {
        search = copy.search;
        searchError = copy.searchError;
    }

    // insist on passing in a SearchState object
    public SearchState() {}

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }
}
