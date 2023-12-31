package use_case.station_general_info;

import entity.Station;
import entity.StationInterface;

import java.text.ParseException;
import java.util.List;

public interface StationGeneralInfoDataAccessInterface {
    /**
     * Purpose: This interface contains all the shared methods for the DAO
     */
    boolean stationExist(String identifier);

    String getAPIMetadataSuccessMessage();
    String amenitiesAPICallMetadataMessage(String stationName);

    String getStationID (String inputStationName);
    StationInterface getStation(String inputStationName);

    String getStationName (StationInterface stationObj);
    String getStationParentLine(String inputStationName);

    List<String> getStationAmenities(String inputStationName);

    void setStation(String stationName) throws ParseException;
    void setStationAmenities(String stationName);

}
