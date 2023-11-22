package interface_adapter.visualize;

import interface_adapter.ViewManagerModel;
import use_case.visualize.VisualizeOutputBoundary;
import use_case.visualize.VisualizeOutputData;

/**
 * Prepares the proper view, and relevant data, to present to the user.
 */
public class VisualizePresenter implements VisualizeOutputBoundary {

    private final VisualizeViewModel visualizeViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructor.
     *
     * @param visualizeViewModel: the view model for the visualize use case.
     * @param viewManagerModel: the view manager model.
     */
    public VisualizePresenter(VisualizeViewModel visualizeViewModel, ViewManagerModel viewManagerModel) {
        this.visualizeViewModel = visualizeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view for showcase to the user.
     *
     * @param data : the output data for the visualize use case to be displayed to the user.
     */
    @Override
    public void prepareSuccessView(VisualizeOutputData data) {
        // gets the state from the view model
        VisualizeState visualizeState = visualizeViewModel.getVisualizationState();

        // gets the data
        visualizeState.setTrains(data.getTrains());
        // sets the data into the view model's state

        // fire changed property

        // tells the view manager to set the state as the active view

        // fire changed property
    }

    /**
     * Prepares the fail view for showcase to the user.
     *
     * @param error : the error message.
     */
    @Override
    public void prepareFailView(String error) {

    }
}
