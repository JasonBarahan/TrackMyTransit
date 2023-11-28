package use_case.visualize;

import entity.Train;

import java.util.ArrayList;
import java.util.List;

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
//        List<List<String>> inputData = visualizeInputData.getTrains();
        // TODO: What should the program do to the array of vehicle objects when input data is passed in?
        // TODO: Usually more code goes here...

        // TODO: Commented out the above line and placed mock data for testing purposes.
        List<List<String>> inputData = new ArrayList<>();
        List<List<String>> outputData = new ArrayList<>();


        List<String> vehicle1 = new ArrayList<>();
        vehicle1.add("LW");
        vehicle1.add("Lakeshore West");
        vehicle1.add("T");
        vehicle1.add("LW - Union Station");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("1026");
        vehicle1.add("43.3419870");
        vehicle1.add("-79.8076960");

        List<String> vehicle2 = new ArrayList<>();
        vehicle2.add("LW");
        vehicle2.add("Lakeshore West");
        vehicle2.add("T");
        vehicle2.add("LW - Union Station");
        vehicle2.add("2023-11-27 16:31:00");
        vehicle2.add("2023-11-27 16:31:00");
        vehicle2.add("1026");
        vehicle2.add("43.6448050");
        vehicle2.add("-79.3777040");

        inputData.add(vehicle1);
        inputData.add(vehicle2);

        // ArrayList for processedVehicleData:
        // Obtain relevant data for marker creation
        for (List<String> vehicle : inputData) {
            List<String> processedVehicleData = new ArrayList<>();

            // Get the scheduled time of departure
            processedVehicleData.add(vehicle.get(4).substring(10));

            // Get the service name
            processedVehicleData.add(vehicle.get(3));

            // Get lat (top) and long (bottom)
            processedVehicleData.add(vehicle.get(7));
            processedVehicleData.add(vehicle.get(8));

            // Get the estimated time of departure
            processedVehicleData.add("Scheduled at " + vehicle.get(5).substring(10));
            outputData.add(processedVehicleData);
        }

        // Generate the output data
        VisualizeOutputData visualizeOutputData = new VisualizeOutputData(outputData);

        // pass it into the presenter (and through the output boundary)
        visualizePresenter.prepareSuccessView(visualizeOutputData);
    }
}
