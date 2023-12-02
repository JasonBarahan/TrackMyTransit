package view;

import app.Main;
import interface_adapter.ViewManagerModel;
import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeViewModel;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import use_case.visualize.VisualizeInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Convert to unit test
 */
public class test2 {
    public test2() {
        VisualizeViewModel vm = new VisualizeViewModel();
        VisualizeController controller = new VisualizeController(new VisualizeInteractor(new VisualizePresenter(vm, new ViewManagerModel())));
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
        controller.execute(inputData);

        // create the view (noting the controller is ignored in our current implementation)
        MapVisualizationView mapVisualizationView = new MapVisualizationView(vm);
        mapVisualizationView.setVisible(true);

        // TODO: Check if all mock information aspects are present

    }

    public static void main(String[] args) {
        test2 test2 = new test2();
    }
}
