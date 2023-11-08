package data_access;

import entity.*;
import use_case.search.SearchDataAccessInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*; // resolves import for List and ArrayList
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Float.valueOf;

// We will name it as FileStationDataAccessObject for now. When we start to implement vehicles, we will change it as requires
// We might need to create different DA0 java files based on what data we are pulling (station, train or bus)
public class FileStationDataAccessObject implements SearchDataAccessInterface {

    private final File txtFile;
    private final Map<String, Station> stations = new HashMap<>();

    private StationFactory stationFactory;

    public FileStationDataAccessObject(String txtPath, StationFactory stationFactory) throws IOException {

        this.stationFactory = stationFactory;

        txtFile = new File(txtPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
            String row;
            reader.readLine();
//            assert header.equals("stop_id,stop_name,stop_lat,stop_lon,line,line_name");

            while ((row = reader.readLine()) != "") {
                // TODO: Error here: For some reason, row = "", therefore there's an index error. Fix.
                String[] col = row.split(",");
                String stationId = col[0];
                String stationName = col[1];
                float stop_lat = Float.valueOf(col[2]);
                float stop_lon = Float.valueOf(col[3]);
//                String line = String.valueOf(col[headers.get("line")]); Don't need
                String parentline = String.valueOf(col[5]);
                List<String> stationAmenities = new ArrayList<String>(); //TODO: empty at the time of reading txt file, this will be populated through API calls
                List<Vehicle> stationVehicles = new ArrayList<Vehicle>(); //TODO: empty at the time of reading txt file, this will be populated through API calls

                Station station = stationFactory.create(stationName, stationId, parentline, stop_lat, stop_lon, stationAmenities, stationVehicles);
                stations.put(stationName, station);
            }

//        // Filling the attribute "stations" with mock data
//
//        List <String> mockAuroraStationAmenities = new ArrayList<String>();
//        mockAuroraStationAmenities.add("Wifi");
//        List <Vehicle> mockAuroraStationVehicles = new ArrayList<Vehicle>();
//        mockAuroraStationVehicles.add(new Vehicle());
//        Station mockAuroraStation = stationFactory.create("Aurora", "AU", "Barrie Line", 3.14f, 3.15f, mockAuroraStationAmenities, mockAuroraStationVehicles);
//        stations.put("Aurora", mockAuroraStation);
//
//        List <String> mockUnionStationAmenities = new ArrayList<String>();
//        mockUnionStationAmenities.add("Washrooms");
//        List <Vehicle> mockUnionStationVehicles = new ArrayList<Vehicle>();
//        mockUnionStationVehicles.add(new Vehicle());
//        Station mockUnionStation = stationFactory.create("Union", "UN", "Infinity Line", 2.00f, 1.00f, mockUnionStationAmenities, mockUnionStationVehicles);
//        stations.put("Union", mockUnionStation);
        }
    }
    @Override
    public Station getStation (String inputStationName) {

        return stations.get(inputStationName);
    }

    @Override
    public String getStationParentLine(String inputStationName) {

        return (stations.get(inputStationName)).getParentLine();
    }

    @Override
    public List<String> getStationAmenities(String inputStationName) {

        return (stations.get(inputStationName)).getAmenitiesList();
    }

    public boolean stationExist(String identifier){
        return stations.containsKey(identifier); //NOTE: MASSIVE ASSUMPTION HERE THAT THE USER types input in correct casing

    }

}
