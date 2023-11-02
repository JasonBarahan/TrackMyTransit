package entity;

import java.util.List;

public class StationFactory {
    /*
        Notes about implementation:

        1. Taking a look at the CAE homework (Week 3), the file CommonUserFactory implements an interface called UserFactory.
        Do we need something similar for ours? I suspect note since we will likely need to create 2 other factory classes TrainFactory and BusFactory/

        2. I believe the intended purpose of StationFactory is to provide a create method that could "construct" instances of (in this case, Station)
        without having us to call the actual Station class constructor. That way, we can keep the constructor as "default access" rather than making it "public"
        for files like FileStationDataAccessObject.java to use (making it public will break CAE principles).
    */
    public Station create (String name, String stationId, String parentLine, float latitude, float longitude, List<String> amenitiesList, List<Vehicle> incomingVehicles) {
        return new Station (name, stationId, parentLine, latitude, longitude, amenitiesList, incomingVehicles);
    }

}
