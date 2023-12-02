package interface_adapter.station_info;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesState;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import use_case.station_info.StationInfoOutputBoundary;
import use_case.station_info.StationInfoOutputData;

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
        stationInfoState.setIncomingVehiclesError(null);

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
        StationInfoState stationInfoState = stationInfoViewModel.getState();
        stationInfoState.setIncomingVehiclesError(incomingVehiclesRetrievalError);
        stationInfoViewModel.firePropertyChanged();
    }
}
