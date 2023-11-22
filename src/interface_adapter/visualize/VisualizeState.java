package interface_adapter.visualize;

import entity.Train;
import entity.Bus;
import entity.Vehicle;

// TODO: Add list of bus objects to the state

/**
 * Contains data required for visualizing vehicles.
 */
public class VisualizeState {
    // Train data
    private Train[] trains;

    // Error string
    private String errorString = null;

    /**
     * Gets the train objects in the state.
     *
     * @return an array of Train objects.
     */
    public Train[] getTrains() {
        return trains;
    }

    /**
     * Sets the train objects in the state.
     *
     * @param trains: an array of Train objects.
     */
    public void setTrains(Train[] trains) {
        this.trains = trains;
    }

    /**
     * Gets the error string for the visualization state, in case data visualization fails.
     *
     * @return the error message.
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Sets the error string for the visualization state, in case data visualization fails.
     *
     * @param errorString: The error message.
     */
    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
