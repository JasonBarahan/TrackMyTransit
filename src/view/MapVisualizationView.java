package view;

import interface_adapter.visualize.VisualizeState;
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


public class MapVisualizationView extends JFrame implements PropertyChangeListener, ActionListener {
    /* Internal strings */
    public final String viewName = "visualize";
    private final VisualizeViewModel visualizeViewModel;

    /*
     There is no controller since no entity/external data needs to be retrieved or modified once this view
     is displayed + this is a popup. This will change should a refresh button be implemented.

     In regard to a refresh button, query data would need to be resent again through the API (i.e. call
     ShowIncomingVehicles use case files, then route the output through MapVisualization use case files)
     so not too sure about how to handle this. Will discuss with team.
    */

    /* JSwing elements */
    private final JMapViewer map;

    JPanel panel;
    JPanel panelTop;
    JPanel helpPanel;

    // denote default style for markers
    private final Style defaultStyle = new Style(
            Color.cyan, new Color(245, 128, 37), new BasicStroke(10), new MapFont().getFont());

    public MapVisualizationView(VisualizeViewModel visualizeViewModel) {
            this.visualizeViewModel = visualizeViewModel;
            visualizeViewModel.addPropertyChangeListener(this);

            // create the map object
            map = new JMapViewer();
            map.addPropertyChangeListener(this);

            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            panel = new JPanel(new BorderLayout());
            panelTop = new JPanel();
            helpPanel = new JPanel();

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
        VisualizeState vehicleData = visualizeViewModel.getVisualizationState();

        JComboBox<Coordinate> vehicleSelector =
                new JComboBox<>(vehicleData.getCoordinateList().toArray(new Coordinate[vehicleData.getVehicleInformationSize()]));
        vehicleSelector.setRenderer(new myRenderer());      // add custom renderer
        vehicleSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Coordinate coordinate = (Coordinate) e.getItem();
                map.setDisplayPosition(coordinate, map.getZoom());
            }
        });
        panelTop.add(vehicleSelector);

        // add markers
        for (int i = 0; i < vehicleData.getVehicleInformationSize(); i++) {
            MapMarkerDot marker = new MapMarkerDot(
                    trainsLayer,
                    vehicleData.getVehicleInformationList().get(i),
                    vehicleData.getCoordinateList().get(i),
                    this.defaultStyle
            );
            map.addMapMarker(marker);
        }

        // On initialization, map is focused on the first vehicle within the list
        // TODO: Implement properly
        // TODO: What happens if there are no vehicles in the list? Throw an error?
        Coordinate coordinate = vehicleData.getCoordinateList().get(0);
        map.setDisplayPosition(coordinate, 16);
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
        // TODO: This OK?

        // Ignore any property changes fired from OSM
        // (adding the map to the screen fires propertyChange)
        if (!(evt.getSource().toString().startsWith("org.openstreetmap"))) {
            VisualizeState vehicleData = (VisualizeState) evt.getNewValue();
            visualizeViewModel.setVisualizationState(vehicleData);
            new MapVisualizationView(visualizeViewModel).setVisible(true);
        }
    }

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
            Coordinate selectedCoordinate = (Coordinate) value;

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
            setText(visualizeViewModel.getVisualizationState().getVehicleInformationList().get(
                    (visualizeViewModel.getVisualizationState().getCoordinateList().indexOf(selectedCoordinate))));

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            return this;
        }
    }
}


