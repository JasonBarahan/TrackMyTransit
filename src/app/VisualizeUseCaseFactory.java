package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeViewModel;
import use_case.visualize.VisualizeInputBoundary;
import use_case.visualize.VisualizeInteractor;
import use_case.visualize.VisualizeOutputBoundary;
import view.MapVisualizationView;

/**
 * A factory for visualization use cases.
 */
public class VisualizeUseCaseFactory {
    /**
     * Prevents instantiation
     */
    public VisualizeUseCaseFactory() {
    }

    /**
     * Creates an instance of the map visualization view.
     *
     * @param viewManagerModel the view manager model object
     * @param visualizeViewModel the view model object
     * @return the MapVisualizationView object.
     */
    public static MapVisualizationView create(
        ViewManagerModel viewManagerModel,
        VisualizeViewModel visualizeViewModel) {

        VisualizeController visualizeController = createVisualizeUseCase(viewManagerModel,
                visualizeViewModel);

        return new MapVisualizationView(visualizeViewModel, visualizeController);
    }

    private static VisualizeController createVisualizeUseCase(
            ViewManagerModel viewManagerModel, VisualizeViewModel visualizeViewModel) {
        // Notice how we pass this method's parameters to the Presenter.
        VisualizeOutputBoundary visualizeOutputBoundary = new VisualizePresenter(visualizeViewModel, viewManagerModel);

        VisualizeInputBoundary visualizeInteractor = new VisualizeInteractor(visualizeOutputBoundary);

        return new VisualizeController(visualizeInteractor);
    }
}
