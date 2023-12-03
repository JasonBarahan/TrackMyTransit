package use_case.station_info;

import entity.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StationInfoInteractor implements StationInfoInputBoundary{
    final StationInfoDataAccessInterface stationInfoDataAccessObject;
    final StationInfoOutputBoundary stationInfoPresenter;

    //TODO: need to consider if we need vehicleFactory
    public StationInfoInteractor(StationInfoDataAccessInterface stationInfoDataAccessInterface,
                                 StationInfoOutputBoundary stationInfoOutputBoundary) {
        this.stationInfoDataAccessObject = stationInfoDataAccessInterface;
        this.stationInfoPresenter = stationInfoOutputBoundary;
    }

    @Override
    public void execute(StationInfoInputData stationInfoInputData) {
        // if not empty
        if (!stationInfoDataAccessObject.incomingVehiclesIsEmpty(stationInfoInputData.getStationName())) {

            // First, fetching the constructed, but incomplete station object. This object is created based on reading from revisedStopData.txt
//            Station incompleteStation = stationInfoDataAccessObject.getStation(stationInfoInputData.getStationName());

            stationInfoDataAccessObject.setStation(stationInfoInputData.getStationName()); // Then, populate the station's currently empty amenitiesList and incomingVehicles attributes based on API calls

            StationInterface station = stationInfoDataAccessObject.getStation(stationInfoInputData.getStationName()); // Now, re-retrieve the fully complete Station object

            // Packaging key details from the above Station object into a SearchOutputData object
            List<Train> incomingVehicles = stationInfoDataAccessObject.getIncomingVehicles(stationInfoInputData.getStationName());

            List<List<String>> incomingVehiclesInfo = new ArrayList<>();

            // We want to show line name, train direction, scheduled time, departure time and delay
            // and sort them by departure time
            for (int i = 0; i < incomingVehicles.size(); i++) {
                List<String> vehicleinfo = new ArrayList<>();
                String vehicleName = incomingVehicles.get(i).getLineName();
                String vehicleDirection = incomingVehicles.get(i).getTrainDirection();
                String vehicleLatitude = String.valueOf(incomingVehicles.get(i).getLatitude());
                String vehicleLongitude = String.valueOf(incomingVehicles.get(i).getLongitude());
                String vehicleScheduledTime = incomingVehicles.get(i).getScheduledTime();
                String vehicleDepartureTime = incomingVehicles.get(i).getDepartureTime();
                String vehicleDelay = incomingVehicles.get(i).getDelay();
                vehicleinfo.add("Vehicle Name: " + vehicleName);
                vehicleinfo.add("Vehicle direction: " + vehicleDirection);
                vehicleinfo.add("Vehicle Latitude: " + vehicleLatitude);
                vehicleinfo.add("Vehicle Longitude: " + vehicleLongitude);
                vehicleinfo.add("Scheduled Departure Time: ");
                vehicleinfo.add(vehicleScheduledTime);
                vehicleinfo.add("Computed Departure Time: ");
                vehicleinfo.add(vehicleDepartureTime);
                vehicleinfo.add("Delay: " + vehicleDelay);
                incomingVehiclesInfo.add(vehicleinfo);
            }
            sortByDateTime(incomingVehiclesInfo);
            // sort incomingVehiclesInfo by date time

            List<List<String>> slicedIncomingVehiclesInfo;
            // slice incomingVehiclesInfo list to get the first three items.
            // i.e. we retrieve 3 vehicles that arrive the soonest
            // if incomingVehiclesInfo only has <= 3 items, keep it the same
            if (incomingVehiclesInfo.size() <= 3) {
                slicedIncomingVehiclesInfo = incomingVehiclesInfo;
            } else {
                slicedIncomingVehiclesInfo = incomingVehiclesInfo.subList(0, 3);
            }

            StationInfoOutputData stationInfoOutputData = new StationInfoOutputData(station.getName(), slicedIncomingVehiclesInfo);

            // return the output data to the user
            stationInfoPresenter.prepareSuccessView(stationInfoOutputData);
        } else {
            String errorMsg = stationInfoDataAccessObject.getVehicleInfoRetrievalErrorMsg(stationInfoInputData.getStationName());
            stationInfoPresenter.prepareFailView("An error occurred during API call.\nError Message: " + errorMsg);
        }
    }

    private static void sortByDateTime(List<List<String>> incomingVehiclesInfo) {
        incomingVehiclesInfo.sort((list1, list2) -> {
            String dateTimeStr1 = list1.get(5);
            String dateTimeStr2 = list2.get(5);

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
}
