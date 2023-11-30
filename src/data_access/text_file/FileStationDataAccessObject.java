package data_access.text_file;

import data_access.API.GOStationApiClass;
import data_access.API.GOStationCoordinatesApiClass;
import data_access.API.GOVehicleApiClass;
import entity.*;
import use_case.StationInfo.StationInfoDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.*;
import java.util.*; // resolves import for List and ArrayList
import java.util.HashMap;
import java.util.Map;

// We will name it as FileStationDataAccessObject for now. When we start to implement vehicles, we will change it as requires
// We might need to create different DA0 java files based on what data we are pulling (station, train or bus)
public class FileStationDataAccessObject implements SearchDataAccessInterface, StationInfoDataAccessInterface {
    private final File stationTxtFile;
    private final Map<String, Station> stations = new HashMap<>();
    private final StationFactory stationFactory;
    private final TrainFactory trainFactory;

    private final GOStationApiClass goStationApiClass;
    private final GOVehicleApiClass goVehicleApiClass;
    private final GOStationCoordinatesApiClass goStationCoordinatesApiClass;

    public FileStationDataAccessObject(String txtFilePath, StationFactory stationFactory, TrainFactory trainFactory,
                                       GOStationApiClass goStationApiClass, GOVehicleApiClass goVehicleApiClass, GOStationCoordinatesApiClass goStationCoordinatesApiClass) throws IOException {

        this.stationFactory = stationFactory;
        this.trainFactory = trainFactory;
        this.goStationApiClass = goStationApiClass;
        this.goVehicleApiClass = goVehicleApiClass;
        this.goStationCoordinatesApiClass = goStationCoordinatesApiClass;
        stationTxtFile = new File(txtFilePath);

        // Reading the provided txt file that has a path specified by attribute txtFilePath
        try(BufferedReader reader = new BufferedReader(new FileReader(stationTxtFile))){
            String line;
            reader.readLine(); // call the readline() method once outside the loop as we do not want to read the first line of txt file, since that line contains just the headers.
            while((line = reader.readLine()) != null)  {

                String[] parsedStationInfo = line.split(","); //splitting  by "," since information in txt file is seperated by ","
                String parsedStationName = parsedStationInfo[1];
                String parsedStationID = parsedStationInfo[0];

                String parsedStationParentLine = parsedStationInfo[5];
                Float parsedStationLatitude = Float.valueOf(parsedStationInfo[2]); // converting string type to float object type. Through potential autoboxing, this float object type is converted to primitative type.
                Float parsedStationLongtitude = Float.valueOf(parsedStationInfo[3]);

                List <String> parsedStationAmenities = new ArrayList<String>(); //This is empty at the time of reading txt file, this will be populated through API calls
                List <Train> parsedStationVehicles = new ArrayList<Train>(); //This is empty at the time of reading txt file, this will be populated through API calls

                // For reference, here are the order of arguments in order to pass into stationFactory.create():
                //(name, stationId, parentLine, latitude, longitude, amenitiesList, incomingVehicles)
                Station station = stationFactory.create(parsedStationName, parsedStationID, parsedStationParentLine, parsedStationLatitude, parsedStationLongtitude, parsedStationAmenities, parsedStationVehicles);

                stations.put(parsedStationName, station);
            }
        }
    }
    @Override
    public Station getStation (String inputStationName) {
        Station incompleteStationObj = stations.get(inputStationName);

        // Assigning the Station obj's amenitiesList attribute to a valid value
        List<String> retrievedStationAmenities = getStationAmenities(inputStationName);
        incompleteStationObj.setAmenitiesList(retrievedStationAmenities);
        List<Train> retrievedIncomingVehicles = getIncomingVehicles(inputStationName);
        incompleteStationObj.setIncomingVehiclesList(retrievedIncomingVehicles);

        //TODO: Do something similar for incomingVehicles?

        return stations.get(inputStationName);
    }

    @Override
    public List<Train> getIncomingVehicles(String inputStationName) {
        String stationId = getStationId(inputStationName);
        List<Train> incomingVehiclesList = new ArrayList<>();
        for (List<String> vehicles: goVehicleApiClass.retrieveVehicleInfo(stationId)) {
            Train vehicle = trainFactory.create(vehicles.get(0), vehicles.get(1),vehicles.get(2),vehicles.get(3),vehicles.get(4),
                    vehicles.get(5),vehicles.get(6),Float.parseFloat(vehicles.get(7)),Float.parseFloat(vehicles.get(8)));
            incomingVehiclesList.add(vehicle);
        }
//        List<Train> incomingVehiclesList = goVehicleApiClass.retrieveVehicleInfo(stationId);
        return incomingVehiclesList;
    }

    @Override
    public String getStationId(String inputStationName) {
        return (stations.get(inputStationName)).getId();
    }

    @Override
    public String getStationParentLine(String inputStationName) {

        return (stations.get(inputStationName)).getParentLine();
    }

    public String getStationID (String inputStationName) {

        return (stations.get(inputStationName)).getId();
    }

    @Override
    public List<String> getStationAmenities(String inputStationName) {
        //TODO: Need to save this information in the actual Station objects such that we don't duplicate API calls
        String stationID = getStationID(inputStationName);
        List<String> stationAmenitiesList = goStationApiClass.retrieveStationAmenities(stationID);
        return stationAmenitiesList;
    }

    public boolean stationExist(String identifier){
        return stations.containsKey(identifier); //TODO: MASSIVE ASSUMPTION HERE THAT THE USER types input in correct casing
                                                 // May need to resolve this by converting user input to lowercase -> then comparing to txt names (which will also be compared in lowercase form?)

    }
}
