package use_case.station_info;

public interface StationInfoOutputBoundary {
    void prepareSuccessView(StationInfoOutputData stationInfoOutputData);

    void parepareFailView(String error);
}
