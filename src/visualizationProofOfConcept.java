import entity.Train;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Proof of concept for map-based visualization of vehicle positioning.
 *
 * @author Jan Peter Stotz (original file)
 * Modified by Jason Barahan
 */
public class visualizationProofOfConcept extends JFrame implements JMapViewerEventListener {
    private final JMapViewerTree treeMap;

    private final JLabel zoomLabel;
    private final JLabel zoomValue;

    private final JLabel mperpLabelName;
    private final JLabel mperpLabelValue;

    public visualizationProofOfConcept() {
        super("JMapViewer Demo");
        setSize(100, 100);

        treeMap = new JMapViewerTree("Zones");

        // Listen to the map viewer for user operations so components will
        // receive events and update
        map().addJMVListener(this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));

        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use left mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);

        // Resize screen function (to fit all map markers on screen)
        JButton button = new JButton("Resize screen");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapMarkers();
            }
        });

        // a mechanism to change the satellite imagery provider. Replaced by a checkbox (can change to button).
        // Not needed, kept here for reference.
        // TODO: Delete
//        JComboBox<TileSource> tileSourceSelector = new JComboBox<>(new TileSource[] {
//                new OsmTileSource.Mapnik(),
//                new BingAerialTileSource()
//        });
//        tileSourceSelector.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                map().setTileSource((TileSource) e.getItem());
//            }
//        });

        // A JComboBox for choosing the tile loading mechanism.
        // Not needed, kept here for reference purposes.
        // TODO: Delete
//        JComboBox<TileLoader> tileLoaderSelector;
//        tileLoaderSelector = new JComboBox<>(new TileLoader[] {new OsmTileLoader(map())});
//        tileLoaderSelector.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                map().setTileLoader((TileLoader) e.getItem());
//            }
//        });
//        map().setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
//        panelTop.add(tileSourceSelector);
//        panelTop.add(tileLoaderSelector);

        // Makes map markers visible.
        // This shouldn't be an option - might delete. (Kept here for reference.)
        // TODO: Delete
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map().getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
        panelBottom.add(showMapMarker);

        // Switches between satellite imagery and regular map imagery.
        // TODO: Switch to button?
        final JCheckBox enableSatelliteImagery = new JCheckBox("Enable satellite imagery");
        enableSatelliteImagery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enableSatelliteImagery.isSelected())
                    map().setTileSource((new BingAerialTileSource()));
                else if (!(enableSatelliteImagery.isSelected()))
                    map().setTileSource((new OsmTileSource.Mapnik()));
            }
        });
        panelBottom.add(enableSatelliteImagery);

        // Tooltips which display text near cursor when hovering.
        // May use this to display bus information.
        // Problem: not friendly for mobile/tablet/touchscreen users.
//        final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
//        showToolTip.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                map().setToolTipText(null);
//            }
//        });
//        panelBottom.add(showToolTip);

        ///

        // Shows zoom controls.
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map().getZoomControlsVisible());
        showZoomControls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        panelBottom.add(showZoomControls);

        // add details to the top of the window
        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);

        add(treeMap, BorderLayout.CENTER);

        // Generate Train objects
        Train[] trains = new Train[]{
                new Train(653, 43.871004, -78.884807),
                new Train(644, 43.765518,-79.364275),
                new Train(558, 43.733045,-79.262706)
        };

        // layer management (show buses and trains)
        LayerGroup vehicles = new LayerGroup("Vehicles");
        Layer trainsLayer = vehicles.addLayer("Trains");

        for (Train train : trains) {
            MapMarkerDot marker = new MapMarkerDot(
                    trainsLayer,
                    String.valueOf(train.getVehicleID()),
                    train.getLatitude(),
                    train.getLongitude()
            );
            map().addMapMarker(marker);
        }

        LayerGroup germanyGroup = new LayerGroup("Germany");
        Layer germanyWestLayer = germanyGroup.addLayer("Germany West");
        Layer germanyEastLayer = germanyGroup.addLayer("Germany East");
        MapMarkerDot eberstadt = new MapMarkerDot(germanyEastLayer, "Eberstadt", 49.814284999, 8.642065999);
        MapMarkerDot ebersheim = new MapMarkerDot(germanyWestLayer, "Ebersheim", 49.91, 8.24);
        MapMarkerDot empty = new MapMarkerDot(germanyEastLayer, 49.71, 8.64);
        MapMarkerDot darmstadt = new MapMarkerDot(germanyEastLayer, "Darmstadt", 49.8588, 8.643);
        map().addMapMarker(eberstadt);
        map().addMapMarker(ebersheim);
        map().addMapMarker(empty);
        Layer franceLayer = treeMap.addLayer("France");
        map().addMapMarker(new MapMarkerDot(franceLayer, "La Gallerie", 48.71, -1));
        map().addMapMarker(new MapMarkerDot(43.604, 1.444));
        map().addMapMarker(new MapMarkerCircle(53.343, -6.267, 0.666));
        map().addMapRectangle(new MapRectangleImpl(new Coordinate(53.343, -6.267), new Coordinate(43.604, 1.444)));
        map().addMapMarker(darmstadt);
        treeMap.addLayer(germanyWestLayer);
        treeMap.addLayer(germanyEastLayer);

        MapPolygon bermudas = new MapPolygonImpl(c(49, 1), c(45, 10), c(40, 5));
        map().addMapPolygon(bermudas);
        map().addMapPolygon(new MapPolygonImpl(germanyEastLayer, "Riedstadt", ebersheim, darmstadt, eberstadt, empty));

        map().addMapMarker(new MapMarkerCircle(germanyWestLayer, "North of Suisse", new Coordinate(48, 7), .5));
        Layer spain = treeMap.addLayer("Spain");
        map().addMapMarker(new MapMarkerCircle(spain, "La Garena", new Coordinate(40.4838, -3.39), .002));
        spain.setVisible(Boolean.FALSE);

        Layer wales = treeMap.addLayer("UK");
        map().addMapRectangle(new MapRectangleImpl(wales, "Wales", c(53.35, -4.57), c(51.64, -2.63)));

        // detects mouse click events
        map().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map().getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });

        // detects mouse movement to shift map accordingly
        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                // TODO: consider if tooltips are useful, then modify the line below for intended purpose
//                if (showToolTip.isSelected()) map().setToolTipText(map().getPosition(p).toString());
            }
        });
    }

    private JMapViewer map() {
        return treeMap.getViewer();
    }

    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%s", map().getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }

    private static Coordinate c(double lat, double lon) {
        return new Coordinate(lat, lon);
    }

    @Override
    public void processCommand(JMVCommandEvent jmvCommandEvent) {
        if (jmvCommandEvent.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                jmvCommandEvent.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }

    public static void main(String[] args) {
        new visualizationProofOfConcept().setVisible(true);
    }
}
