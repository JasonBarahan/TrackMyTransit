package interface_adapter.visualize;

import interface_adapter.ViewManagerModel;
import use_case.visualize.VisualizeOutputBoundary;
import use_case.visualize.VisualizeOutputData;

/**
 * Prepares the proper view, and relevant data, to present to the user.
 */
public class VisualizePresenter implements VisualizeOutputBoundary {

    // Since this is a pop-up window additional views need not be introduced here
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

        // gets the data and sets it into state
        visualizeState.setCoordinateList(data.getCoordinateData());
        visualizeState.setStringList(data.getStringData());
        visualizeState.setSize(data.getSize());

        // sets the data into the view model's state
        visualizeViewModel.setVisualizationState(visualizeState);

        // fire changed property
        visualizeViewModel.firePropertyChanged();

        // tells the view manager to set the state as the active view
        viewManagerModel.setActiveView(visualizeViewModel.getViewName());

        // fire changed property
        viewManagerModel.firePropertyChanged();
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
