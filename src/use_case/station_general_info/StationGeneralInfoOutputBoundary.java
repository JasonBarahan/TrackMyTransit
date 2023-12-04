package use_case.station_general_info;

public interface StationGeneralInfoOutputBoundary {
    void prepareSuccessView(StationGeneralInfoOutputData searchOutputData);
    void prepareFailView(String error);
}
