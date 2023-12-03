package use_case;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import data_access.text_file.FileStationDataAccessObject;
import entity.Station;
import entity.StationFactory;
import entity.TrainFactory;
import org.junit.Test;
import use_case.station_info.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class StationInfoInteractorTest {
    private static void sortByDateTime(List<List<String>> incomingVehiclesInfo) {
        Collections.sort(incomingVehiclesInfo, (list1, list2) -> {
            String dateTimeStr1 = list1.get(7);
            String dateTimeStr2 = list2.get(7);

            LocalDateTime dateTime1 = parseDateTime(dateTimeStr1);
            LocalDateTime dateTime2 = parseDateTime(dateTimeStr2);

            // Compare LocalDateTime objects
            return dateTime1.compareTo(dateTime2);
        });
    }

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
    @Test
    public void successTest() throws IOException {
        StationInfoInputData inputData = new StationInfoInputData("Union Station");
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();

        try {
            StationInfoDataAccessInterface stationRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                    new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

            // This creates a successPresenter that tests whether the test case is as we expect.
            StationInfoOutputBoundary successPresenter = new StationInfoOutputBoundary() {
                @Override
                public void prepareSuccessView(StationInfoOutputData stationInfoOutputData) {
                    Station station = stationRepository.getStation("Union Station");
                    assertEquals("Union Station", stationInfoOutputData.getStationName());
                    assertEquals("Union Station", station.getName());

                    assertTrue(stationRepository.incomingVehiclesNotEmpty("Union Station"));

                    List<List<String>> goVehicleApiList = goVehicleApiClass.retrieveVehicleInfo(station.getId());
                    List<List<String>> goVehicleOutputList = new ArrayList<>();
                    for (List<String> vehicleInfo : goVehicleApiList) {
                        List<String> goVehicleInfo = new ArrayList<>();
                        goVehicleInfo.add("Vehicle Name: " + vehicleInfo.get(1));
                        goVehicleInfo.add("Scheduled Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(4));
                        goVehicleInfo.add("Computed Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(5));
                    }
                    sortByDateTime(goVehicleOutputList);
                    List<List<String>> slicedGoVehiclesInfo;
                    // Since When presenting, Vehicle Api call for Union Station will ALWAYS have more than 3 vehicles,
                    // We will slice the entire list and get the 3 soonest trains to arrive

                    if (goVehicleOutputList.size() <= 3) {
                        slicedGoVehiclesInfo = goVehicleOutputList;
                    } else {
                        slicedGoVehiclesInfo = goVehicleOutputList.subList(0, 3);
                    }
                    for (int i = 0; i < slicedGoVehiclesInfo.size(); i++) {
                        assertEquals(slicedGoVehiclesInfo.get(i).get(i),
                                stationInfoOutputData.getStationIncomingVehiclesInfo().get(i).get(i));
                    }
                }

                @Override
                public void parepareFailView(String error) {
                    fail(error);
                }
            };
            StationInfoInputBoundary interactor = new StationInfoInteractor(stationRepository, successPresenter);
            interactor.execute(inputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void displayAllIfLessThan3Test() throws IOException {
        StationInfoInputData inputData = new StationInfoInputData("Unionville GO"); //TODO: STATION NAME IS SUBJECT TO CHANGE
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();

        try {
            StationInfoDataAccessInterface stationRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                    new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

            // This creates a successPresenter that tests whether the test case is as we expect.
            StationInfoOutputBoundary successPresenter = new StationInfoOutputBoundary() {
                @Override
                public void prepareSuccessView(StationInfoOutputData stationInfoOutputData) {
                    Station station = stationRepository.getStation("Unionville GO");
                    assertEquals("Unionville GO", stationInfoOutputData.getStationName());
                    assertEquals("Unionville GO", station.getName());

                    assertTrue(stationRepository.incomingVehiclesNotEmpty("Unionville GO"));

                    List<List<String>> goVehicleApiList = goVehicleApiClass.retrieveVehicleInfo(station.getId());
                    List<List<String>> goVehicleOutputList = new ArrayList<>();
                    for (List<String> vehicleInfo : goVehicleApiList) {
                        List<String> goVehicleInfo = new ArrayList<>();
                        goVehicleInfo.add("Vehicle Name: " + vehicleInfo.get(1));
                        goVehicleInfo.add("Scheduled Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(4));
                        goVehicleInfo.add("Computed Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(5));
                    }
                    sortByDateTime(goVehicleOutputList);
                    List<List<String>> slicedGoVehiclesInfo;
                    // We are testing stations that has less than or equal to 3 incoming vehicles,
                    // thus we are not slicing the list.
                    slicedGoVehiclesInfo = goVehicleOutputList;

                    for (int i = 0; i < slicedGoVehiclesInfo.size(); i++) {
                        assertEquals(slicedGoVehiclesInfo.get(i).get(i),
                                stationInfoOutputData.getStationIncomingVehiclesInfo().get(i).get(i));
                    }
                }

                @Override
                public void parepareFailView(String error) {
                    fail(error);
                }
            };
            StationInfoInputBoundary interactor = new StationInfoInteractor(stationRepository, successPresenter);
            interactor.execute(inputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void failureVehicleApiCallTest() throws IOException {
        StationInfoInputData inputData = new StationInfoInputData("Milton GO");
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();
        StationInfoDataAccessInterface stationInfoRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

        // This creates a presenter that tests whether the test case is as we expect.
        StationInfoOutputBoundary failurePresenter = new StationInfoOutputBoundary() {
            @Override
            public void prepareSuccessView(StationInfoOutputData stationInfoOutputData) {
                fail("Use case success is unexpected.");
            }

            // Milton GO only offers service in the early morning, which when we're presenting, the api call will
            // have a "204" "No Content" error
            @Override
            public void parepareFailView(String error) {
                assertEquals("Incoming Vehicles Info Retrieval Failed...\nSome error occurred during API call", error);
            }
        };

        StationInfoInputBoundary interactor = new StationInfoInteractor(stationInfoRepository, failurePresenter);
        interactor.execute(inputData);
    }


}
