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
        vehicle1.add("Lakeshore West");
        vehicle1.add("T");
        vehicle1.add("LW - Union Station");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("2023-11-27 16:31:00");
        vehicle1.add("1026");
        vehicle1.add("43.3419870");
        vehicle1.add("-79.8076960");

        List<String> vehicle2 = new ArrayList<>();
        vehicle2.add("LE");
        vehicle2.add("Lakeshore East");
        vehicle2.add("T");
        vehicle2.add("LE - Durham College Oshawa GO");
        vehicle2.add("2023-11-27 16:31:00");
        vehicle2.add("2023-11-27 16:35:33");
        vehicle2.add("1828");
        vehicle2.add("43.6448050");
        vehicle2.add("-79.3777040");

        inputData.add(vehicle1);
        inputData.add(vehicle2);
        controller.execute(inputData);

        // create the view
        MapVisualizationView mapVisualizationView = new MapVisualizationView(vm);
        mapVisualizationView.setVisible(true);

        // TODO: Check if all mock information aspects are present

    }

    public static void main(String[] args) {
        test2 test2 = new test2();
    }
}
