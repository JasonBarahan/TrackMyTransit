package entity;

import java.util.List;

public class TrainFactory {
    /**
     * Provide a create method that could "construct" instances of Train without having us to call
     * the actual Train class constructor.
     **/
    public Train create (String name, String trainNumber, String parentLine, float latitude, float longitude, String scheduledTime, String delay) {
        return new Train (name, trainNumber, parentLine, latitude, longitude, scheduledTime, delay);
    }
}
