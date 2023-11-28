package interface_adapter.visualize;

import entity.Train;
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
     * @param visualizeInteractor: the use case interactor for the visualization use case.
     */
    public VisualizeController(VisualizeInputBoundary visualizeInteractor) {
        this.visualizeInteractor = visualizeInteractor;
    }

    /**
     * Executes the use case interactor.
     *
     * @param vehicleData: an array of vehicle (bus/train) objects
     */
    public void execute(List<List<String>> vehicleData) {
        VisualizeInputData visualizeInputData = new VisualizeInputData(vehicleData);

        // execute
        visualizeInteractor.execute(visualizeInputData);
    }
}
