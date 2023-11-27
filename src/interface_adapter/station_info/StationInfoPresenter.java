package interface_adapter.station_info;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesState;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import use_case.StationInfo.StationInfoOutputBoundary;
import use_case.StationInfo.StationInfoOutputData;

public class StationInfoPresenter implements StationInfoOutputBoundary {
    private final StationInfoViewModel stationInfoViewModel;
    private final ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel;
    private final ViewManagerModel viewManagerModel;

    public StationInfoPresenter(StationInfoViewModel stationInfoViewModel,
                                ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel,
                                ViewManagerModel viewManagerModel) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.showIncomingVehiclesViewModel = showIncomingVehiclesViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView(StationInfoOutputData response) {
        StationInfoState stationInfoState = stationInfoViewModel.getState();
        stationInfoState.setStateStationName(response.getStationName());

        // On success, switch to show incoming vehicles view.

        ShowIncomingVehiclesState showIncomingVehiclesState = showIncomingVehiclesViewModel.getState();
        showIncomingVehiclesState.setStateStationName(response.getStationName());
        showIncomingVehiclesState.setStateIncomingVehiclesList(response.getStationIncomingVehiclesInfo());
        this.showIncomingVehiclesViewModel.setState(showIncomingVehiclesState);
        showIncomingVehiclesViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(showIncomingVehiclesViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
