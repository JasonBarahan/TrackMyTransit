package use_case.station_info;

public class StationInfoInteractor {
    final StationInfoDataAccessInterface stationInfoDataAccessObject;
    final StationInfoOutputBoundary stationPresenter;

    public StationInfoInteractor(StationInfoDataAccessInterface stationInfoDataAccessInterface,
                                 StationInfoOutputBoundary stationInfoOutputBoundary) {
        this.stationInfoDataAccessObject = stationInfoDataAccessInterface;
        this.stationPresenter = stationInfoOutputBoundary;
    }
}
