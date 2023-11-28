package interface_adapter.visualize;

import entity.Train;
import entity.Bus;
import entity.Vehicle;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.List;

// TODO: Add list of bus objects to the state

/**
 * Contains data required for visualizing vehicles.
 */
public class VisualizeState {
    // Train data
    private List<Coordinate> coordinateList;
    private List<String> stringList;
    private int size;

    // Error string
    private String errorString = null;

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

    public List<Coordinate> getCoordinateList() {
        return coordinateList;
    }

    public void setCoordinateList(List<Coordinate> coordinateList) {
        this.coordinateList = coordinateList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
