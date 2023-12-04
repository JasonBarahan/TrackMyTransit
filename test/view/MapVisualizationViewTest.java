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
import javax.swing.plaf.metal.MetalComboBoxButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.fail;

/**
 * TODO: Convert to unit test
 */
public class MapVisualizationViewTest {
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

    // Pauses execution for a set number of milliseconds
    private void pause(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Checks if the button is present in MapVisualizationView.
     */
    @org.junit.Test
    public void checkButtonPresent() {
        VisualizeViewModel visualizeViewModel = executeController(createTwoMockTrains());
        JPanel panel = extractComboBoxPanel(visualizeViewModel);
        // get the button (no additional components found)
        JButton button = (JButton) panel.getComponent(0);

        // check that the button shows the expected text
        assert button.getText().equals("Fit markers to screen");
        // ...and it matches what is expected in the view model
        assert button.getText().equals(VisualizeViewModel.RESIZE_BUTTON_LABEL);
    }

    /**
     * Checks that the 'resize map to fit markers' function works as intended.
     */
    @org.junit.Test
    public void checkDisplayFitting() {
        VisualizeViewModel visualizeViewModel = executeController(createTwoMockTrains());
        JPanel panel = extractComboBoxPanel(visualizeViewModel);
        // get the button (no additional components found)
        JButton button = (JButton) panel.getComponent(0);

        MapVisualizationView mapVisualizationView = new MapVisualizationView(visualizeViewModel);
        mapVisualizationView.map.setDisplayPosition(new Coordinate(0, 0), 1);
        double oldLat = mapVisualizationView.map.getPosition().getLat();
        double oldLon = mapVisualizationView.map.getPosition().getLon();

        mapVisualizationView.map.setDisplayToFitMapMarkers();
        assert oldLat != mapVisualizationView.map.getPosition().getLat();
        assert oldLon != mapVisualizationView.map.getPosition().getLon();
    }

    /**
     * Checks that the 'resize map to fit markers' function works as intended.
     */
    @org.junit.Test
    public void checkDisplayFitting2() {
        VisualizeViewModel visualizeViewModel = executeController(createTwoMockTrains());
        JPanel panel = extractComboBoxPanel(visualizeViewModel);
        // get the button (no additional components found)
        JButton button = (JButton) panel.getComponent(0);

        MapVisualizationView mapVisualizationView = new MapVisualizationView(visualizeViewModel);
        mapVisualizationView.map.setDisplayPosition(new Coordinate(0, 0), 1);
        double oldLat = mapVisualizationView.map.getPosition().getLat();
        double oldLon = mapVisualizationView.map.getPosition().getLon();

        mapVisualizationView.map.setDisplayToFitMapMarkers();
        assert oldLat != mapVisualizationView.map.getPosition().getLat();
        assert oldLon != mapVisualizationView.map.getPosition().getLon();
    }

    /**
     * Checks if the JCheckbox to change the map tiles to satellite view is present.
     */
    @org.junit.Test
    public void checkSatelliteCheckboxPresent() {
        JPanel panel = extractComboBoxPanel(executeController(createTwoMockTrains()));

        // get the checkbox (no additional components found)
        JCheckBox checkBox = (JCheckBox) panel.getComponent(1);

        // check that the checkbox shows the expected text
        assert checkBox.getText().equals("Show satellite imagery");
        // ...and it matches what is expected in the view model
        assert checkBox.getText().equals(VisualizeViewModel.SHOW_SATELLITE_IMAGERY_CHECKBOX_LABEL);
    }

    /**
     * Checks if the JComboBox (vehicle selector) is present, and stores the correct coordinates associated with a
     * vehicle position. Also checks if key elements associated with the JComboBox are present.
     */
    @org.junit.Test
    public void checkJComboBox() {
        JPanel panel = extractComboBoxPanel(executeController(createTwoMockTrains()));

        JComboBox comboBox = (JComboBox) panel.getComponent(2);

        // check the combobox count matches the list of train objects found
        assert comboBox.getItemCount() == 2;

        // check the combobox items and ensure they match the expected indexes
        assert comboBox.getItemAt(0).toString().equals("Coordinate[43.341987, -79.807696]");
        assert comboBox.getItemAt(1).toString().equals("Coordinate[43.644805, -79.377704]");

        // check if present
//        MetalComboBoxButton metalComboBoxButton = (MetalComboBoxButton) comboBox.getComponent(0);
//        CellRendererPane cellRendererPane = (CellRendererPane) comboBox.getComponent(1);
//        assert metalComboBoxButton != null;
//        assert cellRendererPane != null;

        // check that renderer is an instance of custom renderer ("myRenderer")
        assert comboBox.getRenderer().toString().contains("myRenderer");
    }

    /**
     * Checks that selecting an in-service train repositions the map as intended.
     */
    @org.junit.Test
    public void checkMapFunctionality() {
        VisualizeViewModel visualizeViewModel = executeController(createTwoMockTrains());

        MapVisualizationView mapVisualizationView = new MapVisualizationView(visualizeViewModel);
        JRootPane pane = (JRootPane) mapVisualizationView.getComponent(0);
        JPanel panel = (JPanel) pane.getContentPane().getComponent(1);
        JPanel panel2 = (JPanel) panel.getComponent(0);
        JComboBox comboBox = (JComboBox) panel2.getComponent(2);

        // check that the coordinates have changed to where they are required
        ItemEvent e = new ItemEvent(comboBox, 2, new Coordinate(2, 3), ItemEvent.DESELECTED);
        comboBox.getItemListeners()[0].itemStateChanged(e);

        // since there is bound to be some discrepancy between the actual position and the desired position,
        // allow for a tolerance of 0.00001
        assert -0.00001 < mapVisualizationView.map.getPosition().getLat() - 2;
        assert mapVisualizationView.map.getPosition().getLat() - 2 < 0.00001;
        assert -0.00001 < mapVisualizationView.map.getPosition().getLon() - 3;
        assert mapVisualizationView.map.getPosition().getLon() - 3 < 0.00001;
    }

    /**
     * Checks that selecting an out-of-service train triggers the relevant popup.
     */
    @Test
    public void checkNotInServicePopupFunctions() {
        VisualizeViewModel visualizeViewModel = executeController(createTwoMockTrains());

        MapVisualizationView mapVisualizationView = new MapVisualizationView(visualizeViewModel);
        JRootPane pane = (JRootPane) mapVisualizationView.getComponent(0);
        JPanel panel = (JPanel) pane.getContentPane().getComponent(1);
        JPanel panel2 = (JPanel) panel.getComponent(0);
        JComboBox comboBox = (JComboBox) panel2.getComponent(2);

        // check that the coordinates have changed to where they are required
        ItemEvent e = new ItemEvent(comboBox, 2, new Coordinate(2, 3), ItemEvent.DESELECTED);
        comboBox.getItemListeners()[0].itemStateChanged(e);

        // now we check that the other branch is executed
        createCloseTimer().start();
        ItemEvent f = new ItemEvent(comboBox, 4, new Coordinate(-1, -1), ItemEvent.SELECTED);
        comboBox.getItemListeners()[0].itemStateChanged(f);
        assert mapVisualizationView.map.getPosition().getLat() != -1;
        assert mapVisualizationView.map.getPosition().getLon() != -1;
        assert popUpDiscovered;
    }

    /**
     * Checks that property event changes fired from OSM sources do not make the map visible.
     * This is because, when the map initially runs, the instantiation of OSM-related objects fire property changes.
     */
    @Test
    public void checkOSMSourcesDoNotTriggerMap() {
        MapVisualizationView mapVisualizationView = new MapVisualizationView(new VisualizeViewModel());
        VisualizeState state = executeController(createTwoMockTrains()).getVisualizationState();

        // check that org.openstreetmap sources do not trigger display
        PropertyChangeEvent evt1 = new PropertyChangeEvent("org.openstreetmap", "state", null, state);
        mapVisualizationView.propertyChange(evt1);
        assert !(mapVisualizationView.isActive());
    }

    /**
     * Checks that firing a change of state event to VisualizeViewModel triggers the display of MapVisualizationView.
     */
    @Test
    public void checkStateEventChangeTriggersMap() {
        MapVisualizationView mapVisualizationView = new MapVisualizationView(new VisualizeViewModel());
        VisualizeState state = executeController(createTwoMockTrains()).getVisualizationState();

        // check that firing a change of state event to VisualizeViewModel triggers the display of MapVisualizationView
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "state", null, state);
        mapVisualizationView.propertyChange(evt);

        // check that the map actually appears on the screen
        assert mapVisualizationView.getRootPane().isVisible();
    }
}
