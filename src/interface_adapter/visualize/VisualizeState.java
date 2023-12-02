package interface_adapter.visualize;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.List;

/**
 * Contains data required for visualizing vehicles.
 */
public class VisualizeState {
    // Vehicle data
    private List<Coordinate> coordinateList;
    private List<String> vehicleInformationList;
    private int vehicleInformationSize;

    /* Error string */
    private String errorString;

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
    public List<String> getVehicleInformationList() {
        return vehicleInformationList;
    }

    /**
     * Sets the list of strings comprising vehicle information from the state.
     * @param vehicleInformationList list of vehicle information strings
     */
    public void setVehicleInformationList(List<String> vehicleInformationList) {
        this.vehicleInformationList = vehicleInformationList;
    }

    /**
     * Gets the size of both coordinate and vehicle string lists.
     * @return size integer
     */
    public int getVehicleInformationSize() {
        return vehicleInformationSize;
    }

    /**
     * Sets the size of both coordinate and vehicle string lists.
     * @param vehicleInformationSize size integer
     */
    public void setVehicleInformationSize(int vehicleInformationSize) {
        this.vehicleInformationSize = vehicleInformationSize;
    }
}
