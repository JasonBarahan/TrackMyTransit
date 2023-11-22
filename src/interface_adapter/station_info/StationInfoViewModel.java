package interface_adapter.station_info;

import interface_adapter.ViewModel;
import interface_adapter.search.SearchState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StationInfoViewModel extends ViewModel {
    public final String TITLE_LABEL = "Station Info View";

    public final String SHOW_INCOMING_VEHICLES_BUTTON_LABEL = "Show Incoming Vehicles";

    private StationInfoState state = new StationInfoState();

    public StationInfoViewModel() {
        super("stationInfo");
    }

    public void setState(StationInfoState state) {
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

    public StationInfoState getState() {
        return state;
    }
}
