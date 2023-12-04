package interface_adapter.stationInfo.PresenterTest;
import interface_adapter.station_general_info.StationGeneralInfoState;
import interface_adapter.station_general_info.StationGeneralInfoViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MockPresenterStationGeneralInfoViewModel extends StationGeneralInfoViewModel {
    public final String TITLE_LABEL = "Station Info View";

    public final String SHOW_INCOMING_VEHICLES_BUTTON_LABEL = "Show Incoming Vehicles";

    private StationGeneralInfoState state = new MockPresenterStationGeneralInfoState();

    public MockPresenterStationGeneralInfoViewModel() {

    }

    public void setState(MockPresenterStationGeneralInfoState state) {
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

    public StationGeneralInfoState getState() {
        return state;
    }
}
