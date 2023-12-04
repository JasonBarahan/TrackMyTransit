package interface_adapter.visualize;

import use_case.visualize.VisualizeInputBoundary;
import use_case.visualize.VisualizeInputData;

import java.util.List;

/**
 * The controller for the visualization use case.
 */
public class VisualizeController {
    // get the use case interactor
    final VisualizeInputBoundary visualizeInteractor;

    /**
     * Constructor.
     *
     * @param visualizeInteractor the use case interactor for the visualization use case.
     */
    public VisualizeController(VisualizeInputBoundary visualizeInteractor) {
        this.visualizeInteractor = visualizeInteractor;
    }

    /**
     * Executes the use case interactor, splitting this data into a List<Coordinate> array list for storing coordinates
     * of a specific vehicle, and a List<String> array list for storing details pertaining to a specific vehicle.
     * Matching the two details to the same vehicle is done by array list index.
     *
     * @param vehicleData an ArrayList containing ArrayLists of strings, each of which denote data for
     *                    a particular vehicle.
     */
    public void execute(List<List<String>> vehicleData) {
        VisualizeInputData visualizeInputData = new VisualizeInputData(vehicleData);

        // execute
        visualizeInteractor.execute(visualizeInputData);
    }
}
