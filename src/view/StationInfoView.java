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

    /**
     * A window with a title and a JButton.
     */
    public StationInfoView(StationInfoViewModel stationInfoViewModel) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.stationInfoViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Station info screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationInfo = new JLabel("Station name: ");
        stationName = new JLabel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(stationInfo);
        this.add(stationName);
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
    }
}