package data_access.text_file;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import entity.*;
import use_case.station_info.StationInfoDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import entity.Station;

// We will name it as FileStationDataAccessObject for now. When we start to implement vehicles, we will change it as requires
// We might need to create different DA0 java files based on what data we are pulling (station, train or bus)
public class FileStationDataAccessObject implements SearchDataAccessInterface, StationInfoDataAccessInterface {
    private final File stationTxtFile;
    private final Map<String, StationInterface> stations = new HashMap<>();
    private final StationFactory stationFactory;
    private final TrainFactory trainFactory;

    private final GOStationApiClass goStationApiClass;
    private final GOVehicleApiClass goVehicleApiClass;

    public FileStationDataAccessObject(String txtFilePath, StationFactory stationFactory, TrainFactory trainFactory,
                                       GOStationApiClass goStationApiClass, GOVehicleApiClass goVehicleApiClass) throws IOException {

        this.stationFactory = stationFactory;
        this.trainFactory = trainFactory;
        this.goStationApiClass = goStationApiClass;
        this.goVehicleApiClass = goVehicleApiClass;
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
                StationInterface station = stationFactory.create(parsedStationName, parsedStationID, parsedStationParentLine, parsedStationLatitude, parsedStationLongtitude, parsedStationAmenities, parsedStationVehicles);

                stations.put(parsedStationName, station);
            }
        }
    }

    @Override
    public String getAPIMetadataSuccessMessage() {
        return goStationApiClass.getApiMetadataSuccessMessage();
    }

    // Getter method to retrieve Station object.
    // This method does NOT modify the contents of the Station object.
    // Modifications to Station object occurs in the setStation() method
    @Override
    public StationInterface getStation (String inputStationName) {
        if (stationExist(inputStationName)) {
            return stations.get(inputStationName);
        } else {
            return null;
        }
    }

    @Override
    public String getStationName (Station stationObj) {
        return stationObj.getName();
    }

    @Override
    public String getStationParentLine(String inputStationName) {
        return (stations.get(inputStationName)).getParentLine();
    }

    @Override
    public String getStationID (String inputStationName) {
        return (stations.get(inputStationName)).getId();
    }

    // Getter method to retrieve Station Amenities list.
    // This method does NOT modify the contents of the Station object.
    // Modifications to Station object occurs in the setStationAmenities() method
    @Override
    public List<String> getStationAmenities(String inputStationName) {
        return (stations.get(inputStationName)).getAmenitiesList();
    }

    @Override
    public List<Train> getIncomingVehicles(String inputStationName) {
        return (stations.get(inputStationName)).getIncomingVehicles();
    }

    @Override
    public void setStation (String stationName) {

        // Set station amenities
        setStationAmenities(stationName);
        // Set station incoming vehicles
        setIncomingVehiclesList(stationName);

    }

    @Override
    public void setIncomingVehiclesList(String stationName){
        StationInterface stationObj = getStation(stationName);
        String stationID = stationObj.getId();
        if (!incomingVehiclesIsEmpty(stationName)) {
            List<List<String>> goVehicleInfo = goVehicleApiClass.retrieveVehicleInfo(stationID);
            List<Train> incomingVehiclesList = new ArrayList<>();
            for (List<String> vehicles : goVehicleInfo) {
                String lineName = vehicles.get(0);
                String lineCode = vehicles.get(1);
                String trainName = vehicles.get(3);
                String scheduledTime = vehicles.get(4);
                String departureTime = vehicles.get(5);
                String tripNumber = vehicles.get(6);
                String delay = null;  //TODO: get vehicle delay
                String latitude = vehicles.get(7);
                String longitude = vehicles.get(8);
                Train vehicle = trainFactory.create(lineCode, lineName, trainName, scheduledTime, departureTime,
                        tripNumber, delay, Float.parseFloat(latitude), Float.parseFloat(longitude));
                incomingVehiclesList.add(vehicle);
            }
            stationObj.setIncomingVehiclesList(incomingVehiclesList);
        }
    }

    @Override
    public void setStationAmenities(String stationName) {
        StationInterface stationObj = getStation(stationName);
        String stationID = stationObj.getId();
        List<String> stationAmenitiesList = goStationApiClass.retrieveStationAmenities(stationID);
        stationObj.setAmenitiesList(stationAmenitiesList);
    }

    @Override
    public boolean incomingVehiclesIsEmpty(String stationName) {
        return goVehicleApiClass.retrieveVehicleInfo(stations.get(stationName).getId()) instanceof String;
    }

    @Override
    public String getVehicleInfoRetrievalErrorMsg(String stationName){
        return goVehicleApiClass.retrieveVehicleInfo(stations.get(stationName).getId());
    }

    @Override
    public boolean stationExist(String identifier){
        return stations.containsKey(identifier); //TODO: MASSIVE ASSUMPTION HERE THAT THE USER types input in correct casing
                                                 // May need to resolve this by converting user input to lowercase -> then comparing to txt names (which will also be compared in lowercase form?)
                                                // TODO #2: What happens if the text file contains a station that the API no longer supports
    }

    //This is a method that returns the message associated with the attempted amenities API call
    // For more information about API messages, please see comments in file GOStationApiClass.java
    @Override
    public String amenitiesAPICallMetadataMessage(String stationName){
        String stationID = getStationID(stationName);
        Map<String, List<Object>> amenitiesAPICallResult = goStationApiClass.stationAmenitiesCallResult(stationID);
        String amenitiesAPICallMetadataCode = (String) amenitiesAPICallResult.keySet().toArray()[0];
        return (String) (amenitiesAPICallResult.get(amenitiesAPICallMetadataCode)).get(0);
    }

}
