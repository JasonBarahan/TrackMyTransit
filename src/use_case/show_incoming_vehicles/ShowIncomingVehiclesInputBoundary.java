package use_case.show_incoming_vehicles;

import java.text.ParseException;

public interface ShowIncomingVehiclesInputBoundary {
    void execute(ShowIncomingVehiclesInputData showIncomingVehiclesInputData) throws ParseException;
}
