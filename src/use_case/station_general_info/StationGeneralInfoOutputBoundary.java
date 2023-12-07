package use_case.station_general_info;

public interface StationGeneralInfoOutputBoundary {
    /**
     * Provides a blueprint for the prepareSuccessView and prepareFailView methods*/
    void prepareSuccessView(StationGeneralInfoOutputData searchOutputData);
    void prepareFailView(String error);
}
