package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeState;
import interface_adapter.visualize.VisualizeViewModel;
import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import use_case.visualize.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Interactor tests for the visualize use case
 */
public class VisualizeInteractorTest {
    static boolean popUpDiscovered;
    static String message;
    // Dialog checking mechanism. Courtesy Paul Gries
    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {

                    if (window instanceof JDialog) {

                        JDialog dialog = (JDialog)window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
                            System.out.println("A dialog box was opened. Message: " + s);

                            // store the information we got from the JDialog
                            MapVisualizationViewTest.message = s;
                            MapVisualizationViewTest.popUpDiscovered = true;

                            System.out.println("disposing of " + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }

        };

        Timer t = new Timer(1000, close);
        t.setRepeats(false);
        return t;
    }

    // Creates input data containing a null train.
    private List<List<String>> createNullTrain() {
        List<List<String>> inputData = new ArrayList<>();

        List<String> vehicle1 = new ArrayList<>();
        vehicle1.add("ST");
        vehicle1.add("Vehicle Route Name: ST - Old Elm GO");
        vehicle1.add("Vehicle Latitude: -1.0");
        vehicle1.add("Vehicle Longitude: -1.0");
        vehicle1.add("");
        vehicle1.add("2023-11-17 16:41:00");
        vehicle1.add("");
        vehicle1.add("2023-11-17 16:42:00");

        inputData.add(vehicle1);
        return inputData;
    }

    // Creates input data containing one in-service train.
    private List<List<String>> createOneMockTrain() {
        List<List<String>> inputData = new ArrayList<>();

        List<String> vehicle1 = new ArrayList<>();
        vehicle1.add("LW");
        vehicle1.add("Vehicle Route Name: LW - Union Station");
        vehicle1.add("Vehicle Latitude: 43.3419870");
        vehicle1.add("Vehicle Longitude: -79.8076960");
        vehicle1.add("");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("");
        vehicle1.add("2023-11-27 16:31:00");

        inputData.add(vehicle1);
        return inputData;
    }

    // Creates input data containing two in-service trains.
    private List<List<String>> createTwoMockTrains() {
        List<List<String>> inputData = new ArrayList<>();

        List<String> vehicle1 = new ArrayList<>();
        vehicle1.add("LW");
        vehicle1.add("Vehicle Route Name: LW - Union Station");
        vehicle1.add("Vehicle Latitude: 43.3419870");
        vehicle1.add("Vehicle Longitude: -79.8076960");
        vehicle1.add("");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("");
        vehicle1.add("2023-11-27 16:31:00");

        List<String> vehicle2 = new ArrayList<>();
        vehicle2.add("LE");
        vehicle2.add("Vehicle Route Name: LE - Durham College Oshawa GO");
        vehicle2.add("Vehicle Latitude: 43.6448050");
        vehicle2.add("Vehicle Longitude: -79.3777040");
        vehicle2.add("");
        vehicle2.add("2023-11-27 16:31:00");
        vehicle2.add("");
        vehicle2.add("2023-11-27 16:35:33");


        inputData.add(vehicle1);
        inputData.add(vehicle2);
        return inputData;
    }

    // Returns the view model instance.
    private VisualizeViewModel executeController(List<List<String>> input) {
        VisualizeViewModel visualizeViewModel = new VisualizeViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        VisualizePresenter visualizePresenter = new VisualizePresenter(visualizeViewModel, viewManagerModel);
        VisualizeInteractor visualizeInteractor = new VisualizeInteractor(visualizePresenter);
        VisualizeController controller = new VisualizeController(visualizeInteractor);

        controller.execute(input);
        return visualizeViewModel;
    }

