package entity;

import java.util.List;

public class TrainFactory {
    /**
     * Provide a create method that could "construct" instances of Train without having us to call
     * the actual Train class constructor.
     **/
    public Train create (String lineName, String lineNumber, String trainDirection, String scheduledTime,
                         String departureTime, String tripNumber, String delay, float latitude, float longitude) {
        return new Train (lineName, lineNumber, trainDirection, scheduledTime, departureTime,
                tripNumber, delay, latitude, longitude);
    }
}
