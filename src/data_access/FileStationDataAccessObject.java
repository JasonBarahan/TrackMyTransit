package data_access;

import entity.Train;
import entity.Vehicle;
import entity.StationFactory;
import use_case.StationInfo.StationInfoDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.*;
import java.util.*; // resolves import for List and ArrayList
import java.util.HashMap;
import java.util.Map;
import entity.Station;

// We will name it as FileStationDataAccessObject for now. When we start to implement vehicles, we will change it as requires
// We might need to create different DA0 java files based on what data we are pulling (station, train or bus)
public class FileStationDataAccessObject implements SearchDataAccessInterface, StationInfoDataAccessInterface {
    private final File stationTxtFile;
    private final Map<String, Station> stations = new HashMap<>();
    private final StationFactory stationFactory;

    public FileStationDataAccessObject(String txtFilePath, StationFactory stationFactory) throws IOException {

        this.stationFactory = stationFactory;
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
               Float parsedStationLatitude = Float.valueOf(parsedStationInfo[2]); // TODO: Converting entry type to Float (object type), not the float primitative type
               Float parsedStationLongtitude = Float.valueOf(parsedStationInfo[3]);

               List <String> parsedStationAmenities = new ArrayList<String>(); //TODO: empty at the time of reading txt file, this will be populated through API calls
               List <Train> parsedStationVehicles = new ArrayList<Train>(); //TODO: empty at the time of reading txt file, this will be populated through API calls

               // For reference, here are the order of arguments in order to pass into stationFactory.create():
               //(name, stationId, parentLine, latitude, longitude, amenitiesList, incomingVehicles)
               Station station = stationFactory.create(parsedStationName, parsedStationID, parsedStationParentLine, parsedStationLatitude, parsedStationLongtitude, parsedStationAmenities, parsedStationVehicles);

               stations.put(parsedStationName, station);

           }
        }
    }
    @Override
    public Station getStation (String inputStationName) {

        return stations.get(inputStationName);
    }

    @Override
    public String getStationId(String inputStationName) {
        return stations.get(inputStationName).getId();
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
        return stations.containsKey(identifier); //TODO: MASSIVE ASSUMPTION HERE THAT THE USER types input in correct casing
                                                 // May need to resolve this by converting user input to lowercase -> then comparing to txt names (which will also be compared in lowercase form?)

    }

}
