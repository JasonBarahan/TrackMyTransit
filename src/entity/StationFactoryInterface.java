package entity;

import java.util.List;

public interface StationFactoryInterface {
    Station create(String name, String stationId, String parentLine, float latitude, float longitude, List<String> amenitiesList, List<Vehicle> incomingVehicles);
}
