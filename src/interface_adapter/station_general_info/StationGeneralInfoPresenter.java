package interface_adapter.station_general_info;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesState;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import interface_adapter.station_general_info.StationGeneralInfoState;
import interface_adapter.station_general_info.StationGeneralInfoViewModel;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesOutputBoundary;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesOutputData;

public class StationGeneralInfoPresenter implements ShowIncomingVehiclesOutputBoundary {
    private final StationGeneralInfoViewModel stationInfoViewModel;
    private final ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel;
    private final ViewManagerModel viewManagerModel;

    public StationGeneralInfoPresenter(StationGeneralInfoViewModel stationInfoViewModel,
                                         ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel,
                                         ViewManagerModel viewManagerModel) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.showIncomingVehiclesViewModel = showIncomingVehiclesViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView(ShowIncomingVehiclesOutputData response) {
        StationGeneralInfoState stationAmenitiesInfoState = stationInfoViewModel.getState();
        stationAmenitiesInfoState.setStateStationName(response.getStationName());
        stationAmenitiesInfoState.setIncomingVehiclesError(null);

        // On success, switch to show incoming vehicles view.

        ShowIncomingVehiclesState showIncomingVehiclesState = showIncomingVehiclesViewModel.getState();
        showIncomingVehiclesState.setStateStationName(response.getStationName());
        showIncomingVehiclesState.setStateIncomingVehiclesList(response.getStationIncomingVehiclesInfo());
        this.showIncomingVehiclesViewModel.setState(showIncomingVehiclesState);
        showIncomingVehiclesViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(showIncomingVehiclesViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String incomingVehiclesRetrievalError) {
        StationGeneralInfoState stationAmenitiesInfoState = stationInfoViewModel.getState();
        stationAmenitiesInfoState.setIncomingVehiclesError(incomingVehiclesRetrievalError);
        stationInfoViewModel.firePropertyChanged();
    }
}