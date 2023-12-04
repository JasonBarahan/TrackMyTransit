package use_case;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import data_access.text_file.FileStationDataAccessObject;
import entity.StationFactory;
import entity.StationInterface;
import entity.TrainFactory;
import org.junit.Test;
import use_case.show_incoming_vehicles.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShowIncomingVehiclesInteractorTest {
    private static void sortByDateTime(List<List<String>> incomingVehiclesInfo) {
        incomingVehiclesInfo.sort((list1, list2) -> {
            String dateTimeStr1 = list1.get(4);
            String dateTimeStr2 = list2.get(4);

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
        ShowIncomingVehiclesInputData inputData = new ShowIncomingVehiclesInputData("Union Station");
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();

        try {
            ShowIncomingVehiclesDataAccessInterface stationRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                    new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

            // This creates a successPresenter that tests whether the test case is as we expect.
            ShowIncomingVehiclesOutputBoundary successPresenter = new ShowIncomingVehiclesOutputBoundary() {
                @Override
                public void prepareSuccessView(ShowIncomingVehiclesOutputData showIncomingVehiclesOutputData) {
                    StationInterface station = stationRepository.getStation("Union Station");
                    assertEquals("Union Station", showIncomingVehiclesOutputData.getStationName());
                    assertEquals("Union Station", station.getName());

                    assertNotNull(station);

                    assertFalse(stationRepository.incomingVehiclesIsEmpty("Union Station"));

                    List<List<String>> goVehicleApiList = goVehicleApiClass.retrieveVehicleInfo(station.getId());
                    List<List<String>> goVehicleOutputList = new ArrayList<>();
                    for (List<String> vehicleInfo : goVehicleApiList) {
                        List<String> goVehicleInfo = new ArrayList<>();
                        goVehicleInfo.add("Vehicle Name: " + vehicleInfo.get(1));
                        goVehicleInfo.add("Scheduled Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(4));
                        goVehicleInfo.add("Computed Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(5));
                        goVehicleOutputList.add(goVehicleInfo);
                    }
                    sortByDateTime(goVehicleOutputList);
                    List<List<String>> slicedGoVehiclesInfo;
                    // Since When presenting, Vehicle Api call for Union Station will ALWAYS have more than 3 vehicles,
                    // We will slice the entire list and get the 3 soonest trains to arrive

//                    if (goVehicleOutputList.size() <= 3) {
//                        slicedGoVehiclesInfo = goVehicleOutputList;
//                    } else {
                        slicedGoVehiclesInfo = goVehicleOutputList.subList(0, 3);
//                    }
                    List<List<String>> incomingVehiclesOutput = showIncomingVehiclesOutputData.getStationIncomingVehiclesInfo();
                    for (int i = 0; i < slicedGoVehiclesInfo.size(); i++) {
                        assertEquals(slicedGoVehiclesInfo.get(i).get(0),
                                incomingVehiclesOutput.get(i).get(0));
                    }
                }

                @Override
                public void prepareFailView(String error) {
                    fail(error);
                }
            };
            ShowIncomingVehiclesInputBoundary interactor = new ShowIncomingVehiclesInteractor(stationRepository, successPresenter);
            interactor.execute(inputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void displayAllIfLessThan3Test() throws IOException {
        String stationName = "Newmarket GO";
        ShowIncomingVehiclesInputData inputData = new ShowIncomingVehiclesInputData(stationName); //TODO: STATION NAME IS SUBJECT TO CHANGE
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();

        try {
            ShowIncomingVehiclesDataAccessInterface stationRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                    new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

            // This creates a successPresenter that tests whether the test case is as we expect.
            ShowIncomingVehiclesOutputBoundary successPresenter = new ShowIncomingVehiclesOutputBoundary() {
                @Override
                public void prepareSuccessView(ShowIncomingVehiclesOutputData showIncomingVehiclesOutputData) {
                    StationInterface station = stationRepository.getStation(stationName);
                    assertEquals(stationName, showIncomingVehiclesOutputData.getStationName());
                    assertEquals(stationName, station.getName());

                    assertNotNull(station);
                    assertFalse(stationRepository.incomingVehiclesIsEmpty(stationName));

                    List<List<String>> goVehicleApiList = goVehicleApiClass.retrieveVehicleInfo(station.getId());
                    List<List<String>> goVehicleOutputList = new ArrayList<>();
                    for (List<String> vehicleInfo : goVehicleApiList) {
                        List<String> goVehicleInfo = new ArrayList<>();
                        goVehicleInfo.add("Vehicle Name: " + vehicleInfo.get(1));
                        goVehicleInfo.add("Scheduled Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(4));
                        goVehicleInfo.add("Computed Departure Time: ");
                        goVehicleInfo.add(vehicleInfo.get(5));
                        goVehicleOutputList.add(goVehicleInfo);
                    }
                    sortByDateTime(goVehicleOutputList);
                    List<List<String>> slicedGoVehiclesInfo;
                    // We are testing stations that has less than or equal to 3 incoming vehicles,
                    // thus we are not slicing the list.
                    slicedGoVehiclesInfo = goVehicleOutputList;
                    List<List<String>> incomingVehiclesOutput = showIncomingVehiclesOutputData.getStationIncomingVehiclesInfo();

                    for (int i = 0; i < slicedGoVehiclesInfo.size(); i++) {
                        assertEquals(slicedGoVehiclesInfo.get(i).get(0),
                                incomingVehiclesOutput.get(i).get(0));
                    }
                }

                @Override
                public void prepareFailView(String error) {
                    fail(error);
                }
            };
            ShowIncomingVehiclesInputBoundary interactor = new ShowIncomingVehiclesInteractor(stationRepository, successPresenter);
            interactor.execute(inputData);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void failureNoContentVehicleApiCallTest() throws IOException, ParseException {
        ShowIncomingVehiclesInputData inputData = new ShowIncomingVehiclesInputData("Milton GO");
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();
        ShowIncomingVehiclesDataAccessInterface stationRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

        // This creates a presenter that tests whether the test case is as we expect.
        ShowIncomingVehiclesOutputBoundary failurePresenter = new ShowIncomingVehiclesOutputBoundary() {
            @Override
            public void prepareSuccessView(ShowIncomingVehiclesOutputData stationInfoOutputData) {
                fail("Use case success is unexpected.");
            }

            // Milton GO only offers service in the early morning, which when we're presenting, the api call will
            // have a "204" "No Content" error
            @Override
            public void prepareFailView(String error) {
                assertEquals("An error occurred during API call.\nError Message: No Content", error);
                StationInterface station =stationRepository.getStation("Milton GO");
                assertTrue(stationRepository.incomingVehiclesIsEmpty("Milton GO"));
                assertEquals(station.getIncomingVehicles().size(), 0);
            }
        };

        ShowIncomingVehiclesInputBoundary interactor = new ShowIncomingVehiclesInteractor(stationRepository, failurePresenter);
        interactor.execute(inputData);
    }

    // TODO: DO NOT SET API KEY/SET A WRONG KEY FOR THIS TEST AND NEED TO BE RUN INDIVIDUALLY
    @Test
    public void failureInvalidApiKeyTest() throws IOException, ParseException {
        ShowIncomingVehiclesInputData inputData = new ShowIncomingVehiclesInputData("Milton GO");
        GOVehicleApiClass goVehicleApiClass = new GOVehicleApiClass();
        ShowIncomingVehiclesDataAccessInterface stationRepository = new FileStationDataAccessObject("./revisedStopData.txt",
                new StationFactory(), new TrainFactory(), new GOStationApiClass(), goVehicleApiClass);

        // This creates a presenter that tests whether the test case is as we expect.
        ShowIncomingVehiclesOutputBoundary failurePresenter = new ShowIncomingVehiclesOutputBoundary() {
            @Override
            public void prepareSuccessView(ShowIncomingVehiclesOutputData stationInfoOutputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("An error occurred during API call.\nError Message: Unauthorized", error);
                StationInterface station = stationRepository.getStation("Milton GO");
                assertTrue(stationRepository.incomingVehiclesIsEmpty("Milton GO"));
                assertEquals(station.getIncomingVehicles().size(), 0);
            }
        };

        ShowIncomingVehiclesInputBoundary interactor = new ShowIncomingVehiclesInteractor(stationRepository, failurePresenter);
        interactor.execute(inputData);
    }


}
