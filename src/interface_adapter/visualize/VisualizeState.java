package interface_adapter.visualize;

import entity.Train;
import entity.Bus;
import entity.Vehicle;

import java.util.List;

// TODO: Add list of bus objects to the state

/**
 * Contains data required for visualizing vehicles.
 */
public class VisualizeState {
    // Train data
    private List<List<String>> data;

    // Error string
    private String errorString = null;

    /**
     * Gets the train objects in the state.
     *
     * @return an array of Train objects.
     */
    public List<List<String>> getData() {
        return data;
    }

    /**
     * Sets the train objects in the state.
     *
     * @param data: an array of Vehicle objects.
     */
    public void setData(List<List<String>> data) {
        this.data = data;
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
