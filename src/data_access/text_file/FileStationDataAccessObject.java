package data_access.text_file;

import data_access.API.GOStationApiClass;
import data_access.API.GOVehicleApiClass;
import data_access.API.StationApiInterface;
import data_access.API.TrainApiInterface;
import entity.*;
import use_case.station_general_info.StationGeneralInfoDataAccessInterface;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import entity.Station;
import use_case.show_incoming_vehicles.ShowIncomingVehiclesDataAccessInterface;


public class FileStationDataAccessObject implements StationGeneralInfoDataAccessInterface, ShowIncomingVehiclesDataAccessInterface {
    private final File stationTxtFile;
    private final Map<String, StationInterface> stations = new HashMap<>(); // Hashmap of station objects
    private final StationFactory stationFactory;
    private final TrainFactory trainFactory;

    private final StationApiInterface goStationApiClass;
    private final TrainApiInterface goVehicleApiClass;


    /**
     * Constructor returning a FileStation DAO, used to access and change Station entity attribures
     * @param  txtFilePath  string denoting the path of a text file containing station information
     * @param  stationFactory A station Factory
     * @param trainFactory A train factory
     * @param goStationApiClass An GO Station API class
     * @param goVehicleApiClass An GO vehicle API Class
     * @return Instance of FileStationDAO
     */
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

    /** Getter method to retrieve API Success Message. Returns: String*/
    @Override
    public String getAPIMetadataSuccessMessage() {
        return goStationApiClass.getApiMetadataSuccessMessage();
    }

    /** Getter method to retrieve a station object. Returns: StationInterface*/
    @Override
    public StationInterface getStation (String inputStationName) {
        if (stationExist(inputStationName)) {
            return stations.get(inputStationName);
        } else {
            return null;
        }
    }

    /** Getter method to retrieve Station Name. Returns: String*/
    @Override
    public String getStationName (StationInterface stationObj) {
        return stationObj.getName();
    }

    /** Getter method to retrieve Station parent line. Returns: String*/
    @Override
    public String getStationParentLine(String inputStationName) {
        return (stations.get(inputStationName)).getParentLine();
    }

    /** Getter method to retrieve Station ID. Returns: String*/
    @Override
    public String getStationID (String inputStationName) {
        return (stations.get(inputStationName)).getId();
    }

    /** Getter method to retrieve Station Amenities List. Returns: List<String>*/
    @Override
    public List<String> getStationAmenities(String inputStationName) {
        return (stations.get(inputStationName)).getAmenitiesList();
    }

    @Override
    public List<Train> getIncomingVehicles(String inputStationName) {
        return (stations.get(inputStationName)).getIncomingVehicles();
    }

    @Override
    public void setIncomingVehiclesList(String stationName) throws ParseException {
        StationInterface stationObj = getStation(stationName);
        String stationID = stationObj.getId();
        if (!incomingVehiclesIsEmpty(stationName)) {
            List<List<String>> goVehicleInfo = goVehicleApiClass.retrieveVehicleInfo(stationID);
            List<Train> incomingVehiclesList = new ArrayList<>();
            for (List<String> vehicles : goVehicleInfo) {
                String lineName = vehicles.get(1);
                String lineCode = vehicles.get(0);
                String trainName = vehicles.get(3);
                String scheduledTime = vehicles.get(4);
                String departureTime = vehicles.get(5);
                String tripNumber = vehicles.get(6);
                String delay = delayTime(scheduledTime, departureTime);
                String latitude = vehicles.get(7);
                String longitude = vehicles.get(8);
                Train vehicle = trainFactory.create(lineName, lineCode, trainName, scheduledTime, departureTime,
                        tripNumber, delay, Float.parseFloat(latitude), Float.parseFloat(longitude));
                incomingVehiclesList.add(vehicle);
            }
            stationObj.setIncomingVehiclesList(incomingVehiclesList);
        }
    }

    /** Setter method to modify Station object.*/
    @Override
    public void setStation (String stationName) throws ParseException {
        // Set station amenities
        setStationAmenities(stationName);
        // Set station incoming vehicles
        setIncomingVehiclesList(stationName);
    }

    /** Setter method to modify Station amenities.*/
    @Override
    public void setStationAmenities(String stationName){
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

    /** Method returning a Boolean to check whether station exists.
     * MASSIVE ASSUMPTION: We are assuming that the user types in correct casing for station name (needs to be exact match)*/
    @Override
    public boolean stationExist(String identifier){
        return stations.containsKey(identifier);
    }

    /** Method that returns the message associated with the attempted amenities API call
     * For more information about API messages, please see comments in file GOStationApiClass.java
     */
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
            DecimalFormat df = new DecimalFormat("#.#");

            if (time >= 3600) {
                return Double.parseDouble(df.format(time));
            }
            return Double.parseDouble(df.format(time / 60));
        }
    }
}
