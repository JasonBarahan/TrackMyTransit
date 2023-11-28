package use_case.visualize;

import entity.Train;

import java.util.List;

/**
 * Input data for the visualization use case. Data is an array of Train objects.
 */
public class VisualizeInputData {
    final private List<List<String>> vehicleData;

    /**
     * Constructor.
     *
     * @param vehicleData: an array of vehicle (bus/train) objects
     */
    public VisualizeInputData(List<List<String>> vehicleData) {
        this.vehicleData = vehicleData;
    }

    /**
     * Train data getter.
     *
     * @return an array of train objects.
     */
    public List<List<String>> getVehicleData() {
        return vehicleData;
    }
}
