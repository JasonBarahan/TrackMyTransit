package interface_adapter.visualize;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The view model for the vehicle visualization use case.
 */
public class VisualizeViewModel extends ViewModel {

    /* User guidance strings */
    public static final String WINDOW_LABEL = "TrackMyTransit";
    public static final String HELP_LABEL = "Hold the right mouse button while moving it to move the map. Double-click to zoom.";
    public static final String RESIZE_BUTTON_LABEL = "Fit markers to screen";
    public static final String TRAIN_NOT_IN_SERVICE_MESSAGE = "This train is currently not in service. You won't be able to view the position of this train yet.";

    public static final double OUT_OF_SERVICE_COORDINATE = -1;


    // The application state; needed to alert the View on what to present.
    private VisualizeState visualizationState = new VisualizeState();

    // Add listener support
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Generates the window name.
     */
    public VisualizeViewModel() {
        super("TrackMyTransit");
    }

    /**
     * Fires the property changed call to the View.
     */
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.visualizationState);
    }

    /**
     * Adds a property change listener to the view.
     *
     * @param listener: The property change listener.
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * VisualizationState getter method.
     *
     * @return a PropertyChangeListener listener.
     */
    public VisualizeState getVisualizationState() {
        return this.visualizationState;
    }

    /**
     * VisualizationState setter method.
     *
     * @param visualizationState: the visualizationState to set.
     */
    public void setVisualizationState(VisualizeState visualizationState) {
        this.visualizationState = visualizationState;
    }
}
