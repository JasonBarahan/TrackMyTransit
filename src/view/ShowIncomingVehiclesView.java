package view;

import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesState;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShowIncomingVehiclesView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "show_incoming_vehicles";
    private final ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel;
    JLabel stationName;
    JLabel stationIncomingVehicles;

    public ShowIncomingVehiclesView(ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) {
        this.showIncomingVehiclesViewModel = showIncomingVehiclesViewModel;
        this.showIncomingVehiclesViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Incoming Vehicles Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationNameLabel = new JLabel("Station name: ");
        stationName = new JLabel();

        JLabel stationIncomingVehiclesLabel = new JLabel("Incoming Vehicles: ");
        stationIncomingVehicles = new JLabel();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(stationNameLabel);
        this.add(stationName);
        this.add(stationIncomingVehiclesLabel);
        this.add(stationIncomingVehicles);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("Reached"); //Commented out for testing purposes
        ShowIncomingVehiclesState state = (ShowIncomingVehiclesState) evt.getNewValue();
        stationName.setText(state.getStateStationName());
        stationIncomingVehicles.setText(state.getStateIncomingVehiclesList().toString());
        //TODO: Should we make incoming vehicles a String instead of a list?
    }

}
