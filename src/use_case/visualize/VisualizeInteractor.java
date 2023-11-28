package use_case.visualize;

import org.openstreetmap.gui.jmapviewer.Coordinate;
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
     * TODO: complete me
     *
     * @param visualizeInputData : the input data for the visualization use case.
     */
    @Override
    public void execute(VisualizeInputData visualizeInputData) {
        // get data
        List<List<String>> inputData = visualizeInputData.getVehicleData();

        // create new ArrayList objects for coordinates and strings
        List<Coordinate> coordinateList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        // ArrayList for processedVehicleData:
        // Obtain relevant data for marker creation
        for (List<String> vehicle : inputData) {

            // Get lat (top) and long (bottom), then add to coordinates list
            coordinateList.add(new Coordinate(Double.parseDouble(vehicle.get(7)), Double.parseDouble(vehicle.get(8))));

            // String data - used to identify a vehicle to the user.
            StringBuilder vehicleData = new StringBuilder();

            // Get the scheduled time of departure
            vehicleData
                    .append("[")
                    .append(vehicle.get(4).substring(11));

            // Get the estimated time of departure.
            // Only displays a value if scheduled time and estimate time aren't equivalent
            if (vehicle.get(4).substring(11).equals(vehicle.get(5).substring(11))) {
                vehicleData
                        .append("]");
            }
            else {
                vehicleData
                        .append(", estimated ")
                        .append(vehicle.get(5).substring(11))
                        .append("]");
            }

            // Get the service name
            vehicleData
                    .append(" ")
                    .append(vehicle.get(3));

            // add this finished string to processed vehicle data list
            stringList.add(String.valueOf(vehicleData));
        }

        // get size
        // TODO: make this a test
        assert stringList.size() == coordinateList.size();

        // Generate the output data
        VisualizeOutputData visualizeOutputData = new VisualizeOutputData(coordinateList, stringList, stringList.size());

        // pass it into the presenter (and through the output boundary)
        visualizePresenter.prepareSuccessView(visualizeOutputData);
    }
}
