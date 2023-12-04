package interface_adapter.station_general_info;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StationGeneralInfoViewModel extends ViewModel {
    public final String TITLE_LABEL = "Station Info View";

    public final String SHOW_INCOMING_VEHICLES_BUTTON_LABEL = "Show Incoming Vehicles";

    private StationGeneralInfoState state = new StationGeneralInfoState();

    public StationGeneralInfoViewModel() {
        super("stationInfo");
    }

    public void setState(StationGeneralInfoState state) {
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
