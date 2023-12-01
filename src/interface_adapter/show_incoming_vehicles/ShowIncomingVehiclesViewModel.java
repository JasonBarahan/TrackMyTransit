package interface_adapter.show_incoming_vehicles;

import interface_adapter.ViewModel;
import interface_adapter.search.SearchState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShowIncomingVehiclesViewModel extends ViewModel {
    public final String TITLE_LABEL = "Station Incoming Vehicles View";

    private ShowIncomingVehiclesState state = new ShowIncomingVehiclesState();


    public ShowIncomingVehiclesViewModel() {super("show_incoming_vehicles");}

    public void setState(ShowIncomingVehiclesState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public ShowIncomingVehiclesState getState() {
        return state;
    }
}
