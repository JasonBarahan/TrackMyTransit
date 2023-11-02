package data_access;

import entity.Train;
import entity.Vehicle;
import entity.StationFactory;
import use_case.search.SearchDataAccessInterface;

import java.util.*; // resolves import for List and ArrayList
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import entity.Station;

// We will name it as FileStationDataAccessObject for now. When we start to implement vehicles, we will change it as requires
// We might need to create different DA0 java files based on what data we are pulling (station, train or bus)
public class FileStationDataAccessObject implements SearchDataAccessInterface {
    private final Map<String, Station> stations = new HashMap<>();
    private StationFactory stationFactory;

    public FileStationDataAccessObject(StationFactory stationFactory){

        this.stationFactory = stationFactory;

        // Filling the attribute "stations" with mock data

        List <String> mockAuroraStationAmenities = new ArrayList<String>();
        mockAuroraStationAmenities.add("Wifi");
        List <Vehicle> mockAuroraStationVehicles = new ArrayList<Vehicle>();
        mockAuroraStationVehicles.add(new Vehicle());
        Station mockAuroraStation = stationFactory.create("Aurora", "AU", "Barrie Line", 3.14f, 3.15f, mockAuroraStationAmenities, mockAuroraStationVehicles);
        stations.put("Aurora", mockAuroraStation);

        List <String> mockUnionStationAmenities = new ArrayList<String>();
        mockUnionStationAmenities.add("Washrooms");
        List <Vehicle> mockUnionStationVehicles = new ArrayList<Vehicle>();
        mockUnionStationVehicles.add(new Vehicle());
        Station mockUnionStation = stationFactory.create("Union", "UN", "Infinity Line", 2.00f, 1.00f, mockUnionStationAmenities, mockUnionStationVehicles);
        stations.put("Union", mockUnionStation);
    }
    @Override
    public String getStationName(String inputStationName) {
        return (stations.get(inputStationName)).getName();
    }
    public boolean stationExist(String identifier){
        return stations.containsKey(identifier); //NOTE: MASSIVE ASSUMPTION HERE THAT THE USER types input in correct casing

    }

}
