package interface_adapter.stationInfo.PresenterTest;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MockPresenterSearchViewModel extends SearchViewModel {
    public final String TITLE_LABEL = "Find Station Info";
    public final String STATION_INPUT_LABEL = "Station name: ";

    public static final String SUBMIT_BUTTON_LABEL = "Submit";

    private SearchState state = new MockPresenterSearchState();

    public MockPresenterSearchViewModel() {
    }

    public void setState(SearchState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the SearchPresenter will call to let the SearchPanelViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SearchState getState() {
        return state;
    }
}
