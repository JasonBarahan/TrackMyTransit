package interface_adapter.stationInfo.ControllerTest;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import data_access.text_file.FileStationDataAccessObject;
import entity.StationFactory;
import entity.TrainFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.station_general_info.StationGeneralInfoDataAccessInterface;
import use_case.station_general_info.StationGeneralInfoOutputBoundary;

import java.io.IOException;
import java.text.ParseException;

class StationGeneralInfoControllerTest {

    //Defining variables used in the test

    private StationGeneralInfoDataAccessInterface testStationDAO;
    private StationGeneralInfoOutputBoundary testSearchPresenter;
    private MockControllerStationGeneralInfoInteractor testSearchInteractor;
    private SearchController testSearchController;
    private MockControllerSearchViewModel testSearchViewModel;
    private MockControllerSearchState testSearchState;
    private MockControllerStationGeneralInfoViewModel testStationInfoViewModel;

    @BeforeEach
    void setUp() {
        try {

            testSearchViewModel = new MockControllerSearchViewModel();
            testStationInfoViewModel = new MockControllerStationGeneralInfoViewModel();
            testSearchState = testSearchViewModel.getState();

            testStationDAO = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(),
                    new TrainFactory(), new GOStationApiClass(), new GOVehicleApiClass());
            testSearchPresenter = new MockControllerStationGeneralInfoPresenter(testSearchViewModel, testStationInfoViewModel,new ViewManagerModel());
            testSearchInteractor = new MockControllerStationGeneralInfoInteractor(testStationDAO, testSearchPresenter);
            testSearchController = new SearchController(testSearchInteractor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validSuccessfulExecute() throws ParseException {
        testSearchController.execute(testSearchState.getStateStationName());
        // Note: If the message displayed in the print is "If reached here, then execute successfully called"
        // Then, call has successfully ran
    }
}