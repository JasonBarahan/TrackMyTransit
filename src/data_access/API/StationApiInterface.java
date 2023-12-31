package data_access.API;

import java.util.List;
import java.util.Map;

/**
 * Defines the shared attributes and instances of all StationAPI classes
 */
public interface StationApiInterface {
    String API_KEY = System.getenv("API_KEY");
    String getApiMetadataSuccessMessage();
    Map<String, List<Object>> stationAmenitiesCallResult(String stationId);
    List<String> retrieveStationAmenities(String stationId);
}
