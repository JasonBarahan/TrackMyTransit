package interface_adapter.visualize;

import interface_adapter.ViewManagerModel;
import use_case.visualize.VisualizeOutputBoundary;
import use_case.visualize.VisualizeOutputData;

/**
 * Passes output data to the visualization view model and calls its firePropertyChanged method, updating the
 * visualization view and alerting the user.
 */
public class VisualizePresenter implements VisualizeOutputBoundary {

    // Since this is a pop-up window additional views need not be introduced here
    private final VisualizeViewModel visualizeViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructor.
     *
     * @param visualizeViewModel the view model for the visualize use case.
     * @param viewManagerModel the view manager model.
     */
    public VisualizePresenter(VisualizeViewModel visualizeViewModel, ViewManagerModel viewManagerModel) {
        this.visualizeViewModel = visualizeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view for showcase to the user.
     *
     * @param data the output data for the visualize use case to be displayed to the user.
     */
    @Override
    public void prepareSuccessView(VisualizeOutputData data) {
        // gets the state from the view model
        VisualizeState visualizeState = visualizeViewModel.getVisualizationState();

        // gets the data and sets it into state
        visualizeState.setCoordinateList(data.getCoordinateData());
        visualizeState.setVehicleInformationList(data.getStringData());
        visualizeState.setVehicleInformationSize(data.getSize());

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
     * @param error the error message
     */
    @Override
    public void prepareFailView(String error) {
        /*
            Why is this blank?

            The way this is integrated, all fail views are handled by the presenter of the use case which occurs
            before this one. A fail view should not be displayed to the user once the visualize use case is triggered.
            If for some reason the data provided does not trigger the success view, it is a bug and should be reported
            to developers.
         */
    }
}
