package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.station_info.StationInfoState;
import interface_adapter.station_info.StationInfoViewModel;


public class StationInfoView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "stationInfo";
    private final StationInfoViewModel stationInfoViewModel;

    JLabel stationName;
    JLabel stationParentLine;
    JLabel stationAmenities;

    /**
     * A window with a title and a JButton.
     */
    public StationInfoView(StationInfoViewModel stationInfoViewModel) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.stationInfoViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Station info screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationNameLabel = new JLabel("Station name: ");
        stationName = new JLabel();

        JLabel stationParentLineLabel = new JLabel("Station parent line: ");
        stationParentLine = new JLabel();

        JLabel stationAmenitiesLabel = new JLabel("Station amenities: ");
        stationAmenities = new JLabel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(stationNameLabel);
        this.add(stationName);
        this.add(stationParentLineLabel);
        this.add(stationParentLine);
        this.add(stationAmenitiesLabel);
        this.add(stationAmenities);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("Reached"); //Commented out for testing purposes
        StationInfoState state = (StationInfoState) evt.getNewValue();
        stationName.setText(state.getStateStationName());
        stationAmenities.setText(state.getStateStationAmenities());
        stationParentLine.setText(state.getStateStationParentLine());
    }
}