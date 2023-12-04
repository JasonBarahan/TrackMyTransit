package interface_adapter.stationInfo.PresenterTest;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.station_general_info.StationGeneralInfoOutputBoundary;
import use_case.station_general_info.StationGeneralInfoOutputData;

import static org.junit.jupiter.api.Assertions.*;

class SearchPresenterTest {
    private MockPresenterSearchViewModel mockSearchViewModel;
    private MockPresenterStationGeneralInfoViewModel mockStationInfoViewModel;
    private  ViewManagerModel mockViewManagerModel;
    private StationGeneralInfoOutputBoundary mockSearchPresenter;

    @BeforeEach
    void setUp() {
        mockSearchViewModel = new MockPresenterSearchViewModel();
        mockStationInfoViewModel = new MockPresenterStationGeneralInfoViewModel();
        mockViewManagerModel = new ViewManagerModel();
        mockSearchPresenter = new SearchPresenter(mockSearchViewModel, mockStationInfoViewModel, mockViewManagerModel);
    }

    @Test
    void testPrepareSuccessView() {
        StationGeneralInfoOutputData mockSuccessSearchOutputData = new StationGeneralInfoOutputData("Union Station", "Union Line", "Public Washrooms, Wifi");
        mockSearchPresenter.prepareSuccessView(mockSuccessSearchOutputData);
        assertEquals("Union Station", mockStationInfoViewModel.getState().getStateStationName());
        assertEquals("Union Line", mockStationInfoViewModel.getState().getStateStationParentLine());
        assertEquals("<html>Public Washrooms, Wifi<html>", mockStationInfoViewModel.getState().getStateStationAmenities());
    }

    @Test
    void testPrepareFailViewInvalidAPICall() {
        mockSearchPresenter.prepareFailView("Invalid API Call. Message returned: " + "Unauthorized");
        assertEquals("Invalid API Call. Message returned: Unauthorized", mockSearchViewModel.getState().getStateStationError());
    }

    @Test
    void testPrepareFailErrorStationName() {
        mockSearchPresenter.prepareFailView("ERROR: Station with the name [" + "potato" + "] does not exist");
        assertEquals("ERROR: Station with the name [" + "potato" + "] does not exist", mockSearchViewModel.getState().getStateStationError());
    }
}
