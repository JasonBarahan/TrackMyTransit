package interface_adapter.stationInfo.ControllerTest;
import interface_adapter.ViewManagerModel;
import use_case.station_general_info.StationGeneralInfoOutputBoundary;
import use_case.station_general_info.StationGeneralInfoOutputData;

public class MockControllerStationGeneralInfoPresenter implements StationGeneralInfoOutputBoundary {
    private final MockControllerSearchViewModel searchViewModel;
    private final MockControllerStationGeneralInfoViewModel stationInfoViewModel;
    private final ViewManagerModel viewManagerModel;

    public MockControllerStationGeneralInfoPresenter(MockControllerSearchViewModel searchViewModel, MockControllerStationGeneralInfoViewModel stationInfoViewModel, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.stationInfoViewModel = stationInfoViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(StationGeneralInfoOutputData response){
        String retrieveStationName = response.getStationName();
        String retrieveStationAmenities = response.getStationAmenities();
        String retrieveParentLine = response.getStationParentLine();

        // Step 1: Resetting the station error value in the searchState to be null (in case any failed search attempts came before this "successful" attempt)
        MockControllerSearchState searchState = searchViewModel.getState();
        searchState.setStateStationName(retrieveStationName);

        //  This line sets the state's station error attribute value to null.
        //  This happens ONLY IF the prepareSuccessView has been triggered
        //  This is because should there be an error message stored in the state, we want to CLEAR THAT to display the success view
        searchState.setStateStationError(null);

        // Step 2: Setting values in the SearchPanelView

        // Setting the station name value and updating state with change
        MockControllerStationGeneralInfoState stationInfoState = stationInfoViewModel.getState();
        stationInfoState.setStateStationName(retrieveStationName);

        // Setting the station amenities list and updating state with change
        stationInfoState.setStateStationAmenities(retrieveStationAmenities);

        // Setting the station amenities list and updating state with change
        stationInfoState.setStateStationParentLine(retrieveParentLine);

        stationInfoViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(stationInfoViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String stationRetrievalError){
        MockControllerSearchState searchState = searchViewModel.getState();
        searchState.setStateStationError(stationRetrievalError);
        searchViewModel.firePropertyChanged();
    }
}
