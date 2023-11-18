package api;

import java.util.List;

public interface StationAPI {
    List<String> getStationAmenities(String stationId);

    List<String> getIncomingVehicles(String stationId);
}
