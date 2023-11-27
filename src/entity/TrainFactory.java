package entity;

import java.util.List;

public class TrainFactory {
    /**
     * Provide a create method that could "construct" instances of Train without having us to call
     * the actual Train class constructor.
     **/
    public Train create (String lineName, String lineNumber, String trainName, float latitude, float longitude,
                         String scheduledTime, String departureTime, String tripNumber, String delay) {
        return new Train (lineName, lineNumber, trainName, latitude, longitude,
                scheduledTime, departureTime, tripNumber, delay);
    }
}
