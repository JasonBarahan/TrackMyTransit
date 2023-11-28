package view;

import entity.Train;
import interface_adapter.ViewManagerModel;
import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizePresenter;
import interface_adapter.visualize.VisualizeState;
import interface_adapter.visualize.VisualizeViewModel;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import resources.map.MapFont;
import use_case.visualize.VisualizeInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class MapVisualizationView extends JFrame implements PropertyChangeListener, ActionListener {
    // Internal strings
    private final String viewName = "visualize";
    private final VisualizeViewModel visualizeViewModel;
    private final VisualizeController visualizeController;
    // User guidance string
    private final String HELP_LABEL = "Hold the right mouse button while moving it to move the map. Double-click to zoom.";

    // JSwing elements
    private final JMapViewer map;

    private Coordinate c(double lat, double lon) {
        return new Coordinate(lat, lon);
    }

    // denote default style for markers
    private final Style defaultStyle = new Style(
            Color.cyan, new Color(245, 128, 37), new BasicStroke(10), new MapFont().getFont())  ;

    public MapVisualizationView(VisualizeViewModel visualizeViewModel, VisualizeController visualizeController) {
        this.visualizeViewModel = visualizeViewModel;
        visualizeViewModel.addPropertyChangeListener(this);

        this.visualizeController = visualizeController;
        // todo: debug
        visualizeController.execute(new Train[3]);

        // create the map object
        map = new JMapViewer();
        // TODO: check if this works
        map.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel helpPanel = new JPanel();

        // attach borders
        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        helpPanel.add(new JLabel(HELP_LABEL));

        // resize all markers to fit screen
        final JButton fitButton = new JButton("Fit markers to screen");
        fitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.setDisplayToFitMapMarkers();
            }
        });
        panelTop.add(fitButton);

        // checkbox to show satellite imagery
        final JCheckBox showImageryCheckBox = new JCheckBox("Show satellite imagery");
        showImageryCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showImageryCheckBox.isSelected())
                    map.setTileSource((new BingAerialTileSource()));
                else if (!(showImageryCheckBox.isSelected()))
                    map.setTileSource((new OsmTileSource.Mapnik()));
            }
        });
        panelTop.add(showImageryCheckBox);

        // add the actual map
        add(map, BorderLayout.CENTER);

        // detects mouse click events
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map.getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });

        // detects mouse movement to shift map accordingly
        map.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map.getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        // layer management (show buses and trains)
        LayerGroup vehicles = new LayerGroup("Vehicles");
        Layer trainsLayer = vehicles.addLayer("Trains");

        // vehicle selection screen
//        List<List<String>> stuff = visualizeViewModel.getVisualizationState().getData();
        test test = new test();
        List<List<String>> stuff = test.getData();

        List[] data = new ArrayList[stuff.size()];
        for (int i = 0; i < stuff.size(); i++) {
            data[i] = stuff.get(i);
        }

        JComboBox<List<String>> vehicleSelector;
        vehicleSelector = new JComboBox<>(data);
//        vehicleSelector.setRenderer(new myRenderer());      // add custom renderer
        vehicleSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<String> data = (List<String>) e.getItem();
                map.setDisplayPosition(c(parseDouble(data.get(2)), parseDouble(data.get(3))), map.getZoom());
            }
        });
        panelTop.add(vehicleSelector);

        // add markers
        for (List<String> vehicle : data) {
            String str = "[" + vehicle.get(0) + "] " + vehicle.get(1) + " - " + vehicle.get(4);
            MapMarkerDot marker = new MapMarkerDot(
                    trainsLayer,
                    str,
                    c(parseDouble(vehicle.get(2)), parseDouble(vehicle.get(3))),
                    this.defaultStyle
            );
            map.addMapMarker(marker);
        }

        // On initialization, map is focused on the first vehicle within the list
        // TODO: Implement properly
        List vehicle = data[0];
        map.setDisplayPosition(c(parseDouble((String) vehicle.get(2)), parseDouble((String) vehicle.get(3))), 13);
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
        VisualizeController controller = new VisualizeController(new VisualizeInteractor(new VisualizePresenter(new VisualizeViewModel(), new ViewManagerModel())));
        new MapVisualizationView(new VisualizeViewModel(), controller).setVisible(true);
    }
}
