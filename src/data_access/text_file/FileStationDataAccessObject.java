package data_access.text_file;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import entity.*;
import use_case.station_info.StationInfoDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.delay.DelayInteractor;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import entity.Station;

// We will name it as FileStationDataAccessObject for now. When we start to implement vehicles, we will change it as requires
// We might need to create different DA0 java files based on what data we are pulling (station, train or bus)
public class FileStationDataAccessObject implements SearchDataAccessInterface, StationInfoDataAccessInterface, DelayInteractor {
    private final File stationTxtFile;
    private final Map<String, Station> stations = new HashMap<>();
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
                Station station = stationFactory.create(parsedStationName, parsedStationID, parsedStationParentLine, parsedStationLatitude, parsedStationLongtitude, parsedStationAmenities, parsedStationVehicles);

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
    public Station getStation (String inputStationName) {
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
    public List<Train> getIncomingVehicles(String inputStationName) throws ParseException {
        //TODO: Consider moving the "construction" of the train object to a new method called setIncomingVehicles
        if (incomingVehiclesNotEmpty(inputStationName)) {
            String stationId = getStationID(inputStationName);
            List<Train> incomingVehiclesList = new ArrayList<>();
            for (List<String> vehicles : goVehicleApiClass.retrieveVehicleInfo(stationId)) {
                String lineCode = vehicles.get(0);
                String lineName = vehicles.get(1);
                String trainName = vehicles.get(3);
                String scheduledTime = vehicles.get(4);
                String departureTime = vehicles.get(5);
                String tripNumber = vehicles.get(6);
                String delay = delayTime(scheduledTime, departureTime);  // Leave as null for error
                String latitude = vehicles.get(7);
                String longitude = vehicles.get(8);
                Train vehicle = trainFactory.create(lineCode, lineName, trainName, scheduledTime, departureTime,
                        tripNumber, delay, Float.parseFloat(latitude), Float.parseFloat(longitude));
                incomingVehiclesList.add(vehicle);
            }
//        List<Train> incomingVehiclesList = goVehicleApiClass.retrieveVehicleInfo(stationId);
            return incomingVehiclesList;
        }
        else {return null;}
    }

    @Override
    public void setStation (Station stationObj) throws ParseException {
        //Get station name
        String stationName = getStationName(stationObj);

        // Set station amenities
        setStationAmenities(stationObj);

        // TODO: Resolve the lines below such that they follow the format above
        List<Train> retrievedIncomingVehicles = getIncomingVehicles(stationName);
        stationObj.setIncomingVehiclesList(retrievedIncomingVehicles);

    }

    @Override
    public void setStationAmenities(Station stationObj){
        String stationID = stationObj.getId();
        List<String> stationAmenitiesList = goStationApiClass.retrieveStationAmenities(stationID);
        stationObj.setAmenitiesList(stationAmenitiesList);
    }

    @Override
    public boolean incomingVehiclesNotEmpty(String stationName) {
        if (goVehicleApiClass.retrieveVehicleInfo(stations.get(stationName).getId())==(null)) {
            return false;
        }
        return true;
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

    @Override
    public String delayTime(String scheduled, String computed) throws ParseException {
        String scheduleDate = scheduled.substring(11);
        String computedDate = computed.substring(11);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date scheduleTime = dateFormat.parse(scheduleDate);
        Date computedTime = dateFormat.parse(computedDate);

        double difference = (computedTime.getTime() - scheduleTime.getTime()) * 0.001; // in seconds

        // if cases for returning the differences in time
        if (difference >= 3600) {
            return MAJOR + calculated(difference) + HOURS;
        } else if (difference >= 900) {
            return MAJOR + calculated(difference) + MINUTES;
        } else if (difference >= 180) {
            return DELAY + calculated(difference) + MINUTES;
        } else if (difference >= 60) {
            return MINIMAL + calculated(difference) + MINUTES;
        } return NONE + " arriving in " + difference + SECONDS;

    }

    @Override
    public double calculated(double time) {
        {
            if (time >= 3600) {
                return time;
            }
            return time / 60;
        }
    }
}
