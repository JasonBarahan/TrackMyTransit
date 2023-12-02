package use_case.station_info;

public interface StationInfoOutputBoundary {
    void prepareSuccessView(StationInfoOutputData stationInfoOutputData);

    void prepareFailView(String error);
}