    // Extracts the combo box panel from MapVisualizationView.
    private JPanel extractComboBoxPanel(VisualizeViewModel visualizeViewModel) {
        MapVisualizationView mapVisualizationView = new MapVisualizationView(visualizeViewModel);
        JRootPane pane = (JRootPane) mapVisualizationView.getComponent(0);
        JPanel panel = (JPanel) pane.getContentPane().getComponent(1);
        JPanel panel2 = (JPanel) panel.getComponent(0);
        for (int i = 0; i < panel2.getComponentCount(); i++) {
            System.out.println(panel2.getComponent(i));
        }
        return panel2;
    }

    /**
     * Checks that, upon reaching the presenter, the desired state data is correct.
     */
    @org.junit.Test
    public void testPresenterSuccessView() {
        // Generate input data
        VisualizeInputData visualizeInputData = new VisualizeInputData(createOneMockTrain());

        // create a custom presenter whose success view checks that it works as anticipated
        VisualizeOutputBoundary visualizeOutputBoundary = new VisualizeOutputBoundary() {
            @Override
            public void prepareSuccessView(VisualizeOutputData data) {
                // coordinate checks
                assert data.getCoordinateData().get(0).getLat() == 43.3419870;
                assert data.getCoordinateData().get(0).getLon() == -79.8076960;

                // string metadata checks
                assert data.getStringData().get(0).equals(
                        "[16:31:00]  LW - Union Station");

                // size check
                assert data.getSize() == 1;
                assert data.getCoordinateData().size() == 1;
                assert data.getStringData().size() == 1;
            }

            @Override
            public void prepareFailView(String error) {
                fail("Not supposed to happen");
            }
        };
        // execute the interactor to test mock output boundary file
        VisualizeInputBoundary visualizeInteractor = new VisualizeInteractor(visualizeOutputBoundary);
        visualizeInteractor.execute(visualizeInputData);
    }

    /**
     * Checks that the state error is null if the success view is triggered.
     */
    @org.junit.Test
    public void testStateErrorIsNullOnInstantiation() {
        VisualizeState state = new VisualizeState();
        assert state.getErrorString() == null;
    }

    /**
     * Checks that input data is not modified before reaching the Controller.
     */
    @Test
    public void testController() {
        // create expected input data ( this is the exact same data from CreateTwoMockTrains() )
        List<List<String>> expectedInputData = new ArrayList<>();

        List<String> vehicle1 = new ArrayList<>();
        vehicle1.add("LW");
        vehicle1.add("Vehicle Route Name: LW - Union Station");
        vehicle1.add("Vehicle Latitude: 43.3419870");
        vehicle1.add("Vehicle Longitude: -79.8076960");
        vehicle1.add("");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("");
        vehicle1.add("2023-11-27 16:31:00");

        List<String> vehicle2 = new ArrayList<>();
        vehicle2.add("LE");
        vehicle2.add("Vehicle Route Name: LE - Durham College Oshawa GO");
        vehicle2.add("Vehicle Latitude: 43.6448050");
        vehicle2.add("Vehicle Longitude: -79.3777040");
        vehicle2.add("");
        vehicle2.add("2023-11-27 16:31:00");
        vehicle2.add("");
        vehicle2.add("2023-11-27 16:35:33");


        expectedInputData.add(vehicle1);
        expectedInputData.add(vehicle2);

        // create mock input boundary
        VisualizeInputBoundary visualizeInteractor = new VisualizeInputBoundary() {
            @Override
            public void execute(VisualizeInputData visualizeInputData) {
                assert visualizeInputData.getVehicleData().equals(expectedInputData);
            }
        };

        // check that executing this will trigger a match
        VisualizeController controller = new VisualizeController(visualizeInteractor);
        controller.execute(createTwoMockTrains());
    }
    /**
     * Checks that state data as retrieved from the view model returns accurate results for a list containing
     * two in-service trains.
     */
    @org.junit.Test
    public void testInteractorTwoTrains() {
        // execute the controller, pass through all layers, return the view model
        VisualizeViewModel visualizeViewModel = executeController(createTwoMockTrains());

        // get the state
        VisualizeState visualizeState = visualizeViewModel.getVisualizationState();

        // coordinate checks
        assert visualizeState.getCoordinateList().get(0).getLat() == 43.3419870;
        assert visualizeState.getCoordinateList().get(0).getLon() == -79.8076960;
        assert visualizeState.getCoordinateList().get(1).getLat() == 43.6448050;
        assert visualizeState.getCoordinateList().get(1).getLon() == -79.3777040;

        // string metadata checks
        assert visualizeState.getVehicleInformationList().get(0).equals(
                "[16:31:00]  LW - Union Station");
        assert visualizeState.getVehicleInformationList().get(1).equals(
                "[16:31:00, estimated 16:35:33]  LE - Durham College Oshawa GO"
        );

        // size check
        assert visualizeState.getVehicleInformationSize() == 2;
        assert visualizeState.getCoordinateList().size() == 2;
        assert visualizeState.getVehicleInformationList().size() == 2;
    }

