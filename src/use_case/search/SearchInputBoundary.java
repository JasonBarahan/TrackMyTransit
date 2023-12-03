package use_case.search;

import java.text.ParseException;

public interface SearchInputBoundary {
    void execute(SearchInputData searchInputData) throws ParseException;
}
