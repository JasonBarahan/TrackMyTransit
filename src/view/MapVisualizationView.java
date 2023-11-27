package view;

import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeViewModel;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import use_case.visualize.VisualizeInteractor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MapVisualizationView extends JPanel implements PropertyChangeListener, ActionListener {
    // Internal strings
    private final String viewName = "visualize";
    private final VisualizeViewModel visualizeViewModel;
    private final VisualizeController visualizeController;
    private final JMapViewer map;

    public MapVisualizationView(VisualizeViewModel visualizeViewModel, VisualizeController visualizeController) {
        this.visualizeViewModel = visualizeViewModel;
        visualizeViewModel.addPropertyChangeListener(this);

        this.visualizeController = visualizeController;

        // create the map object
        map = new JMapViewer();


    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Evoked" + e.getActionCommand());
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    // TODO: Debug, remove when done
    public static void main(String[] args) {
        new MapVisualizationView(null, null).setVisible(true);
    }

}
