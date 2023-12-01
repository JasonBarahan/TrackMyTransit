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

}
