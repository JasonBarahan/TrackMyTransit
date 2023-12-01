package use_case.displayStationInfo;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import data_access.text_file.FileStationDataAccessObject;
import entity.Station;
import entity.StationFactory;
import entity.TrainFactory;
import org.junit.jupiter.api.Test;
import use_case.search.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class StationInfoTest {

    @Test
    void successTest() {
        SearchInputData inputData = new SearchInputData("Aurora GO");
        try {
            SearchDataAccessInterface stationObjRepository = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(), new TrainFactory(), new GOStationApiClass(), new GOVehicleApiClass());
        // This creates a successPresenter that tests whether the test case is as we expect.
        SearchOutputBoundary successPresenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData searchOutputData) {

                Station stationObj = stationObjRepository.getStation("Aurora GO");

                assertNotNull(stationObj);

                assertEquals("Aurora GO", searchOutputData.getStationName());
                assertEquals("Aurora GO", stationObj.getName());

                assertEquals("Barrie", searchOutputData.getStationParentLine());
                assertEquals("Barrie", stationObjRepository.getStationParentLine("Aurora GO"));

                assertEquals("Bicycle Rack, Kiss & Ride Passenger Drop Off, Pay Phones, Public Washroom, Reserved parking, Ticket Vending Machine (Cash/ Debit/ Credit) , Waiting Room, Wheelchair Accessible Bus Service, Wheelchair Accessible Train Service, Carpool, Shelters, Wi-Fi, PRESTO Enabled TVM (Cash/Debit/Credit), Heated Shelters, Self Serve Reload Machine (Debit/ Credit)",
                        searchOutputData.getStationAmenities());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        SearchInputBoundary searchInteractor = new SearchInteractor(stationObjRepository, successPresenter);
        searchInteractor.execute(inputData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void failureInvalidInputTest() {
        SearchInputData inputData = new SearchInputData("ABCDEFG");
        try {
            SearchDataAccessInterface stationObjRepository = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(), new TrainFactory(), new GOStationApiClass(), new GOVehicleApiClass());
            // This creates a successPresenter that tests whether the test case is as we expect.
            SearchOutputBoundary failurePresenter = new SearchOutputBoundary() {
                @Override
                public void prepareSuccessView(SearchOutputData searchOutputData) {
                    // 2 things to check: the output data is correct, and the user has been created in the DAO.
                    fail("Use case success is unexpected.");
                }

                @Override
                public void prepareFailView(String error) {
                    assertEquals("ERROR: Station with the name [ABCDEFG] does not exist", error);
                    Station stationObj = stationObjRepository.getStation("ABCDEFG");
                    assertNull(stationObj);
                }
            };

            SearchInputBoundary searchInteractor = new SearchInteractor(stationObjRepository, failurePresenter);
            searchInteractor.execute(inputData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
