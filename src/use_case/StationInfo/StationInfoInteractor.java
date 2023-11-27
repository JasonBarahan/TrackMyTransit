package use_case.StationInfo;

import entity.Station;
import entity.Vehicle;
import entity.VehicleFactory;
import use_case.search.SearchOutputData;

import java.util.List;

public class StationInfoInteractor implements StationInfoInputBoundary{
    final StationInfoDataAccessInterface stationInfoDataAccessObject;
    final StationInfoOutputBoundary stationInfoPresenter;
    final VehicleFactory vehicleFactory;

    //TODO: need to consider if we need vehicleFactory
    public StationInfoInteractor(StationInfoDataAccessInterface stationInfoDataAccessInterface,
                                 StationInfoOutputBoundary stationInfoOutputBoundary,
                                 VehicleFactory vehicleFactory) {
        this.stationInfoDataAccessObject = stationInfoDataAccessInterface;
        this.stationInfoPresenter = stationInfoOutputBoundary;
        this.vehicleFactory = vehicleFactory;
    }

    @Override
    public void execute(StationInfoInputData stationInfoInputData) {
            // Creating a Station object using the station factory based on the name that the user input
            Station station = stationInfoDataAccessObject.getStation(stationInfoInputData.getStationName());

            // Packaging key details from the above Station object into a SearchOutputData object
            List<Vehicle> incomingVehicles = station.getIncomingVehicles();
            List<List<String>> incomingVehiclesInfo;
            for (vehicle : incomingVehicles) {

            String amenitiesListAsString = String.join(", ", amenitiesList);
            SearchOutputData searchOutputData = new SearchOutputData(station.getName(), station.getParentLine(), amenitiesListAsString);

            // return the output data to the user
            stationPresenter.prepareSuccessView(searchOutputData);
          }
    }
}
