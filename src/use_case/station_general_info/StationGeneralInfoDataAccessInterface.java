package use_case.station_general_info;

import entity.Station;
import entity.StationInterface;

import java.text.ParseException;
import java.util.List;

public interface StationGeneralInfoDataAccessInterface {
    boolean stationExist(String identifier);

    String getAPIMetadataSuccessMessage();
    String amenitiesAPICallMetadataMessage(String stationName);

    String getStationID (String inputStationName);
    StationInterface getStation(String inputStationName);

    String getStationName (Station stationObj);
    String getStationParentLine(String inputStationName);

    List<String> getStationAmenities(String inputStationName);

    void setStation(String stationName) throws ParseException;
    void setStationAmenities(String stationName);

}
