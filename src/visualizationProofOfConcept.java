import entity.Train;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import resources.map.MapFont;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;

import static java.awt.font.TextAttribute.*;

// TODO: JMapViewer is not listed on Maven. I added the .jar file manually after downloading it from
// TODO: the repository Jonathan Calver linked. You may need to add this dependency yourself.
// TODO: Try manually adding a local dependency to the pom.xml file so Maven recognizes it (yet to try this)

/**
 * Proof of concept for map-based visualization of vehicle positioning.
 *
 * @author Jan Peter Stotz (original file)
 * Modified by Jason Barahan
 */
public class visualizationProofOfConcept extends JFrame implements JMapViewerEventListener {
    private final JMapViewer treeMap;

    private final JLabel zoomLabel;
    private final JLabel zoomValue;

    private final JLabel mperpLabelName;
    private final JLabel mperpLabelValue;

    // TODO: Make different types for trains? Buses? Have the colour change based on the line the vehicle is operating?
    private final Style defaultStyle = new Style(
            Color.cyan, new Color(245, 128, 37), new BasicStroke(10), new MapFont().getFont())  ;

    public visualizationProofOfConcept() {
        super("TrackMyTransit");
        setSize(50, 50);

//        treeMap = new JMapViewerTree("Vehicles");
        treeMap = new JMapViewer();

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

        // meters per pixels data displayed on top of window
        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));

        // zoom value displayed on top of window
        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        // usage instructions added below
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
        panelBottom.add(button);

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
        final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
        showToolTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setToolTipText("a");
            }
        });
        panelBottom.add(showToolTip);

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
        // TODO: this is temporary. We'll depend on API calls.
        Train[] trains = new Train[]{
                new Train(653, 43.871004, -78.884807, "LE", "Durham College Oshawa GO"),
                new Train(644, 43.765518,-79.364275, "RH", "Bloomington GO"),
                new Train(558, 43.733045,-79.262706, "ST", "Mount Joy GO"),
                // these are actually buses, their IDs are length 4
                new Train(8551,43.666071, -79.356378, "65", "Union Station"),
                new Train(8427,43.668853, -79.357046, "65E", "Aurora GO"),
                new Train(2580, 43.765939, -79.364240, "94", "Pickering GO")
        };

        // vehicle selection screen
        // TODO: have route data be displayed within the combo box instead
        JComboBox<Train> vehicleSelector;
        vehicleSelector = new JComboBox<>(trains);
        vehicleSelector.setRenderer(new myRenderer());      // add custom renderer
        vehicleSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Train data = (Train) e.getItem();
                map().setDisplayPosition(c(data.getLatitude(), data.getLongitude()), map().getZoom());
            }
        });
        panelTop.add(vehicleSelector);

        // layer management (show buses and trains)
        LayerGroup vehicles = new LayerGroup("Vehicles");
        Layer trainsLayer = vehicles.addLayer("Trains");

        // add vehicles to map
        //
        // TODO: Read me
        // - Each marker denotes a vehicle (train/bus).
        // - The "style" of each marker has been modified to take an rgb value. This allows for vehicles to
        //   be coloured according to their parent line, and should be relatively easy to implement
        //   by usage of (yet another) file.
        // - The outline of each marker can be modified according to their adherence to scheduled departure time.
        //   It shows red if late (dark red if very late) and green if early/on time (dark green if very early).
        //   This can be implemented with API calls.
        // - Right now the text associated with the marker is the vehicle ID. If I recall correctly most information
        //   will be displayed separately from the map. Adding this information to the map (proper) may be unnecessary.
        // - TODO: I will read the documentation to see if hovering over the marker to display additional information
        //   TODO: is possible. (If not, I may need to modify the code itself if necessary.)
        //   Implementation notes: set a small radius around each vehicle marker and have it so, if the cursor is
        //   within the marker's radius, additional details (like direction, destination, exact lateness) is
        //   displayed on the tooltip.
        // - TODO: Fix the text so it becomes legible (or not include it at all, use a tooltip)
        //   Implementation notes: black background (or background depends on lateness), white font colour.
        // - TODO: How to handle refresh calls?
        //
        // The below for loop access entities directly, so this violates CA. Will need to modify accordingly.
        // TODO: Have the interactor make map points instead of calling APIs directly
        for (Train train : trains) {
            String str = new String(
                    "[" + train.getVehicleID() + "] " + train.getRouteName() + " to " + train.getRouteDestination()
                    + " - On Time"
            );
            MapMarkerDot marker = new MapMarkerDot(
                    trainsLayer,
                    str,
                    c(train.getLatitude(), train.getLongitude()),
                    defaultStyle
            );
            map().addMapMarker(marker);
        }

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

        // On initialization, map is focused on the first vehicle within the list
        map().setDisplayPosition(c(trains[0].getLatitude(), trains[0].getLongitude()), 13);
    }

    private JMapViewer map() {
        return treeMap;
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
    public void processCommand(JMVCommandEvent jmvCommandEvent) {}

    public static void main(String[] args) {
        new visualizationProofOfConcept().setVisible(true);
    }
}

// Creating a custom renderer which overrides getListCellRendererComponent to change displayed strings in tje
// jComboBox.
class myRenderer extends DefaultListCellRenderer {

    /**
     * Return a component that has been configured to display the specified
     * value. That component's <code>paint</code> method is then called to
     * "render" the cell.  If it is necessary to compute the dimensions
     * of a list because the list cells do not have a fixed size, this method
     * is called to generate a component on which <code>getPreferredSize</code>
     * can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // TODO: Fix handling for bus objects. Here we've type casted the value to display in the list.
        Train train = (Train) value;

        // if cell selected
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }

        // if cell not selected
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        // set text displayed
        setIcon(null);
        setText("[" + train.getVehicleID() + "] " + train.getRouteName() + " to " + train.getRouteDestination());

        setEnabled(list.isEnabled());
        setFont(list.getFont());
        return this;
    }
}