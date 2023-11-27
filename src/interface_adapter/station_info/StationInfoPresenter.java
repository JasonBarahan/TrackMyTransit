package interface_adapter.station_info;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesState;
import interface_adapter.show_incoming_vehicles.ShowIncomingVehiclesViewModel;
import use_case.StationInfo.StationInfoOutputBoundary;
import use_case.StationInfo.StationInfoOutputData;

public class StationInfoPresenter implements StationInfoOutputBoundary {
    private final StationInfoViewModel stationInfoViewModel;
    private final ShowIncomingVehiclesViewModel showIncomingVehiclesoViewModel;
    private final ViewManagerModel viewManagerModel;

    public StationInfoPresenter(StationInfoViewModel stationInfoViewModel,
                                ShowIncomingVehiclesViewModel showIncomingVehiclesViewModel,
                                ViewManagerModel viewManagerModel) {
        this.stationInfoViewModel = stationInfoViewModel;
        this.showIncomingVehiclesoViewModel = showIncomingVehiclesViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView(StationInfoOutputData response) {
        // On success, switch to show incoming vehicles view.

        ShowIncomingVehiclesState showIncomingVehiclesState = ShowIncomingVehiclesViewModel.getState();
//        loginState.setUsername(response.getUsername());
//        this.loginViewModel.setState(loginState);
//        loginViewModel.firePropertyChanged();
//
//        viewManagerModel.setActiveView(loginViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
        //TODO: implement show incoming vehicles first
    }
}
