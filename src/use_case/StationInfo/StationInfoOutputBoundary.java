package use_case.StationInfo;

public interface StationInfoOutputBoundary {
    void prepareSuccessView(StationInfoOutputData stationInfoOutputData);

    void parepareFailView(String error); // TODO: Implement later
}
