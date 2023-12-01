package use_case.visualize;

/**
 * The output boundary for the visualize use case, which prepares the proper view to display to the user.
 */
public interface VisualizeOutputBoundary {
    /**
     * Prepares the success view for showcase to the user.
     *
     * @param data the output data for the visualize use case to be displayed to the user.
     */
    public void prepareSuccessView(VisualizeOutputData data);

    /**
     * Prepares the fail view for showcase to the user.
     *
     * @param error the error message.
     */
    public void prepareFailView(String error);
}
