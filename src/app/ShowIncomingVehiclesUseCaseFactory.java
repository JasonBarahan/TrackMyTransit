package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import interface_adapter.station_amenites_info.StationAmenitiesInfoController;
import interface_adapter.station_amenites_info.StationAmenitiesInfoPresenter;
import interface_adapter.station_amenites_info.StationAmenitiesInfoViewModel;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesDataAccessInterface;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesInputBoundary;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesInteractor;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesOutputBoundary;
import view.StationAmenitiesView;

import javax.swing.*;
import java.io.IOException;

public class ShowIncomingVehiclesUseCaseFactory {

    /** Prevent instantiation. */
    private ShowIncomingVehiclesUseCaseFactory() {}

    public static StationAmenitiesView create(
            ViewManagerModel viewManagerModel,
            StationAmenitiesInfoViewModel stationInfoViewModel,
            ShowIncomingVehiclesDataAccessInterface stationInfoDataAccessObject,
            ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) {

        try {
            StationAmenitiesInfoController stationAmenitiesInfoController = createStationInfoUseCase(viewManagerModel,
                    stationInfoViewModel, stationInfoDataAccessObject, showIncomingVehiclesViewModel);
            return new StationAmenitiesView(stationInfoViewModel, stationAmenitiesInfoController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static StationAmenitiesInfoController createStationInfoUseCase(
            ViewManagerModel viewManagerModel,
            StationAmenitiesInfoViewModel stationInfoViewModel,
            ShowIncomingVehiclesDataAccessInterface stationInfoDataAccessObject,
            ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        ShowIncomingVehiclesOutputBoundary stationInfoOutputBoundary = new StationAmenitiesInfoPresenter(stationInfoViewModel,
                showIncomingVehiclesViewModel, viewManagerModel);

        ShowIncomingVehiclesInputBoundary stationInfoInteractor = new ShowIncomingVehiclesInteractor(
                stationInfoDataAccessObject, stationInfoOutputBoundary);

        return new StationAmenitiesInfoController(stationInfoInteractor);
    }
}
