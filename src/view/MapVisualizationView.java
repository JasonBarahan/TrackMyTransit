package view;

import interface_adapter.visualize.VisualizeController;
import interface_adapter.visualize.VisualizeViewModel;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    public MapVisualizationView(VisualizeViewModel visualizeViewModel, VisualizeController visualizeController) {
        this.visualizeViewModel = visualizeViewModel;
        visualizeViewModel.addPropertyChangeListener(this);

        this.visualizeController = visualizeController;

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

        // On initialization, map is focused on the first vehicle within the list
        // TODO: Implement
//        map().setDisplayPosition(c(trains[0].getLatitude(), trains[0].getLongitude()), 13);
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
        new MapVisualizationView(new VisualizeViewModel(), null).setVisible(true);
    }
}
