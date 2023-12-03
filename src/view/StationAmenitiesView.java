package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.station_amenites_info.StationAmenitiesInfoController;
import interface_adapter.station_amenites_info.StationAmenitiesInfoState;
import interface_adapter.station_amenites_info.StationAmenitiesInfoViewModel;


public class StationAmenitiesView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "stationInfo";
    private final StationAmenitiesInfoViewModel stationInfoViewModel;

    JLabel stationName;
    JLabel stationParentLine;
    JLabel stationAmenities;
    final JButton show_incoming_vehicles;
    private final StationAmenitiesInfoController stationAmenitiesInfoController;

    /**
     * A window with a title and a JButton.
     */
    public StationAmenitiesView(StationAmenitiesInfoViewModel stationInfoViewModel, StationAmenitiesInfoController stationAmenitiesInfoController) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.stationAmenitiesInfoController = stationAmenitiesInfoController;
        this.stationInfoViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Station info screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationNameLabel = new JLabel("Station name: ");
        stationName = new JLabel();
        JLabel stationParentLineLabel = new JLabel("Station parent line: ");
        stationParentLine = new JLabel();
        JLabel stationAmenitiesLabel = new JLabel("Station amenities: ");
        stationAmenities = new JLabel();

        JPanel buttons = new JPanel();
        show_incoming_vehicles = new JButton(stationInfoViewModel.SHOW_INCOMING_VEHICLES_BUTTON_LABEL);
        buttons.add(show_incoming_vehicles);

        show_incoming_vehicles.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(show_incoming_vehicles)) {
                            StationAmenitiesInfoState currentState = stationInfoViewModel.getState();

                            StationAmenitiesView.this.stationAmenitiesInfoController.execute(
                                    currentState.getStateStationName()
                            );
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(stationNameLabel);
        this.add(stationName);
        this.add(stationParentLineLabel);
        this.add(stationParentLine);
        this.add(stationAmenitiesLabel);
        this.add(stationAmenities);
        this.add(buttons);
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
        StationAmenitiesInfoState state = (StationAmenitiesInfoState) evt.getNewValue();
        if (state.getIncomingVehiclesError() != null) {
            JOptionPane.showMessageDialog(this, state.getIncomingVehiclesError());
        }
        stationName.setText(state.getStateStationName());
        stationAmenities.setText(state.getStateStationAmenities());
        stationParentLine.setText(state.getStateStationParentLine());
    }
}