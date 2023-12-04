package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import interface_adapter.station_general_info.StationGeneralInfoController;
import interface_adapter.station_general_info.StationGeneralInfoPresenter;
import interface_adapter.station_general_info.StationGeneralInfoViewModel;
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
            StationGeneralInfoViewModel stationAmenitiesInfoViewModel,
            ShowIncomingVehiclesDataAccessInterface stationAmenitiesInfoDataAccessObject,
            ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) {

        try {
            StationGeneralInfoController stationAmenitiesInfoController = createStationInfoUseCase(viewManagerModel,
                    stationAmenitiesInfoViewModel, stationAmenitiesInfoDataAccessObject, showIncomingVehiclesViewModel);
            return new StationAmenitiesView(stationAmenitiesInfoViewModel, stationAmenitiesInfoController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open station data file.");
        }

        return null;
    }

    private static StationGeneralInfoController createStationInfoUseCase(
            ViewManagerModel viewManagerModel,
            StationGeneralInfoViewModel stationAmenitiesInfoViewModel,
            ShowIncomingVehiclesDataAccessInterface stationAmenitiesInfoDataAccessObject,
            ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        ShowIncomingVehiclesOutputBoundary showIncomingVehiclesOutputBoundary = new StationGeneralInfoPresenter(
                stationAmenitiesInfoViewModel, showIncomingVehiclesViewModel, viewManagerModel);

        ShowIncomingVehiclesInputBoundary showIncomingVehiclesInteractor = new ShowIncomingVehiclesInteractor(
                stationAmenitiesInfoDataAccessObject, showIncomingVehiclesOutputBoundary);

        return new StationGeneralInfoController(showIncomingVehiclesInteractor);
    }
}
