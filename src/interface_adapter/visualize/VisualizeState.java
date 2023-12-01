package interface_adapter.visualize;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.List;

/**
 * Contains data required for visualizing vehicles.
 */
public class VisualizeState {
    // Vehicle data
    private List<Coordinate> coordinateList;
    private List<String> stringList;
    private int size;

    /* Error string */
    private String errorString = null;

    /**
     * Gets the error string for the visualization state, in case data visualization fails.
     *
     * @return the error message
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Sets the error string for the visualization state, in case data visualization fails.
     *
     * @param errorString the error message
     */
    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    /**
     * Gets the list of coordinates associated with a vehicle from the state.
     * @return list of coordinates
     */
    public List<Coordinate> getCoordinateList() {
        return coordinateList;
    }

    /**
     * Sets the list of coordinates associated with a vehicle from the state.
     * @param coordinateList list of coordinates
     */
    public void setCoordinateList(List<Coordinate> coordinateList) {
        this.coordinateList = coordinateList;
    }

    /**
     * Gets the list of strings comprising vehicle information from the state.
     * @return list of vehicle information strings
     */
    public List<String> getStringList() {
        return stringList;
    }

    /**
     * Sets the list of strings comprising vehicle information from the state.
     * @param stringList list of vehicle information strings
     */
    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    /**
     * Gets the size of both coordinate and vehicle string lists.
     * @return size integer
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of both coordinate and vehicle string lists.
     * @param size size integer
     */
    public void setSize(int size) {
        this.size = size;
    }
}
