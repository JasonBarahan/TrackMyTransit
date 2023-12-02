package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeViewModel;
import use_case.visualize.VisualizeInteractor;

import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxButton;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Convert to unit test
 */
public class MapVisualizationViewTest {
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
    public MapVisualizationViewTest() {
        VisualizeViewModel visualizeViewModel = new VisualizeViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        VisualizePresenter visualizePresenter = new VisualizePresenter(visualizeViewModel, viewManagerModel);
        VisualizeInteractor visualizeInteractor = new VisualizeInteractor(visualizePresenter);
        VisualizeController controller = new VisualizeController(visualizeInteractor);

        // execute the controller
//        controller.execute(inputData);

        // create the view (noting the controller is ignored in our current implementation)
        MapVisualizationView mapVisualizationView = new MapVisualizationView(visualizeViewModel);
        mapVisualizationView.setVisible(true);

        // TODO: Check if all mock information aspects are present
        JRootPane pane = (JRootPane) mapVisualizationView.getComponent(0);

        JPanel panel = (JPanel) pane.getContentPane().getComponent(0);
        JPanel panel2 = (JPanel) panel.getComponent(0);

        // get the button (no additional components found)
        JButton button = (JButton) panel2.getComponent(0);
        System.out.println(button.getComponentCount());
        // TODO: test if clicking the button changes something

        // get the checkbox (no additional components found)
        JCheckBox checkBox = (JCheckBox) panel2.getComponent(1);
        System.out.println(checkBox.getComponentCount());
        // TODO: test if clicking the checkbox changes something

        // get the combobox
        JComboBox comboBox = (JComboBox) panel2.getComponent(2);
        // TODO: more tests

        // check the combobox count matches the list of train objects found
        assert comboBox.getItemCount() == 2;

        // check the combobox items
        System.out.println(comboBox.getItemAt(1));

        MetalComboBoxButton metalComboBoxButton = (MetalComboBoxButton) comboBox.getComponent(0);
        CellRendererPane cellRendererPane = (CellRendererPane) comboBox.getComponent(1);
        System.out.println(comboBox.getRenderer().toString());

        // check that the last vehicle loaded contains one of the expected outputs
        assert comboBox.getRenderer().toString().contains("[16:31:00, estimated 16:35:33]  LE - Durham College Oshawa GO");


    }

    public static void main(String[] args) {
        MapVisualizationViewTest MapVisualizationViewTest = new MapVisualizationViewTest();
    }
}
