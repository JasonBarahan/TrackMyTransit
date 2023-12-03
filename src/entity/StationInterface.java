package entity;

import java.util.*;

public interface StationInterface {

    String getId();

    String getName();

    String getParentLine();

    float getLatitude();

    float getLongitude();

    List<String> getAmenitiesList();

    void setAmenitiesList(List<String> stationAmenitiesList);

    List<Train> getIncomingVehicles();

    void setIncomingVehiclesList(List<Train> retrievedIncomingVehicles);

}
