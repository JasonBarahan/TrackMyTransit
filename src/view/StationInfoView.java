package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.station_info.StationInfoController;
import interface_adapter.station_info.StationInfoState;
import interface_adapter.station_info.StationInfoViewModel;


public class StationInfoView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "stationInfo";
    private final StationInfoViewModel stationInfoViewModel;
    JLabel stationName;
    final JButton show_incoming_vehicles;
    private final StationInfoController stationInfoController;

    /**
     * A window with a title and a JButton.
     */
    public StationInfoView(StationInfoViewModel stationInfoViewModel, StationInfoController stationInfoController) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.stationInfoController = stationInfoController;
        this.stationInfoViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Station info screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stationInfo = new JLabel("Station name: ");
        stationName = new JLabel();

        JPanel buttons = new JPanel();
        show_incoming_vehicles = new JButton(stationInfoViewModel.SHOW_INCOMING_VEHICLES_BUTTON_LABEL);
        buttons.add(show_incoming_vehicles);

        show_incoming_vehicles.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(show_incoming_vehicles)) {
                            StationInfoState currentState = stationInfoViewModel.getState();

                            StationInfoView.this.stationInfoController.execute(
                                    currentState.getStateStationName()
                            );
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(stationInfo);
        this.add(stationName);
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
        StationInfoState state = (StationInfoState) evt.getNewValue();
        stationName.setText(state.getStateStationName());
    }

}
