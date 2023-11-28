package view;

import interface_adapter.visualize.VisualizeViewModel;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import resources.map.MapFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class MapVisualizationView extends JFrame implements PropertyChangeListener, ActionListener {
    /* Internal strings */
    private final String viewName = "visualize";
    private final VisualizeViewModel visualizeViewModel;

    /*
     I got rid of the controller since no entity/external data needs to be retrieved or modified once this view
     is displayed. This will change should a refresh button be implemented.

     In regard to a refresh button, query data would need to be resent again through the APi (i.e. call
     ShowIncomingVehicles use case files, then route the output through MapVisualization use case files)
     so not too sure about how to handle this. Will discuss with team.
    */

    /* JSwing elements */
    private final JMapViewer map;

    private Coordinate c(double lat, double lon) {
        return new Coordinate(lat, lon);
    }

    private Coordinate c(String lat, String lon) {
        return new Coordinate(Double.parseDouble(lat), Double.parseDouble(lon));
    }

    // denote default style for markers
    // TODO: Depending on parent line info, change the color of the inner point
    private final Style defaultStyle = new Style(
            Color.cyan, new Color(245, 128, 37), new BasicStroke(10), new MapFont().getFont())  ;

    // map keys in output data to keywords for clarity
    private String retrieveData(List<String> data, String str) {
        String val = null;
        switch(str.toLowerCase()) {
            case "lat":
                val = data.get(0);
                break;
            case "lon":
                val = data.get(1);
                break;
            case "infostring":
                val = data.get(2);
                break;
        }
        return val;
    }

    public MapVisualizationView(VisualizeViewModel visualizeViewModel) {
        this.visualizeViewModel = visualizeViewModel;
        visualizeViewModel.addPropertyChangeListener(this);

        // create the map object
        map = new JMapViewer();
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
        helpPanel.add(new JLabel(VisualizeViewModel.HELP_LABEL));

        // resize all markers to fit screen
        final JButton fitButton = new JButton(VisualizeViewModel.RESIZE_BUTTON_LABEL);
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

        List<String>[] data = new ArrayList[stuff.size()];
        for (int i = 0; i < stuff.size(); i++) {
            data[i] = stuff.get(i);
        }

        JComboBox<List<String>> vehicleSelector = new JComboBox<>(data);
        vehicleSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<String> data = (List<String>) e.getItem();
                map.setDisplayPosition(c(retrieveData(data, "lat"), retrieveData(data, "lon")), map.getZoom());
            }
        });
        panelTop.add(vehicleSelector);

        // add markers
        for (List<String> vehicle : data) {
            MapMarkerDot marker = new MapMarkerDot(
                    trainsLayer,
                    retrieveData(vehicle, "infoString"),
                    c(retrieveData(vehicle, "lat"), retrieveData(vehicle, "lon")),
                    this.defaultStyle
            );
            map.addMapMarker(marker);
        }

        // On initialization, map is focused on the first vehicle within the list
        // TODO: Implement properly
        // TODO: What happens if there are no vehicles in the list? Throw an error?
        List<String> vehicle = data[0];
        map.setDisplayPosition(c(retrieveData(vehicle, "lat"), retrieveData(vehicle, "lon")), 13);
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
        new MapVisualizationView(new VisualizeViewModel()).setVisible(true);
    }
}
