package use_case.visualize;

/**
 * The input data boundary for the Visualize use case.
 */
public interface VisualizeInputBoundary {

    /**
     * Takes the input data and executes the desired actions of the use case.
     *
     * @param visualizeInputData: the input data for the visualization use case.
     */
    void execute(VisualizeInputData visualizeInputData);
}
