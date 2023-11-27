package use_case.StationInfo;

import entity.Station;
import entity.Vehicle;
import entity.VehicleFactory;
import use_case.search.SearchOutputData;
import entity.Train;

import java.util.ArrayList;
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
            // Creating a Station object using the station factory based on the name that the user input
            Station station = stationInfoDataAccessObject.getStation(stationInfoInputData.getStationName());

            // Packaging key details from the above Station object into a SearchOutputData object
            List<Train> incomingVehicles = station.getIncomingVehicles();
            // List<Train> will change into List<Vehicle> after implementing Vehicle class.
            List<List<String>> incomingVehiclesInfo = new ArrayList<>();

            // We want to show train display name, scheduled time, departure time and delay
            for (int i = 0; i < incomingVehicles.size(); i++) {
                List<String> vehicleinfo = new ArrayList<>();
                String vehicleName = incomingVehicles.get(i).getTrainName();
                String vehicleScheduledTime = incomingVehicles.get(i).getScheduledTime();
                String vehicleDepartureTime = incomingVehicles.get(i).getDepartureTime();
                String vehicleDelay = incomingVehicles.get(i).getDelay();
                vehicleinfo.add(vehicleName);
                vehicleinfo.add(vehicleScheduledTime);
                vehicleinfo.add(vehicleDepartureTime);
                vehicleinfo.add(vehicleDelay);
                incomingVehiclesInfo.add(vehicleinfo);
            }

//            String incomingVehicleListAsString;
//            for (List<String> i : incomingVehiclesInfo) {
//                incomingVehicleListAsString = String.join("\n", i);
//            }
//            Do not need to convert to String

            StationInfoOutputData stationInfoOutputData = new StationInfoOutputData(station.getName(), incomingVehiclesInfo);

            // return the output data to the user
            stationInfoPresenter.prepareSuccessView(stationInfoOutputData);
          }

}
