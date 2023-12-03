package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeState;
import interface_adapter.visualize.VisualizeViewModel;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import use_case.visualize.VisualizeInteractor;

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
    private VisualizeViewModel executeController(List<List<String>> input) {
        VisualizeViewModel visualizeViewModel = new VisualizeViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        VisualizePresenter visualizePresenter = new VisualizePresenter(visualizeViewModel, viewManagerModel);
        VisualizeInteractor visualizeInteractor = new VisualizeInteractor(visualizePresenter);
        VisualizeController controller = new VisualizeController(visualizeInteractor);

        controller.execute(input);
        return visualizeViewModel;
    }

    @org.junit.Test
    public void testStateErrorIsNullOnInstantiation() {
        VisualizeState state = new VisualizeState();
        assert state.getErrorString() == null;
    }
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

    @org.junit.Test
    public void checkButtonFunctions() {
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

    @org.junit.Test
    public void checkItemStateChangeFires() {
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
        MetalComboBoxButton metalComboBoxButton = (MetalComboBoxButton) comboBox.getComponent(0);
        CellRendererPane cellRendererPane = (CellRendererPane) comboBox.getComponent(1);
        assert metalComboBoxButton != null;
        assert cellRendererPane != null;

        // check that renderer is an instance of custom renderer ("myRenderer")
        assert comboBox.getRenderer().toString().contains("myRenderer");
    }

    @org.junit.Test
    public void checkMapFunctionality() {
        VisualizeViewModel visualizeViewModel = new VisualizeViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        VisualizePresenter visualizePresenter = new VisualizePresenter(visualizeViewModel, viewManagerModel);
        VisualizeInteractor visualizeInteractor = new VisualizeInteractor(visualizePresenter);
        VisualizeController controller = new VisualizeController(visualizeInteractor);

        // execute the controller
        controller.execute(createTwoMockTrains());

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

        // now we check that the other branch is executed
        createCloseTimer().start();
        ItemEvent f = new ItemEvent(comboBox, 4, new Coordinate(-1, -1), ItemEvent.SELECTED);
        comboBox.getItemListeners()[0].itemStateChanged(f);
        assert mapVisualizationView.map.getPosition().getLat() != -1;
        assert mapVisualizationView.map.getPosition().getLon() != -1;
        assert popUpDiscovered;

        checkMapDoesNotAppear();
        checkMapAppears();
    }

    public void checkMapDoesNotAppear() {
        MapVisualizationView mapVisualizationView = new MapVisualizationView(new VisualizeViewModel());
        VisualizeState state = executeController(createTwoMockTrains()).getVisualizationState();

        // check that org.openstreetmap sources do not trigger display
        PropertyChangeEvent evt1 = new PropertyChangeEvent("org.openstreetmap", "state", null, state);
        mapVisualizationView.propertyChange(evt1);
        assert !(mapVisualizationView.isActive());
    }

    public void checkMapAppears() {
        MapVisualizationView mapVisualizationView = new MapVisualizationView(new VisualizeViewModel());
        VisualizeState state = executeController(createTwoMockTrains()).getVisualizationState();

        // check that firing a change of state event to VisualizeViewModel triggers the display of MapVisualizationView
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "state", null, state);
        mapVisualizationView.propertyChange(evt);

        // check that the map actually appears on the screen
        assert mapVisualizationView.getRootPane().isVisible();
    }
}
