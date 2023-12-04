package interface_adapter.stationInfo.ControllerTest;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MockControllerSearchViewModel extends ViewModel {
    public final String TITLE_LABEL = "Find Station Info";
    public final String STATION_INPUT_LABEL = "Station name: ";

    public static final String SUBMIT_BUTTON_LABEL = "Submit";

    private MockControllerSearchState state = new MockControllerSearchState(); // state being passed in MockControllerSearchViewModel is a "fake, dummy state"

    public MockControllerSearchViewModel() {
        super("search");
    }

    public void setState(MockControllerSearchState state) {
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

    public MockControllerSearchState getState() {
        return state;
    }
}
