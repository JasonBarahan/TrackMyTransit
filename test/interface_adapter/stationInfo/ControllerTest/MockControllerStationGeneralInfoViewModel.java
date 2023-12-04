package interface_adapter.stationInfo.ControllerTest;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MockControllerStationGeneralInfoViewModel extends ViewModel {
    public final String TITLE_LABEL = "Station Info View";

    public final String SHOW_INCOMING_VEHICLES_BUTTON_LABEL = "Show Incoming Vehicles";

    private MockControllerStationGeneralInfoState state = new MockControllerStationGeneralInfoState();

    public MockControllerStationGeneralInfoViewModel() {
        super("stationInfo");
    }

    public void setState(MockControllerStationGeneralInfoState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the SearchPresenter will call to let the StationInfoViewModel know
    // to alert the StationInfoView
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public MockControllerStationGeneralInfoState getState() {
        return state;
    }
}
