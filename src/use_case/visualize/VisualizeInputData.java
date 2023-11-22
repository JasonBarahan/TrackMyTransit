package use_case.visualize;

import entity.Train;

/**
 * Input data for the visualization use case. Data is an array of Train objects.
 */
public class VisualizeInputData {
    final private Train[] trains;

    /**
     * Constructor.
     *
     * @param trains: an array of train objects
     */
    public VisualizeInputData(Train[] trains) {
        this.trains = trains;
    }

    /**
     * Train data getter.
     *
     * @return an array of train objects.
     */
    public Train[] getTrains() {
        return trains;
    }
}
