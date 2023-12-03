package entity;

import java.util.*;

public interface StationInterface {

    String getId();

    String getName();

    String getParentLine();

    float getLatitude();

    float getLongitude();

    List<String> getAmenitiesList();

    List<Train> getIncomingVehicles();

    void setIncomingVehiclesList(List<Train> incomingVehiclesList);

    void setAmenitiesList(List<String> stationAmenitiesList);
}