    /**
     * Checks that state data as retrieved from the view model returns accurate results for a list containing
     * one in-service train.
     */
    @org.junit.Test
    public void testInteractorOneTrain() {
        // execute the controller, pass through all layers, return the view model
        VisualizeViewModel visualizeViewModel = executeController(createOneMockTrain());

        // get the state
        VisualizeState visualizeState = visualizeViewModel.getVisualizationState();

        // coordinate checks
        assert visualizeState.getCoordinateList().get(0).getLat() == 43.3419870;
        assert visualizeState.getCoordinateList().get(0).getLon() == -79.8076960;

        // string metadata checks
        assert visualizeState.getVehicleInformationList().get(0).equals(
                "[16:31:00]  LW - Union Station");

        // size check
        assert visualizeState.getVehicleInformationSize() == 1;
        assert visualizeState.getCoordinateList().size() == 1;
        assert visualizeState.getVehicleInformationList().size() == 1;
    }

    /**
     * Checks that state data as retrieved from the view model returns accurate results for a list containing
     * one out-of-service train.
     */
    @org.junit.Test
    public void testInteractorNull() {
        // execute the controller, pass through all layers, return the view model
        VisualizeViewModel visualizeViewModel = executeController(createNullTrain());

        // get the state
        VisualizeState visualizeState = visualizeViewModel.getVisualizationState();

        // coordinate checks
        assert visualizeState.getCoordinateList().get(0).getLat() == -1.0;
        assert visualizeState.getCoordinateList().get(0).getLon() == -1.0;

        // string metadata checks
        assert visualizeState.getVehicleInformationList().get(0).equals(
                "[16:41:00, currently not in service]  ST - Old Elm GO");

        // size check
        assert visualizeState.getVehicleInformationSize() == 1;
        assert visualizeState.getCoordinateList().size() == 1;
        assert visualizeState.getVehicleInformationList().size() == 1;
    }

    /**
     * Checks if state setters and getters work correctly.
     */
    @org.junit.Test
    public void checkVisualizeViewModelSetState() {
        VisualizeViewModel visualizeViewModel = new VisualizeViewModel();
        VisualizeState state = new VisualizeState();

        List<Coordinate> testCoordinateList = new ArrayList<>();
        testCoordinateList.add(new Coordinate(-1, -1));
        state.setCoordinateList(testCoordinateList);

        List<String> testStringList = new ArrayList<>();
        testStringList.add("a");
        state.setVehicleInformationList(testStringList);

        state.setVehicleInformationSize(1);

        visualizeViewModel.setVisualizationState(state);
        assert visualizeViewModel.getVisualizationState().getCoordinateList().get(0).getLon() == -1;
        assert visualizeViewModel.getVisualizationState().getCoordinateList().get(0).getLat() == -1;
        assert visualizeViewModel.getVisualizationState().getVehicleInformationList().get(0).equals("a");
        assert visualizeViewModel.getVisualizationState().getVehicleInformationSize() == 1;
    }
}
