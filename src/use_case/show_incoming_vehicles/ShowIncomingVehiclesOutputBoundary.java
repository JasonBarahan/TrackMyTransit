package use_case.station_general_info;

public interface ShowIncomingVehiclesOutputBoundary {
    void prepareSuccessView(ShowIncomingVehiclesOutputData showIncomingVehiclesOutputData);

    void prepareFailView(String error);
}
