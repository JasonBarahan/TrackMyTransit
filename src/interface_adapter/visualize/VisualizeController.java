package interface_adapter.visualize;

import entity.Train;
import use_case.visualize.VisualizeInputBoundary;
import use_case.visualize.VisualizeInputData;

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
     * @param trainData: an array of train objects
     */
    public void execute(Train[] trainData) {
        VisualizeInputData visualizeInputData = new VisualizeInputData(trainData);

        // execute
        visualizeInteractor.execute(visualizeInputData);
    }
}
