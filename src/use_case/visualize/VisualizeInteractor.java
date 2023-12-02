package use_case.visualize;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class VisualizeInteractor implements VisualizeInputBoundary {

    // This is where a data access object goes. Right now, data is not fetched from any persistent data source,
    // thus it is not needed.

    // invoke presenter as output boundary
    final VisualizeOutputBoundary visualizePresenter;

    final double OUT_OF_SERVICE_COORDINATE = -1;

    private String boxText (String str) {
        return "[" + str + "]";
    }

    /**
     * Constructor.
     *
     * @param visualizeOutputBoundary an output boundary for the Visualize use case.
     */
    public VisualizeInteractor(VisualizeOutputBoundary visualizeOutputBoundary) {
        this.visualizePresenter = visualizeOutputBoundary;
    }

    /**
     * Takes the input data and executes the desired actions of the use case.
     *
     * @param visualizeInputData the input data for the visualization use case.
     */
    @Override
    public void execute(VisualizeInputData visualizeInputData) {
        // get data
        List<List<String>> inputData = visualizeInputData.getVehicleData();
        System.out.println(inputData);

        // create new ArrayList objects for coordinates and strings
        List<Coordinate> coordinateList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        // ArrayList for processedVehicleData:
        // Obtain relevant data for marker creation
        for (List<String> vehicle : inputData) {

            // Get lat (top) and long (bottom), then add to coordinates list
            coordinateList.add(new Coordinate(
                    Double.parseDouble(vehicle.get(2).substring(18)),
                    Double.parseDouble(vehicle.get(3).substring(19))));

            // String of vehicle metadata - used to identify a vehicle to the user.
            StringBuilder vehicleData = new StringBuilder();

            /* Get the scheduled time of departure and estimated time of departure. */

            // This branch is triggered if both latitude and longitude are -1.0, which denotes a train being out of service
            // (non-trackable)
            if (Double.parseDouble(vehicle.get(2).substring(18)) == OUT_OF_SERVICE_COORDINATE
                    && Double.parseDouble(vehicle.get(3).substring(18)) == OUT_OF_SERVICE_COORDINATE) {
                vehicleData
                        .append(boxText(
                                vehicle.get(5).substring(11)
                                        + ", currently not in service"));
            }

            // This branch triggers if there is no difference between the scheduled and estimated departure times
            else if (vehicle.get(5).substring(11).equals(vehicle.get(7).substring(11))) {
                vehicleData
                        .append(boxText(vehicle.get(5).substring(11)));
            }

            // This branch triggers if there is a delay (discrepancy between scheduled and estimated departure times).
            else {
                vehicleData
                        .append(boxText(
                                vehicle.get(5).substring(11)
                                + ", estimated "
                                + vehicle.get(7).substring(11)));
            }

            // Get the service name
            vehicleData
                    .append(" ")
                    .append(vehicle.get(1).substring(19));

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
