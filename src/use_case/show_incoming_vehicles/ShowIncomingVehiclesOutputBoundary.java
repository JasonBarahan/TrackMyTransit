package use_case.show_incoming_vehicles;

public interface ShowIncomingVehiclesOutputBoundary {
    void prepareSuccessView(ShowIncomingVehiclesOutputData showIncomingVehiclesOutputData);

    void prepareFailView(String error);
}
