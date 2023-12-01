package view;

import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesState;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowIncomingVehiclesView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "show_incoming_vehicles";
    private final ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel;
    JLabel stationName;
    JTextArea stationIncomingVehicles;

    public ShowIncomingVehiclesView(ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) {
        this.showIncomingVehiclesViewModel = showIncomingVehiclesViewModel;
        this.showIncomingVehiclesViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Incoming Vehicles Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationNameLabel = new JLabel("Station name: ");
        stationNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stationName = new JLabel();
        stationName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationIncomingVehiclesLabel = new JLabel("Incoming Vehicles: ");
        stationIncomingVehiclesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stationIncomingVehicles = new JTextArea();

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
        List<String> vehiclesInfo = new ArrayList<>();
        for (java.util.List<String> vehicleInfo : state.getStateIncomingVehiclesList()) {
            String vehicleInfoListAsString = String.join(", ", vehicleInfo);
            vehiclesInfo.add(vehicleInfoListAsString);
            vehiclesInfo.add("\n");
        }
        String vehiclesInfoListAsString = String.join(", ", vehiclesInfo);
        StringBuilder incomingVehicles = new StringBuilder();
        for (String vehicleinfo : vehiclesInfoListAsString.split(",")) {
            incomingVehicles.append(vehicleinfo).append("\n");
        }
        stationIncomingVehicles.setText(incomingVehicles.toString());
    }

}