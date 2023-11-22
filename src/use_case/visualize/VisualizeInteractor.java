package use_case.visualize;

import entity.Train;

public class VisualizeInteractor implements VisualizeInputBoundary {

    // This is where a data access object goes. Right now, data is not fetched from any persistent data source,
    // thus it is not needed.

    // invoke presenter as output boundary
    final VisualizeOutputBoundary visualizePresenter;

    /**
     * Constructor.
     *
     * @param visualizeOutputBoundary: an output boundary for the Visualize use case.
     */
    public VisualizeInteractor(VisualizeOutputBoundary visualizeOutputBoundary) {
        this.visualizePresenter = visualizeOutputBoundary;
    }

    /**
     * Takes the input data and executes the desired actions of the use case.
     *
     * @param visualizeInputData : the input data for the visualization use case.
     */
    @Override
    public void execute(VisualizeInputData visualizeInputData) {
        // get data
        Train[] trainData = visualizeInputData.getTrains();
        // TODO: What should the program do to the array of vehicle objects when input data is passed in?
        // TODO: Usually more code goes here...

        // Generate the output data
        VisualizeOutputData visualizeOutputData = new VisualizeOutputData(trainData);

        // pass it into the presenter (and through the output boundary)
        visualizePresenter.prepareSuccessView(visualizeOutputData);
    }
}
