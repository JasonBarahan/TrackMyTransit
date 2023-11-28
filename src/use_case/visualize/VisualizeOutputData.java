package use_case.visualize;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.List;

// TODO: unit tests: coordinate data and string data are of same length, and match size
public class VisualizeOutputData {
    private final List<Coordinate> coordinateData;
    private final List<String> stringData;
    private final int size;

    /**
     * Constructor.
     *
     * @param coordinateData the list of coordinates associated with the vehicle
     * @param stringData the list of vehicle metadata output as a string
     * @param size the size of both lists
     */
    public VisualizeOutputData(List<Coordinate> coordinateData, List<String> stringData, int size) {
        this.coordinateData = coordinateData;
        this.stringData = stringData;
        this.size = size;
    }

    public List<Coordinate> getCoordinateData() {
        return coordinateData;
    }

    public List<String> getStringData() {
        return stringData;
    }

    public int getSize() {
        return size;
    }
}
