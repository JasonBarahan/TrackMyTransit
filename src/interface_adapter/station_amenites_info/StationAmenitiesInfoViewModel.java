package interface_adapter.station_amenites_info;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StationAmenitiesInfoViewModel extends ViewModel {
    public final String TITLE_LABEL = "Station Info View";

    public final String SHOW_INCOMING_VEHICLES_BUTTON_LABEL = "Show Incoming Vehicles";

    private StationAmenitiesInfoState state = new StationAmenitiesInfoState();

    public StationAmenitiesInfoViewModel() {
        super("stationInfo");
    }

    public void setState(StationAmenitiesInfoState state) {
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

    public StationAmenitiesInfoState getState() {
        return state;
    }
}
