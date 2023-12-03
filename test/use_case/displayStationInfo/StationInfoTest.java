package use_case.displayStationInfo;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import data_access.API.TestGOStationApiClass;
import data_access.text_file.FileStationDataAccessObject;
import entity.StationFactory;
import entity.StationInterface;
import entity.TrainFactory;
import org.junit.jupiter.api.Test;
import use_case.search_show_amenities.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class StationInfoTest {

    //This is a unit test that tests whether the program can handle a valid input
    @Test
    void successTest() {
        SearchShowAmenitiesInputData inputData = new SearchShowAmenitiesInputData("Aurora GO");
        try {
            SearchShowAmenitiesDataAccessInterface stationObjRepository = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(), new TrainFactory(), new GOStationApiClass(), new GOVehicleApiClass());
        // This creates a successPresenter that tests whether the test case is as we expect.
        SearchShowAmenitiesOutputBoundary successPresenter = new SearchShowAmenitiesOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchShowAmenitiesOutputData searchOutputData) {

                StationInterface stationObj = stationObjRepository.getStation("Aurora GO");

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

        SearchShowAmenitiesInputBoundary searchInteractor = new SearchShowAmenitiesInteractor(stationObjRepository, successPresenter);
        searchInteractor.execute(inputData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //This is a unit test that tests whether the program can handle an invalid input
    @Test
    void failureInvalidInputTest() {
        SearchShowAmenitiesInputData inputData = new SearchShowAmenitiesInputData("ABCDEFG");
        try {
            SearchShowAmenitiesDataAccessInterface stationObjRepository = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(), new TrainFactory(), new GOStationApiClass(), new GOVehicleApiClass());
            // This creates a successPresenter that tests whether the test case is as we expect.
            SearchShowAmenitiesOutputBoundary failurePresenter = new SearchShowAmenitiesOutputBoundary() {
                @Override
                public void prepareSuccessView(SearchShowAmenitiesOutputData searchOutputData) {
                    // 2 things to check: the output data is correct, and the user has been created in the DAO.
                    fail("Use case success is unexpected.");
                }

                @Override
                public void prepareFailView(String error) {
                    assertEquals("ERROR: Station with the name [ABCDEFG] does not exist", error);
                    StationInterface stationObj = stationObjRepository.getStation("ABCDEFG");
                    assertNull(stationObj);
                }
            };

            SearchShowAmenitiesInputBoundary searchInteractor = new SearchShowAmenitiesInteractor(stationObjRepository, failurePresenter);
            searchInteractor.execute(inputData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void failureInvalidAPIKeyTest() {
        SearchShowAmenitiesInputData inputData = new SearchShowAmenitiesInputData("Union Station");
        try {
            SearchShowAmenitiesDataAccessInterface stationObjRepository = new FileStationDataAccessObject("./revisedStopData.txt", new StationFactory(), new TrainFactory(), new TestGOStationApiClass(), new GOVehicleApiClass());
            // This creates a successPresenter that tests whether the test case is as we expect.
            SearchShowAmenitiesOutputBoundary failurePresenter = new SearchShowAmenitiesOutputBoundary() {
                @Override
                public void prepareSuccessView(SearchShowAmenitiesOutputData searchOutputData) {
                    // 2 things to check: the output data is correct, and the user has been created in the DAO.
                    fail("Use case success is unexpected.");
                }

                @Override
                public void prepareFailView(String error) {
                    StationInterface stationObj = stationObjRepository.getStation("Aurora GO");
                    assertEquals("Invalid API Call. Message returned: Unauthorized", error);
                    //Note: Union Station is an actual valid GO Station, so it would have a respective Station object due to reading from the text file
                    // However, its amenities should not be populated since an API call should NOT have been made
                    // Therefore, its amenitiesList attribute (which is List<String> should be 0)
                    assertEquals(stationObj.getAmenitiesList().size(), 0);
                }
            };

            SearchShowAmenitiesInputBoundary searchInteractor = new SearchShowAmenitiesInteractor(stationObjRepository, failurePresenter);
            searchInteractor.execute(inputData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
