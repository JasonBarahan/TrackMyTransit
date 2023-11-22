package interface_adapter.visualize;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The view model for the vehicle visualization use case.
 */
public class VisualizeViewModel extends ViewModel {

    // Final labels for internal use
    private final String WINDOW_LABEL = "TrackMyTransit";

    // User guidance string
    private final String HELP_LABEL = "Hold the right mouse button while moving it to move the map. Double-click to zoom.";

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
