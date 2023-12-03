package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import interface_adapter.station_info.StationInfoController;
import interface_adapter.station_info.StationInfoPresenter;
import interface_adapter.station_info.StationInfoViewModel;
import use_case.station_info.StationInfoDataAccessInterface;
import use_case.station_info.StationInfoInputBoundary;
import use_case.station_info.StationInfoInteractor;
import use_case.station_info.StationInfoOutputBoundary;
import view.StationInfoView;

import javax.swing.*;
import java.io.IOException;

public class StationInfoUseCaseFactory {

    /** Prevent instantiation. */
    private StationInfoUseCaseFactory() {}

    public static StationInfoView create(
            ViewManagerModel viewManagerModel,
            StationInfoViewModel stationInfoViewModel,
            StationInfoDataAccessInterface stationInfoDataAccessObject,
            ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) {

        try {
            StationInfoController stationInfoController = createStationInfoUseCase(viewManagerModel,
                    stationInfoViewModel, stationInfoDataAccessObject, showIncomingVehiclesViewModel);
            return new StationInfoView(stationInfoViewModel, stationInfoController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open station data file.");
        }

        return null;
    }

    private static StationInfoController createStationInfoUseCase(
            ViewManagerModel viewManagerModel,
            StationInfoViewModel stationInfoViewModel,
            StationInfoDataAccessInterface stationInfoDataAccessObject,
            ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        StationInfoOutputBoundary stationInfoOutputBoundary = new StationInfoPresenter(stationInfoViewModel,
                showIncomingVehiclesViewModel, viewManagerModel);

        StationInfoInputBoundary stationInfoInteractor = new StationInfoInteractor(
                stationInfoDataAccessObject, stationInfoOutputBoundary);

        return new StationInfoController(stationInfoInteractor);
    }
}
